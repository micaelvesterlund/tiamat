package org.rutebanken.tiamat.pelias;


import com.google.common.util.concurrent.Striped;
import com.vividsolutions.jts.geom.Point;
import org.rutebanken.tiamat.model.*;
import org.rutebanken.tiamat.pelias.model.Feature;
import org.rutebanken.tiamat.pelias.model.Properties;
import org.rutebanken.tiamat.pelias.model.ReverseLookupResult;
import org.rutebanken.tiamat.repository.TopographicPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CountyAndMunicipalityLookupService {

    private static final Logger logger = LoggerFactory.getLogger(CountyAndMunicipalityLookupService.class);

    @Autowired
    private PeliasReverseLookupClient peliasReverseLookupClient;

    @Autowired
    private TopographicPlaceRepository topographicPlaceRepository;
    
    private Striped<Semaphore> stripedSemaphores = Striped.lazyWeakSemaphore(19, 1);


    /**
     * Reverse lookup stop place centroid from Pelias.
     * References to topographical places for municipality and county on the stopPLace.
     */
    public void populateCountyAndMunicipality(StopPlace stopPlace, AtomicInteger topographicPlacesCreatedCounter) throws IOException, InterruptedException {

        Point point = stopPlace.getCentroid().getLocation().getGeometryPoint();

        Properties peliasProperties = reverseLookup(point, stopPlace);
        if(peliasProperties == null) {
            return;
        }

        String peliasCounty = peliasProperties.getCounty();
        Semaphore stripedSemaphore = stripedSemaphores.get(peliasCounty);
        stripedSemaphore.acquire();
        try {
            TopographicPlace municipality = populateCountyAndMunicipality(peliasProperties.getCounty(), peliasProperties.getLocaladmin(), topographicPlacesCreatedCounter);
            createAndSetRef(stopPlace, municipality);
        } finally {
            logger.debug("Releasing semaphore for region {}", peliasCounty);
            stripedSemaphore.release();
        }
    }

    private Properties reverseLookup(Point point, StopPlace stopPlace) throws IOException {
        ReverseLookupResult reverseLookupResult = peliasReverseLookupClient.reverseLookup(String.valueOf(point.getY()),
                String.valueOf(point.getX()), 1);

        if (reverseLookupResult.getFeatures().isEmpty()) {
            logger.warn("Got empty features list from Pelias reverse. {},{}", String.valueOf(point.getY()),
                    String.valueOf(point.getX()));
            return null;
        }

        Properties properties = reverseLookupResult.getFeatures().get(0).getProperties();

        logger.trace("Got county {} and locality {}", properties.getCounty(), properties.getLocaladmin());

        if (properties.getCounty() == null) {
            logger.warn("County was null from Pelias for stop place {}. Ignoring.", stopPlace.getName());
            return null;
        }

        if (properties.getLocaladmin() == null) {
            logger.warn("Localadmin was null from Pelias for stop place {}. Ignoring.", stopPlace.getName());
            return null;
        }
        return properties;
    }

    private void createAndSetRef(StopPlace stopPlace, TopographicPlace municipality) {
        TopographicPlaceRefStructure municipalityRef = new TopographicPlaceRefStructure();
        municipalityRef.setRef(String.valueOf(municipality.getId()));

        logger.trace("Setting reference to municipality {} : {} to stop place {}",
                municipality.getName(), municipalityRef.getRef(), stopPlace.getName());

        stopPlace.setTopographicPlaceRef(municipalityRef);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private TopographicPlace populateCountyAndMunicipality(String peliasCounty, String peliasLocalAdmin, AtomicInteger topographicPlacesCreatedCounter) throws InterruptedException {
        List<TopographicPlace> counties = topographicPlaceRepository
                .findByNameValueAndCountryRefRefAndTopographicPlaceType(
                        peliasCounty,
                        IanaCountryTldEnumeration.NO,
                        TopographicPlaceTypeEnumeration.COUNTY);
        logger.debug("Got {} counties for region {} from repository", counties.size(), peliasCounty);

        TopographicPlace county = createOrUseExistingCounty(counties, peliasCounty, topographicPlacesCreatedCounter);

        List<TopographicPlace> municipalities = topographicPlaceRepository
                .findByNameValueAndCountryRefRefAndTopographicPlaceType(
                        peliasLocalAdmin,
                        IanaCountryTldEnumeration.NO,
                        TopographicPlaceTypeEnumeration.TOWN);

        logger.debug("Got {} municipalities for locality {} from repository", counties.size(), peliasLocalAdmin);

        return createOrUseExistingMunicipality(municipalities, county, peliasLocalAdmin, peliasCounty, topographicPlacesCreatedCounter);
    }

    private TopographicPlace createOrUseExistingMunicipality(List<TopographicPlace> municipalities,
                                                             TopographicPlace county, String locality, String region, AtomicInteger topographicPlacesCreatedCounter) {

        TopographicPlace municipality;

        if (municipalities.isEmpty()) {
            logger.debug("Creating new municipality for locality {}", locality);

            municipality = new TopographicPlace();
            municipality.setName(new MultilingualString(locality, "no", ""));
            municipality.setTopographicPlaceType(TopographicPlaceTypeEnumeration.TOWN);

            TopographicPlaceRefStructure countyRef = new TopographicPlaceRefStructure();
            countyRef.setRef(String.valueOf(county.getId()));

            CountryRef countryRef = new CountryRef();
            countryRef.setRef(IanaCountryTldEnumeration.NO);
            municipality.setCountryRef(countryRef);

            logger.debug("Adding reference to county {} from municipality {}", region, locality);

            municipality.setParentTopographicPlaceRef(countyRef);
            topographicPlaceRepository.saveAndFlush(municipality);

            topographicPlacesCreatedCounter.incrementAndGet();
            logger.info("Created municipality {} with id: {}, referencing county {}", locality, municipality.getId(), county.getId());

        } else {
            municipality = municipalities.get(0);
            logger.debug("Found existing municipality {} with id {}", municipality.getName(), municipality.getId());
        }
        return municipality;
    }

    private TopographicPlace createOrUseExistingCounty(List<TopographicPlace> counties, String peliasCounty, AtomicInteger topographicPlacesCreatedCounter) {

        TopographicPlace county;

        if (counties.isEmpty()) {

            logger.debug("Creating new county from pelias county: {}", peliasCounty);
            county = new TopographicPlace();
            county.setName(new MultilingualString(peliasCounty, "no", ""));
            county.setTopographicPlaceType(TopographicPlaceTypeEnumeration.COUNTY);

            CountryRef countryRef = new CountryRef();
            countryRef.setRef(IanaCountryTldEnumeration.NO);
            county.setCountryRef(countryRef);

            topographicPlaceRepository.saveAndFlush(county);

            topographicPlacesCreatedCounter.incrementAndGet();
            logger.info("Created county {} with id: {}", peliasCounty, county.getId());
        } else {
            county = counties.get(0);
            logger.debug("Found existing county for region {}: {}", peliasCounty, county.getId());
        }
        return county;
    }

}