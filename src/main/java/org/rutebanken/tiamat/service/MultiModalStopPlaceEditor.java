package org.rutebanken.tiamat.service;

import org.rutebanken.helper.organisation.ReflectionAuthorizationService;
import org.rutebanken.tiamat.model.EmbeddableMultilingualString;
import org.rutebanken.tiamat.model.SiteRefStructure;
import org.rutebanken.tiamat.model.StopPlace;
import org.rutebanken.tiamat.repository.StopPlaceRepository;
import org.rutebanken.tiamat.versioning.StopPlaceVersionedSaverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.rutebanken.helper.organisation.AuthorizationConstants.ROLE_EDIT_STOPS;

@Transactional
@Component
public class MultiModalStopPlaceEditor {

    private static final Logger logger = LoggerFactory.getLogger(MultiModalStopPlaceEditor.class);

    @Autowired
    private StopPlaceVersionedSaverService stopPlaceVersionedSaverService;

    @Autowired
    private StopPlaceRepository stopPlaceRepository;

    @Autowired
    private ReflectionAuthorizationService authorizationService;


    public StopPlace createMultiModalParentStopPlace(List<String> childStopPlaceIds, EmbeddableMultilingualString name) {

        // Fetch max versions of future child stop places
        List<StopPlace> futureChildStopPlaces = childStopPlaceIds.stream().map(id -> stopPlaceRepository.findFirstByNetexIdOrderByVersionDesc(id)).collect(Collectors.toList());

        authorizationService.assertAuthorized(ROLE_EDIT_STOPS, futureChildStopPlaces);

        logger.info("Saving first version of parent stop place {}", name);
        StopPlace parentStopPlace = stopPlaceVersionedSaverService.saveNewVersion(new StopPlace(name));

        // Terminate last version of stop place. Create new version with parent site ref

        futureChildStopPlaces.forEach(existingVersion -> {

            if(existingVersion.getValidBetween() != null) {
                if(existingVersion.getValidBetween().getFromDate() != null && existingVersion.getValidBetween().getFromDate().isAfter(Instant.now())) {
                    throw new RuntimeException("The stop place " + existingVersion.getNetexId() + " version " + existingVersion.getVersion() + " is not currently valid: from date = " + existingVersion.getValidBetween().getFromDate());
                }
                if(existingVersion.getValidBetween().getToDate() != null && existingVersion.getValidBetween().getToDate().isBefore(Instant.now())) {
                    throw new RuntimeException("The stop place " + existingVersion.getNetexId() + " version " + existingVersion.getVersion() + " is not currently valid: to date = " + existingVersion.getValidBetween().getToDate());
                }
            }
            logger.info("Adding child stop place {} to parent stop place {}", existingVersion, parentStopPlace);
            StopPlace stopPlaceCopy = stopPlaceVersionedSaverService.createCopy(existingVersion, StopPlace.class);
            SiteRefStructure siteRefStructure = new SiteRefStructure();
            siteRefStructure.setRef(parentStopPlace.getNetexId());
            siteRefStructure.setVersion(String.valueOf(parentStopPlace.getVersion()));

            stopPlaceCopy.setParentSiteRef(siteRefStructure);
            stopPlaceCopy.setName(null);
            stopPlaceVersionedSaverService.saveNewVersion(existingVersion, stopPlaceCopy);

        });
        return parentStopPlace;
    }

    public StopPlace addToMultiModalParentStopPlace(String parentStopPlaceId, List<String> childStopPlaceIds) {
        // What happens if you have a new version of the parent stop place?: Then the child stop place should be bumped as well
        StopPlace parentStopPlace = stopPlaceRepository.findFirstByNetexIdOrderByVersionDesc(parentStopPlaceId);
        authorizationService.assertAuthorized(ROLE_EDIT_STOPS, Arrays.asList(parentStopPlace));

        List<StopPlace> stopPlaces = stopPlaceRepository.findAll(childStopPlaceIds);
        authorizationService.assertAuthorized(ROLE_EDIT_STOPS, stopPlaces);

        stopPlaces.forEach(stopPlace -> {
            SiteRefStructure siteRefStructure = new SiteRefStructure();
            siteRefStructure.setRef(parentStopPlace.getNetexId());
            stopPlace.setParentSiteRef(siteRefStructure);
        });

        return parentStopPlace;
    }

    public StopPlace removeFromMultiModalStopPlace(String parentStopPlaceId, List<String> childStopPlaceIds) {

        StopPlace parentStopPlace = stopPlaceRepository.findFirstByNetexIdOrderByVersionDesc(parentStopPlaceId);
        authorizationService.assertAuthorized(ROLE_EDIT_STOPS, Arrays.asList(parentStopPlace));

        List<StopPlace> stopPlaces = stopPlaceRepository.findAll(childStopPlaceIds);
        authorizationService.assertAuthorized(ROLE_EDIT_STOPS, stopPlaces);

        stopPlaces.forEach(stopPlace -> {
            SiteRefStructure parentSiteRef = stopPlace.getParentSiteRef();
            if (parentSiteRef != null && parentSiteRef.getRef().equals(parentStopPlaceId)) {
                stopPlace.setParentSiteRef(null);
            }
        });

        return parentStopPlace;
    }
}
