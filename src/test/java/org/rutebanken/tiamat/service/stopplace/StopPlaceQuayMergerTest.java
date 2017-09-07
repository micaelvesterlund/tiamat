package org.rutebanken.tiamat.service.stopplace;

import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Test;
import org.rutebanken.tiamat.TiamatIntegrationTest;
import org.rutebanken.tiamat.model.*;
import org.rutebanken.tiamat.netex.mapping.mapper.NetexIdMapper;
import org.rutebanken.tiamat.service.stopplace.StopPlaceQuayMerger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.rutebanken.tiamat.netex.mapping.mapper.NetexIdMapper.MERGED_ID_KEY;

public class StopPlaceQuayMergerTest extends TiamatIntegrationTest {

    @Autowired
    private StopPlaceQuayMerger stopPlaceQuayMerger;

    /**
     * Test added to reproduce/verify NRP-1791
     */
    @Test
    @Transactional
    public void testMergeQuaysWithEmptyNonNullValuesInTarget() {

        StopPlace fromStopPlace = new StopPlace();
        fromStopPlace.setName(new EmbeddableMultilingualString("Name"));

        Quay fromQuay = new Quay();
        fromQuay.setPublicCode("A");
        fromQuay.setPrivateCode(new PrivateCodeStructure("", "test"));

        Quay toQuay = new Quay();
        toQuay.setPublicCode("");
        toQuay.setPrivateCode(new PrivateCodeStructure("B", ""));

        fromStopPlace.getQuays().add(fromQuay);
        fromStopPlace.getQuays().add(toQuay);

        stopPlaceVersionedSaverService.saveNewVersion(fromStopPlace);

        StopPlace stopPlaceWithMergedQuays = stopPlaceQuayMerger.mergeQuays(fromStopPlace.getNetexId(), fromQuay.getNetexId(), toQuay.getNetexId(), null, false);

        assertThat(stopPlaceWithMergedQuays).isNotNull();

        // assertQuays
        assertThat(stopPlaceWithMergedQuays.getQuays()).hasSize(1);
        Quay quay = stopPlaceWithMergedQuays.getQuays().iterator().next();

        assertThat(quay.getNetexId()).isEqualTo(toQuay.getNetexId());

        assertThat(quay.getVersion()).isEqualTo(1 + toQuay.getVersion());
        assertThat(quay.getPublicCode()).isEqualTo(fromQuay.getPublicCode());
        assertThat(quay.getPrivateCode().getType()).isEqualTo(fromQuay.getPrivateCode().getType());
        assertThat(quay.getPrivateCode().getValue()).isEqualTo(toQuay.getPrivateCode().getValue());
        assertThat(quay.getOrCreateValues(MERGED_ID_KEY)).contains(fromQuay.getNetexId());
    }

    @Test
    @Transactional
    public void testMergeQuays() {

        StopPlace fromStopPlace = new StopPlace();
        fromStopPlace.setName(new EmbeddableMultilingualString("Name"));
        fromStopPlace.setCentroid(geometryFactory.createPoint(new Coordinate(11.1, 60.1)));
        fromStopPlace.getOriginalIds().add("TEST:StopPlace:1234");
        fromStopPlace.getOriginalIds().add("TEST:StopPlace:5678");

        Quay fromQuay = new Quay();
        fromQuay.setCompassBearing(new Float(90));
        fromQuay.setCentroid(geometryFactory.createPoint(new Coordinate(11.2, 60.2)));
        fromQuay.getOriginalIds().add("TEST:Quay:123401");
        fromQuay.getOriginalIds().add("TEST:Quay:567801");
        fromQuay.setPublicCode("A");

        String testKey = "testKey";
        String testValue = "testValue";
        fromQuay.getKeyValues().put(testKey, new Value(testValue));

        PlaceEquipment equipment = new PlaceEquipment();
        ShelterEquipment shelter = new ShelterEquipment();
        shelter.setSeats(BigInteger.ONE);
        shelter.setAirConditioned(false);
        equipment.getInstalledEquipment().add(shelter);
        fromQuay.setPlaceEquipments(equipment);

        Quay toQuay = new Quay();
        toQuay.setCentroid(geometryFactory.createPoint(new Coordinate(11.21, 60.21)));
        toQuay.getOriginalIds().add("TEST:Quay:432101");
        toQuay.getOriginalIds().add("TEST:Quay:876501");
        toQuay.setPublicCode("");

        Quay quayToKeepUnaltered = new Quay();
        quayToKeepUnaltered.setCompassBearing(new Float(180));
        quayToKeepUnaltered.setCentroid(geometryFactory.createPoint(new Coordinate(11.211, 60.211)));
        quayToKeepUnaltered.getOriginalIds().add("TEST:Quay:432102");

        fromStopPlace.getQuays().add(fromQuay);
        fromStopPlace.getQuays().add(toQuay);
        fromStopPlace.getQuays().add(quayToKeepUnaltered);

        stopPlaceVersionedSaverService.saveNewVersion(fromStopPlace);

        StopPlace stopPlaceWithMergedQuays = stopPlaceQuayMerger.mergeQuays(fromStopPlace.getNetexId(), fromQuay.getNetexId(), toQuay.getNetexId(), null, false);

        assertThat(stopPlaceWithMergedQuays).isNotNull();
        assertThat(stopPlaceWithMergedQuays.getOriginalIds()).isNotEmpty();

        // assertQuays
        assertThat(stopPlaceWithMergedQuays.getQuays()).hasSize(2);
        stopPlaceWithMergedQuays.getQuays().forEach(quay -> {
            if (quay.getNetexId().equals(toQuay.getNetexId())){

                assertThat(quay.getVersion()).isEqualTo(1 + toQuay.getVersion());

                assertThat(quay.getCompassBearing()).isEqualTo(fromQuay.getCompassBearing());
                assertThat(quay.getKeyValues().get(testKey)).isNotNull();
                assertThat(quay.getKeyValues().get(testKey).getItems()).contains(testValue);

                assertThat(quay.getPlaceEquipments()).isNotNull();
                assertThat(quay.getPlaceEquipments().getInstalledEquipment()).isNotNull();
                assertThat(quay.getPlaceEquipments().getInstalledEquipment()).hasSize(1);
                assertThat(quay.getPlaceEquipments().getInstalledEquipment().get(0)).isInstanceOf(ShelterEquipment.class);

                assertThat(!quay.equals(toQuay));
                assertThat(!quay.equals(fromQuay));

            } else if (quay.getNetexId().equals(quayToKeepUnaltered.getNetexId())){

                assertThat(quay.equals(quayToKeepUnaltered));

            } else {
                fail("Unknown Quay has been added");
            }
        });

        StopPlace firstVersion = stopPlaceRepository.findFirstByNetexIdAndVersion(fromStopPlace.getNetexId(), 1);
        StopPlace secondVersion = stopPlaceRepository.findFirstByNetexIdAndVersion(fromStopPlace.getNetexId(), 2);

        assertThat(firstVersion).isNotNull();
        assertThat(secondVersion).isNotNull();
        assertThat(firstVersion.getValidBetween().getToDate()).as("first version to date should be after after from date").isAfter(firstVersion.getValidBetween().getFromDate());
        assertThat(secondVersion.getValidBetween().getToDate()).as("second version to date").isNull();
        assertThat(firstVersion.getQuays()).hasSize(3);
        assertThat(secondVersion.getQuays()).hasSize(2);

    }
}
