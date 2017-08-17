package org.rutebanken.tiamat.rest.graphql.fetchers;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.util.Preconditions;
import graphql.language.Field;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.rutebanken.helper.organisation.ReflectionAuthorizationService;
import org.rutebanken.tiamat.model.*;
import org.rutebanken.tiamat.repository.StopPlaceRepository;
import org.rutebanken.tiamat.rest.graphql.helpers.CleanupHelper;
import org.rutebanken.tiamat.rest.graphql.resolvers.GeometryResolver;
import org.rutebanken.tiamat.rest.graphql.resolvers.ValidBetweenMapper;
import org.rutebanken.tiamat.rest.graphql.scalars.TransportModeScalar;
import org.rutebanken.tiamat.service.TopographicPlaceLookupService;
import org.rutebanken.tiamat.versioning.StopPlaceVersionedSaverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

import static org.rutebanken.helper.organisation.AuthorizationConstants.ROLE_EDIT_STOPS;
import static org.rutebanken.tiamat.rest.graphql.GraphQLNames.*;
import static org.rutebanken.tiamat.rest.graphql.resolvers.ObjectResolver.getEmbeddableString;
import static org.rutebanken.tiamat.rest.graphql.resolvers.ObjectResolver.getPrivateCodeStructure;

@Service("stopPlaceUpdater")
@Transactional
class StopPlaceUpdater implements DataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(StopPlaceUpdater.class);

    @Autowired
    private StopPlaceVersionedSaverService stopPlaceVersionedSaverService;

    @Autowired
    private StopPlaceRepository stopPlaceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private GeometryResolver geometryResolver;

    @Autowired
    private TopographicPlaceLookupService topographicPlaceLookupService;

    @Autowired
    private ReflectionAuthorizationService authorizationService;

    @Autowired
    private ValidBetweenMapper validBetweenMapper;

    @Override
    public Object get(DataFetchingEnvironment environment) {
        List<Field> fields = environment.getFields();
        CleanupHelper.trimValues(environment.getArguments());
        StopPlace stopPlace = null;
        for (Field field : fields) {
            if (field.getName().equals(MUTATE_STOPPLACE)) {
                stopPlace = createOrUpdateStopPlace(environment);
            }
        }
        return Arrays.asList(stopPlace);
    }


    private StopPlace createOrUpdateStopPlace(DataFetchingEnvironment environment) {
        StopPlace updatedStopPlace;
        StopPlace existingVersion = null;

        if (environment.getArgument(OUTPUT_TYPE_STOPPLACE) != null) {
            Map input = environment.getArgument(OUTPUT_TYPE_STOPPLACE);

            String netexId = (String) input.get(ID);
            if (netexId != null) {
                logger.info("Updating StopPlace {}", netexId);
                existingVersion = stopPlaceRepository.findFirstByNetexIdOrderByVersionDesc(netexId);
                Preconditions.checkArgument(existingVersion != null, "Attempting to update StopPlace [id = %s], but StopPlace does not exist.", netexId);
                updatedStopPlace = stopPlaceVersionedSaverService.createCopy(existingVersion, StopPlace.class);

            } else {
                logger.info("Creating new StopPlace");
                updatedStopPlace = new StopPlace();
            }

            if (updatedStopPlace != null) {
                boolean hasValuesChanged = populateStopPlaceFromInput(input, updatedStopPlace);

                if (hasValuesChanged) {
                    authorizationService.assertAuthorized(ROLE_EDIT_STOPS, Arrays.asList(existingVersion, updatedStopPlace));

                    if(updatedStopPlace.getName() == null || Strings.isNullOrEmpty(updatedStopPlace.getName().getValue())) {
                        throw new IllegalArgumentException("Updated stop place must have name set: " + updatedStopPlace);
                    }
                    updatedStopPlace = stopPlaceVersionedSaverService.saveNewVersion(existingVersion, updatedStopPlace);

                    return updatedStopPlace;
                }
            }
        }
        return existingVersion;
    }

    /**
     * @param input
     * @param stopPlace
     * @return true if StopPlace or any og the attached Quays are updated
     */
    private boolean populateStopPlaceFromInput(Map input, StopPlace stopPlace) {
        boolean isUpdated = populate(input, stopPlace);

        if (input.get(STOP_PLACE_TYPE) != null) {
            stopPlace.setStopPlaceType((StopTypeEnumeration) input.get(STOP_PLACE_TYPE));
            isUpdated = true;
        }

        if (input.get(VERSION_COMMENT) != null) {
            stopPlace.setVersionComment((String) input.get(VERSION_COMMENT));
        }

        if (input.get(VALID_BETWEEN) != null) {
            stopPlace.setValidBetween(validBetweenMapper.map((Map) input.get(VALID_BETWEEN)));
            isUpdated = true;
        }
        if (input.get(WEIGHTING) != null) {
            stopPlace.setWeighting((InterchangeWeightingEnumeration) input.get(WEIGHTING));
            isUpdated = true;
        }
        if (input.get(PARENT_SITE_REF) != null) {
            SiteRefStructure parentSiteRef = new SiteRefStructure();
            parentSiteRef.setRef((String) input.get(PARENT_SITE_REF));
            stopPlace.setParentSiteRef(parentSiteRef);
            isUpdated = true;
        }

        isUpdated = isUpdated | setTransportModeSubMode(stopPlace, input.get(TRANSPORT_MODE), input.get(SUBMODE));

        if (input.get(QUAYS) != null) {
            List quays = (List) input.get(QUAYS);
            for (Object quayObject : quays) {

                Map quayInputMap = (Map) quayObject;
                if (populateQuayFromInput(stopPlace, quayInputMap)) {
                    isUpdated = true;
                } else {
                    logger.info("Quay not changed");
                }
            }
        }
        if (isUpdated) {
            stopPlace.setChanged(Instant.now());
        }

        return isUpdated;
    }

    private boolean setTransportModeSubMode(StopPlace stopPlace, Object transportMode, Object submode) {
        if (transportMode != null) {
            stopPlace.setTransportMode((VehicleModeEnumeration) transportMode);

            //Resetting all submodes
            stopPlace.setBusSubmode(null);
            stopPlace.setTramSubmode(null);
            stopPlace.setRailSubmode(null);
            stopPlace.setMetroSubmode(null);
            stopPlace.setAirSubmode(null);
            stopPlace.setWaterSubmode(null);
            stopPlace.setTelecabinSubmode(null);
            stopPlace.setFunicularSubmode(null);

            if (submode != null) {

                VehicleModeEnumeration stopPlaceTransportMode = stopPlace.getTransportMode();

                Preconditions.checkNotNull(stopPlaceTransportMode);
                List<String> validSubmodes = TransportModeScalar.getValidSubmodes(stopPlaceTransportMode.value());

                String errorMessage = "Submode " + submode + " is invalid for TransportMode " + stopPlaceTransportMode;

                if (submode instanceof BusSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((BusSubmodeEnumeration) submode).value()), errorMessage);
                    stopPlace.setBusSubmode((BusSubmodeEnumeration) submode);
                } else if (submode instanceof TramSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((TramSubmodeEnumeration) submode).value()), errorMessage);
                    stopPlace.setTramSubmode((TramSubmodeEnumeration) submode);
                } else if (submode instanceof RailSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((RailSubmodeEnumeration) submode).value()), errorMessage);
                    stopPlace.setRailSubmode((RailSubmodeEnumeration) submode);
                } else if (submode instanceof MetroSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((MetroSubmodeEnumeration) submode).value()), errorMessage);
                    stopPlace.setMetroSubmode((MetroSubmodeEnumeration) submode);
                } else if (submode instanceof AirSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((AirSubmodeEnumeration) submode).value()), errorMessage);
                    stopPlace.setAirSubmode((AirSubmodeEnumeration) submode);
                } else if (submode instanceof WaterSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((WaterSubmodeEnumeration) submode).value()), errorMessage);
                    stopPlace.setWaterSubmode((WaterSubmodeEnumeration) submode);
                } else if (submode instanceof TelecabinSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((TelecabinSubmodeEnumeration) submode).value()),errorMessage);
                    stopPlace.setTelecabinSubmode((TelecabinSubmodeEnumeration) submode);
                } else if (submode instanceof FunicularSubmodeEnumeration) {
                    Preconditions.checkArgument(validSubmodes.contains(((FunicularSubmodeEnumeration) submode).value()),errorMessage);
                    stopPlace.setFunicularSubmode((FunicularSubmodeEnumeration) submode);
                }
            }
            return true;
        }
        return false;
    }

    private boolean populateQuayFromInput(StopPlace stopPlace, Map quayInputMap) {
        Quay quay;
        if (quayInputMap.get(ID) != null) {
            Optional<Quay> existingQuay = stopPlace.getQuays().stream()
                    .filter(q -> q.getNetexId() != null)
                    .filter(q -> q.getNetexId().equals(quayInputMap.get(ID))).findFirst();

            Preconditions.checkArgument(existingQuay.isPresent(),
                    "Attempting to update Quay [id = %s] on StopPlace [id = %s] , but Quay does not exist on StopPlace",
                    quayInputMap.get(ID),
                    stopPlace.getNetexId());

            quay = existingQuay.get();
            logger.info("Updating Quay {} for StopPlace {}", quay.getNetexId(), stopPlace.getNetexId());
        } else {
            quay = new Quay();
            logger.info("Creating new Quay");
        }
        boolean isQuayUpdated = populate(quayInputMap, quay);

        if (quayInputMap.get(COMPASS_BEARING) != null) {
            quay.setCompassBearing(((BigDecimal) quayInputMap.get(COMPASS_BEARING)).floatValue());
            isQuayUpdated = true;
        }
        if (quayInputMap.get(PUBLIC_CODE) != null) {
            quay.setPublicCode((String) quayInputMap.get(PUBLIC_CODE));
            isQuayUpdated = true;
        }

        if(quayInputMap.get(PRIVATE_CODE) != null) {
            Map privateCodeInputMap = (Map) quayInputMap.get(PRIVATE_CODE);
            if(quay.getPrivateCode() == null) {
                quay.setPrivateCode(new PrivateCodeStructure());
            }
            quay.getPrivateCode().setType((String) privateCodeInputMap.get(TYPE));
            quay.getPrivateCode().setValue((String) privateCodeInputMap.get(VALUE));
            isQuayUpdated = true;
        }

        if (isQuayUpdated) {
            quay.setChanged(Instant.now());

            if (quay.getNetexId() == null) {
                stopPlace.getQuays().add(quay);
            }
        }
        return isQuayUpdated;
    }

    private boolean populate(Map input, SiteElement entity) {
        boolean isUpdated = false;

        if (input.get(NAME) != null) {
            entity.setName(getEmbeddableString((Map) input.get(NAME)));
            isUpdated = true;
        }
        if (input.get(SHORT_NAME) != null) {
            entity.setShortName(getEmbeddableString((Map) input.get(SHORT_NAME)));
            isUpdated = true;
        }
        if (input.get(DESCRIPTION) != null) {
            entity.setDescription(getEmbeddableString((Map) input.get(DESCRIPTION)));
            isUpdated = true;
        }


        if (input.get(KEY_VALUES) != null) {
            List<Map> keyValues = (List) input.get(KEY_VALUES);

            entity.getKeyValues().clear();

            keyValues.forEach(inputMap-> {
                String key = (String)inputMap.get(KEY);
                List<String> values = (List<String>)inputMap.get(VALUES);

                Value value = new Value(values);
                entity.getKeyValues().put(key, value);
            });

            isUpdated = true;
        }

        if (input.get(ALTERNATIVE_NAMES) != null) {
            List alternativeNames = (List) input.get(ALTERNATIVE_NAMES);
            for (Object alternativeNameObject : alternativeNames) {
                Map alternativeNameInputMap = (Map) alternativeNameObject;
                if (populateAlternativeNameFromInput(entity, alternativeNameInputMap)) {
                    isUpdated = true;
                } else {
                    logger.info("AlternativeName not changed");
                }
            }
        }
        if (input.get(GEOMETRY) != null) {
            entity.setCentroid(geometryResolver.createGeoJsonPoint((Map) input.get(GEOMETRY)));

            if (entity instanceof StopPlace) {
                try {
                    topographicPlaceLookupService.populateTopographicPlaceRelation((StopPlace) entity);
                } catch (Exception e) {
                    logger.warn("Setting TopographicPlace on StopPlace failed", e);
                }
            }


            isUpdated = true;
        }

        if (input.get(PLACE_EQUIPMENTS) != null) {
            PlaceEquipment equipments = new PlaceEquipment();

            Map<String, Object> equipmentInput = (Map) input.get(PLACE_EQUIPMENTS);

            if (equipmentInput.get(SANITARY_EQUIPMENT) != null) {

                List equipment = (List) equipmentInput.get(SANITARY_EQUIPMENT);
                for (Object item : equipment) {
                    Map<String, Object> sanitaryEquipment = (Map<String, Object>) item;

                    SanitaryEquipment toalett = new SanitaryEquipment();
                    toalett.setNumberOfToilets((BigInteger) sanitaryEquipment.get(NUMBER_OF_TOILETS));
                    toalett.setGender((GenderLimitationEnumeration) sanitaryEquipment.get(GENDER));
                    equipments.getInstalledEquipment().add(toalett);
                }
            }

            if (equipmentInput.get(SHELTER_EQUIPMENT) != null) {

                List equipment = (List) equipmentInput.get(SHELTER_EQUIPMENT);
                for (Object item : equipment) {
                    Map<String, Object> shelterEquipment = (Map<String, Object>) item;
                    ShelterEquipment leskur = new ShelterEquipment();
                    leskur.setEnclosed((Boolean) shelterEquipment.get(ENCLOSED));
                    leskur.setSeats((BigInteger) shelterEquipment.get(SEATS));
                    leskur.setStepFree((Boolean) shelterEquipment.get(STEP_FREE));
                    equipments.getInstalledEquipment().add(leskur);
                }
            }

            if (equipmentInput.get(CYCLE_STORAGE_EQUIPMENT) != null) {

                List equipment = (List) equipmentInput.get(CYCLE_STORAGE_EQUIPMENT);
                for (Object item : equipment) {
                    Map<String, Object> cycleStorageEquipment = (Map<String, Object>) item;
                    CycleStorageEquipment sykkelskur = new CycleStorageEquipment();
                    sykkelskur.setNumberOfSpaces((BigInteger) cycleStorageEquipment.get(NUMBER_OF_SPACES));
                    sykkelskur.setCycleStorageType((CycleStorageEnumeration) cycleStorageEquipment.get(CYCLE_STORAGE_TYPE));
                    equipments.getInstalledEquipment().add(sykkelskur);
                }
            }

            if (equipmentInput.get(WAITING_ROOM_EQUIPMENT) != null) {

                List equipment = (List) equipmentInput.get(WAITING_ROOM_EQUIPMENT);
                for (Object item : equipment) {
                    Map<String, Object> waitingRoomEquipment = (Map<String, Object>) item;
                    WaitingRoomEquipment venterom = new WaitingRoomEquipment();
                    venterom.setSeats((BigInteger) waitingRoomEquipment.get(SEATS));
                    venterom.setHeated((Boolean) waitingRoomEquipment.get(HEATED));
                    venterom.setStepFree((Boolean) waitingRoomEquipment.get(STEP_FREE));
                    equipments.getInstalledEquipment().add(venterom);
                }
            }

            if (equipmentInput.get(TICKETING_EQUIPMENT) != null) {

                List equipment = (List) equipmentInput.get(TICKETING_EQUIPMENT);
                for (Object item : equipment) {
                    Map<String, Object> ticketingEquipment = (Map<String, Object>) item;
                    TicketingEquipment billettAutomat = new TicketingEquipment();
                    billettAutomat.setTicketOffice((Boolean) ticketingEquipment.get(TICKET_OFFICE));
                    billettAutomat.setTicketMachines((Boolean) ticketingEquipment.get(TICKET_MACHINES));
                    billettAutomat.setNumberOfMachines((BigInteger) ticketingEquipment.get(NUMBER_OF_MACHINES));
                    equipments.getInstalledEquipment().add(billettAutomat);
                }
            }

            if (equipmentInput.get(GENERAL_SIGN) != null) {

                List equipment = (List) equipmentInput.get(GENERAL_SIGN);
                for (Object item : equipment) {

                    Map<String, Object> generalSignEquipment = (Map<String, Object>) item;

                    GeneralSign skilt = new GeneralSign();
                    skilt.setPrivateCode(getPrivateCodeStructure((Map) generalSignEquipment.get(PRIVATE_CODE)));
                    skilt.setContent(getEmbeddableString((Map) generalSignEquipment.get(CONTENT)));
                    skilt.setSignContentType((SignContentEnumeration) generalSignEquipment.get(SIGN_CONTENT_TYPE));
                    equipments.getInstalledEquipment().add(skilt);
                }
            }



            if (entity instanceof StopPlace) {
                ((StopPlace)entity).setPlaceEquipments(equipments);
            } else if (entity instanceof Quay) {
                ((Quay)entity).setPlaceEquipments(equipments);
            }
            isUpdated = true;
        }

        if (input.get(ACCESSIBILITY_ASSESSMENT) != null) {
            AccessibilityAssessment accessibilityAssessment = entity.getAccessibilityAssessment();
            if (accessibilityAssessment == null) {
                accessibilityAssessment = new AccessibilityAssessment();
            }

            Map<String, Object> accessibilityAssessmentInput = (Map) input.get(ACCESSIBILITY_ASSESSMENT);
            List<AccessibilityLimitation> limitations = accessibilityAssessment.getLimitations();
            AccessibilityLimitation limitation;
            if (limitations == null || limitations.isEmpty()) {
                limitations = new ArrayList<>();
                limitation = new AccessibilityLimitation();
            } else {
                limitation = limitations.get(0);
            }

            AccessibilityLimitation limitationFromInput = createAccessibilityLimitationFromInput((Map<String, LimitationStatusEnumeration>) accessibilityAssessmentInput.get("limitations"));

            //Only flag as updated if limitations are updated
            if (limitationFromInput.getWheelchairAccess() != limitation.getWheelchairAccess() |
                    limitationFromInput.getAudibleSignalsAvailable() != limitation.getAudibleSignalsAvailable() |
                    limitationFromInput.getStepFreeAccess() != limitation.getStepFreeAccess() |
                    limitationFromInput.getLiftFreeAccess() != limitation.getLiftFreeAccess() |
                    limitationFromInput.getEscalatorFreeAccess() != limitation.getEscalatorFreeAccess()) {

                limitation.setWheelchairAccess(limitationFromInput.getWheelchairAccess());
                limitation.setAudibleSignalsAvailable(limitationFromInput.getAudibleSignalsAvailable());
                limitation.setStepFreeAccess(limitationFromInput.getStepFreeAccess());
                limitation.setLiftFreeAccess(limitationFromInput.getLiftFreeAccess());
                limitation.setEscalatorFreeAccess(limitationFromInput.getEscalatorFreeAccess());


                limitations.clear();
                limitations.add(limitation);
                accessibilityAssessment.setLimitations(limitations);

                entity.setAccessibilityAssessment(accessibilityAssessment);

                isUpdated = true;
            }
        }
        return isUpdated;
    }

    private AccessibilityLimitation createAccessibilityLimitationFromInput(Map<String, LimitationStatusEnumeration> limitationsInput) {
        AccessibilityLimitation limitation = new AccessibilityLimitation();
        limitation.setWheelchairAccess(limitationsInput.get(WHEELCHAIR_ACCESS));
        limitation.setAudibleSignalsAvailable(limitationsInput.get(AUDIBLE_SIGNALS_AVAILABLE));
        limitation.setStepFreeAccess(limitationsInput.get(STEP_FREE_ACCESS));
        limitation.setLiftFreeAccess(limitationsInput.get(LIFT_FREE_ACCESS));
        limitation.setEscalatorFreeAccess(limitationsInput.get(ESCALATOR_FREE_ACCESS));
        return limitation;
    }

    private boolean populateAlternativeNameFromInput(SiteElement entity, Map entry) {
        boolean isUpdated = false;
        AlternativeName altName;

        NameTypeEnumeration nameType = (NameTypeEnumeration) entry.getOrDefault(NAME_TYPE, NameTypeEnumeration.OTHER);
        EmbeddableMultilingualString name = getEmbeddableString((Map) entry.get(NAME));

        if (name != null) {

            Optional<AlternativeName> existing = entity.getAlternativeNames()
                    .stream()
                    .filter(alternativeName -> alternativeName != null)
                    .filter(alternativeName -> alternativeName.getName() != null)
                    .filter(alternativeName -> {
                            return (alternativeName.getName().getLang() != null &&
                                    alternativeName.getName().getLang().equals(name.getLang()) &&
                                    alternativeName.getNameType() != null && alternativeName.getNameType().equals(nameType));
                    })
                    .findFirst();
            if (existing.isPresent()) {
                altName = existing.get();
            } else {
                altName = new AlternativeName();
            }
            if (name.getValue() != null) {
                altName.setName(name);
                altName.setNameType(nameType);
                isUpdated = true;
            }

            if (altName.getName() == null || altName.getName().getValue() == null || altName.getName().getValue().isEmpty()) {
                entity.getAlternativeNames().remove(altName);
            } else if (isUpdated && altName.getNetexId() == null) {
                entity.getAlternativeNames().add(altName);
            }
        }

        return isUpdated;
    }
}
