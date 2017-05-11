package org.rutebanken.tiamat.importer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import org.junit.Ignore;
import org.junit.Test;
import org.rutebanken.tiamat.TiamatIntegrationTest;
import org.rutebanken.tiamat.model.EmbeddableMultilingualString;
import org.rutebanken.tiamat.model.Parking;
import org.rutebanken.tiamat.model.ParkingTypeEnumeration;
import org.rutebanken.tiamat.model.ParkingVehicleEnumeration;
import org.rutebanken.tiamat.netex.mapping.mapper.NetexIdMapper;
import org.rutebanken.tiamat.versioning.ParkingVersionedSaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test stop place importer with geodb and repository.
 * See also {@link MergingParkingImporterTest}
 */
@Transactional
public class MergingParkingImporterTest extends TiamatIntegrationTest {

    @Autowired
    private MergingParkingImporter mergingParkingImporter;

    @Autowired
    private ParkingVersionedSaverService parkingVersionedSaverService;

    /**
     * Two parkingss with the same name and coordinates should become one stop place.
     */
    @Test
    public void parkingsWithSameCoordinatesMustNotBeAddedMultipleTimes() throws ExecutionException, InterruptedException {
        String name = "Ski stasjon";

        double parkingLatitude = 59.422556;
        double parkingLongitude = 5.265704;

        Parking firstParking = createParking(name,
                parkingLongitude, parkingLatitude, null);
        firstParking.setParkingType(ParkingTypeEnumeration.PARK_AND_RIDE);
        firstParking.getParkingVehicleTypes().add(ParkingVehicleEnumeration.CAR);

        // Import first stop place.
        Parking firstImportResult = mergingParkingImporter.importParkingWithoutNetexMapping(firstParking);

        Parking secondParking = createParking(name,
                parkingLongitude, parkingLatitude, null);
        secondParking.setParkingType(ParkingTypeEnumeration.PARK_AND_RIDE);
        secondParking.getParkingVehicleTypes().add(ParkingVehicleEnumeration.CAR);
        secondParking.getParkingVehicleTypes().add(ParkingVehicleEnumeration.MINIBUS);

        // Import second stop place
        Parking importResult = mergingParkingImporter.importParkingWithoutNetexMapping(secondParking);

        assertThat(importResult.getNetexId()).isEqualTo(firstImportResult.getNetexId());
        assertThat(importResult.getVersion()).isGreaterThan(firstImportResult.getVersion());

        assertThat(importResult.getParkingVehicleTypes().size()).isEqualTo(secondParking.getParkingVehicleTypes().size());
    }

    /**
     * The second time the stop place is imported, the type must be updated if it was empty.
     */
    @Test
    @Ignore(value = "Causes GaplessIdGeneratorServiceTest to fail with ConstraintViolationException")
    public void updateParkingType() throws ExecutionException, InterruptedException {

        Point point = point(10.7096245, 59.9086885);

        Parking firstParking = new Parking();
        firstParking.setCentroid(point);
        firstParking.setName(new EmbeddableMultilingualString("Ski stasjon", "no"));
        firstParking.getOrCreateValues(NetexIdMapper.ORIGINAL_ID_KEY).add("original-id-ski");
        firstParking.setParkingType(ParkingTypeEnumeration.ROADSIDE);
        firstParking.setVersion(1L);

        parkingVersionedSaverService.saveNewVersion(firstParking);

        Parking newParking = new Parking();
        newParking.setCentroid(point);
        newParking.setName(new EmbeddableMultilingualString("Ski stasjon", "no"));
        newParking.getOrCreateValues(NetexIdMapper.ORIGINAL_ID_KEY).add("original-id-ski");
        newParking.setParkingType(ParkingTypeEnumeration.PARK_AND_RIDE);

        Parking importResult = mergingParkingImporter.importParkingWithoutNetexMapping(newParking);

        assertThat(importResult.getNetexId()).isEqualTo(firstParking.getNetexId());
        assertThat(importResult.getParkingType()).isEqualTo(ParkingTypeEnumeration.PARK_AND_RIDE);
    }

    @Test
    @Ignore(value = "Causes GaplessIdGeneratorServiceTest to fail with ConstraintViolationException")
    public void detectAndMergeParkingVehicleTypesFromTwoSimilarParkings() throws ExecutionException, InterruptedException {

        Parking firstParking = new Parking();
        firstParking.setCentroid(point(60.000, 10.78));
        firstParking.setName(new EmbeddableMultilingualString("Andalsnes", "no"));
        firstParking.setVersion(1L);
        firstParking.getParkingVehicleTypes().add(ParkingVehicleEnumeration.PEDAL_CYCLE);
        firstParking.setParkingType(ParkingTypeEnumeration.PARK_AND_RIDE);

        parkingVersionedSaverService.saveNewVersion(firstParking);

        Parking secondParking = new Parking();
        secondParking.setCentroid(point(60.000, 10.78));
        secondParking.setName(new EmbeddableMultilingualString("Andalsnes", "no"));
        secondParking.getParkingVehicleTypes().add(ParkingVehicleEnumeration.CAR);
        secondParking.getParkingVehicleTypes().add(ParkingVehicleEnumeration.PEDAL_CYCLE);
        secondParking.setParkingType(ParkingTypeEnumeration.PARK_AND_RIDE);

        Parking importResult = mergingParkingImporter.importParkingWithoutNetexMapping(secondParking);

        assertThat(importResult.getNetexId()).isEqualTo(firstParking.getNetexId());
        assertThat(importResult.getVersion()).isEqualTo(2L);
        assertThat(importResult.getParkingVehicleTypes()).containsExactly(ParkingVehicleEnumeration.CAR, ParkingVehicleEnumeration.PEDAL_CYCLE);
    }

    private Point point(double longitude, double latitude) {
        return
                geometryFactory.createPoint(
                        new Coordinate(longitude, latitude));
    }

    private Parking createParking(String name, double longitude, double latitude, String stopPlaceId) {
        Parking parking = new Parking();
        parking.setCentroid(point(longitude, latitude));
        parking.setName(new EmbeddableMultilingualString(name, ""));
        parking.setNetexId(stopPlaceId);
        return parking;
    }

}
