package org.rutebanken.tiamat.importers;

import org.rutebanken.tiamat.model.Quay;
import org.rutebanken.tiamat.model.SiteFrame;
import org.rutebanken.tiamat.model.StopPlace;
import org.rutebanken.tiamat.netex.mapping.mapper.NetexIdMapper;
import org.rutebanken.tiamat.pelias.CountyAndMunicipalityLookupService;
import org.rutebanken.tiamat.repository.QuayRepository;
import org.rutebanken.tiamat.repository.StopPlaceRepository;
import org.rutebanken.tiamat.service.CentroidComputer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Qualifier("defaultStopPlaceImporter")
@Transactional
public class DefaultStopPlaceImporter implements StopPlaceImporter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultStopPlaceImporter.class);

    private final TopographicPlaceCreator topographicPlaceCreator;

    private final CountyAndMunicipalityLookupService countyAndMunicipalityLookupService;

    private final QuayRepository quayRepository;

    private final StopPlaceRepository stopPlaceRepository;

    private final StopPlaceFromOriginalIdFinder stopPlaceFromOriginalIdFinder;

    private final NearbyStopPlaceFinder nearbyStopPlaceFinder;

    private final CentroidComputer centroidComputer;

    private final KeyValueListAppender keyValueListAppender;

    private final QuayMerger quayMerger;


    @Autowired
    public DefaultStopPlaceImporter(TopographicPlaceCreator topographicPlaceCreator,
                                    CountyAndMunicipalityLookupService countyAndMunicipalityLookupService,
                                    QuayRepository quayRepository, StopPlaceRepository stopPlaceRepository,
                                    StopPlaceFromOriginalIdFinder stopPlaceFromOriginalIdFinder,
                                    NearbyStopPlaceFinder nearbyStopPlaceFinder,
                                    CentroidComputer centroidComputer,
                                    KeyValueListAppender keyValueListAppender, QuayMerger quayMerger) {
        this.topographicPlaceCreator = topographicPlaceCreator;
        this.countyAndMunicipalityLookupService = countyAndMunicipalityLookupService;
        this.quayRepository = quayRepository;
        this.stopPlaceRepository = stopPlaceRepository;
        this.stopPlaceFromOriginalIdFinder = stopPlaceFromOriginalIdFinder;
        this.nearbyStopPlaceFinder = nearbyStopPlaceFinder;
        this.centroidComputer = centroidComputer;
        this.keyValueListAppender = keyValueListAppender;
        this.quayMerger = quayMerger;
    }

    @Override
    public StopPlace importStopPlace(StopPlace newStopPlace, SiteFrame siteFrame,
                                     AtomicInteger topographicPlacesCreatedCounter) throws InterruptedException, ExecutionException {
        logger.info("Import stop place {}", newStopPlace);

        final StopPlace foundStopPlace = findNearbyOrExistingStopPlace(newStopPlace);

        final StopPlace stopPlace;
        if (foundStopPlace != null) {
            stopPlace = handleAlreadyExistingStopPlace(foundStopPlace, newStopPlace);
        } else {
            stopPlace = handleCompletelyNewStopPlace(newStopPlace, siteFrame, topographicPlacesCreatedCounter);
        }
        return stopPlace;
    }


    public StopPlace handleCompletelyNewStopPlace(StopPlace newStopPlace, SiteFrame siteFrame, AtomicInteger topographicPlacesCreatedCounter) throws ExecutionException {

        if(newStopPlace.getId() != null) {
            newStopPlace.setId(null);
            if(newStopPlace.getQuays() != null) {
                newStopPlace.getQuays().forEach(q -> q.setId(null));
            }
        }
        if (hasTopographicPlaces(siteFrame)) {
            topographicPlaceCreator.setTopographicReference(newStopPlace,
                    siteFrame.getTopographicPlaces().getTopographicPlace(),
                    topographicPlacesCreatedCounter);
        } else {
            lookupCountyAndMunicipality(newStopPlace, topographicPlacesCreatedCounter);
        }
        if(newStopPlace.getQuays() != null) {
            Set<Quay> quays = quayMerger.addNewQuaysOrAppendImportIds(newStopPlace.getQuays(), null, new AtomicInteger(), new AtomicInteger());
            newStopPlace.setQuays(quays);
            logger.info("Importing quays for new stop place {}", newStopPlace);
        }

        centroidComputer.computeCentroidForStopPlace(newStopPlace);
        // Ignore incoming version. Always set version to 1 for new stop places.
        logger.info("New stop place: {}. Setting version to \"1\"", newStopPlace.getName());
        newStopPlace.setVersion("1");
        return saveAndUpdateCache(newStopPlace);
    }

    public StopPlace handleAlreadyExistingStopPlace(StopPlace foundStopPlace, StopPlace newStopPlace) {
        logger.info("Found existing stop place {} from incoming {}", foundStopPlace, newStopPlace);

        quayMerger.addNewQuaysOrAppendImportIds(newStopPlace, foundStopPlace);
        keyValueListAppender.appendToOriginalId(NetexIdMapper.ORIGINAL_ID_KEY, newStopPlace, foundStopPlace);
        centroidComputer.computeCentroidForStopPlace(foundStopPlace);

        logger.info("Updated existing stop place {}. ", foundStopPlace);
        foundStopPlace.getQuays().forEach(q -> logger.info("Stop place {}:  Quay {}: {}", foundStopPlace.getId(), q.getId(), q.getName()));
        incrementVersion(foundStopPlace);

        return saveAndUpdateCache(foundStopPlace);
    }

    private void incrementVersion(StopPlace stopPlace) {
        Long version = tryParseLong(stopPlace.getVersion());
        version ++;
        logger.info("Setting version {} for stop place {}", version, stopPlace.getName());
        stopPlace.setVersion(version.toString());
    }

    private long tryParseLong(String version) {
        try {
            return Long.parseLong(version);
        } catch(NumberFormatException |NullPointerException e) {
            logger.warn("Could not parse version from string {}. Returning 0", version);
            return 0L;
        }
    }

    private StopPlace saveAndUpdateCache(StopPlace stopPlace) {
        if(stopPlace.getId() == null) {
            stopPlaceRepository.save(stopPlace);
        }
        if(stopPlace.getQuays() != null) {
            for (Quay quay : stopPlace.getQuays()) {
                if (quay.getId() == null) {
                    quayRepository.save(quay);
                }
            }
        }

        stopPlaceFromOriginalIdFinder.update(stopPlace);
        nearbyStopPlaceFinder.update(stopPlace);
        logger.info("Saved stop place {}", stopPlace);
        return stopPlace;
    }

    private boolean hasTopographicPlaces(SiteFrame siteFrame) {
        return siteFrame.getTopographicPlaces() != null
                && siteFrame.getTopographicPlaces().getTopographicPlace() != null
                && !siteFrame.getTopographicPlaces().getTopographicPlace().isEmpty();
    }

    private void lookupCountyAndMunicipality(StopPlace stopPlace, AtomicInteger topographicPlacesCreatedCounter) {
        try {
            countyAndMunicipalityLookupService.populateCountyAndMunicipality(stopPlace, topographicPlacesCreatedCounter);
        } catch (IOException | InterruptedException e) {
            logger.warn("Could not lookup county and municipality for stop place with id {}", stopPlace.getId());
        }
    }

    private StopPlace findNearbyOrExistingStopPlace(StopPlace newStopPlace) {
        final StopPlace existingStopPlace = stopPlaceFromOriginalIdFinder.find(newStopPlace);
        if (existingStopPlace != null) {
            return existingStopPlace;
        }

        if (newStopPlace.getName() != null) {
            final StopPlace nearbyStopPlace = nearbyStopPlaceFinder.find(newStopPlace);
            if (nearbyStopPlace != null) {
                logger.debug("Found nearby stop place with name: {}, id: {}", nearbyStopPlace.getName(), nearbyStopPlace.getId());
                return nearbyStopPlace;
            }
        }
        return null;
    }

}
