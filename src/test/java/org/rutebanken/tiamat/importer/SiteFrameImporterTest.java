package org.rutebanken.tiamat.importer;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.rutebanken.tiamat.TiamatApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rutebanken.tiamat.model.SiteFrame;
import org.rutebanken.tiamat.model.StopPlace;
import org.rutebanken.tiamat.model.StopPlacesInFrame_RelStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TiamatApplication.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ActiveProfiles("geodb")
public class SiteFrameImporterTest {

    @Autowired
    private GeometryFactory geometryFactory;

    @Autowired
    private SiteFrameImporter siteFrameImporter;

    @Autowired
    private DefaultStopPlaceImporter stopPlaceImporter;



    @Test
    public void noStopPlacesInSiteFrameShouldNotCauseNullpointer() {
        SiteFrame siteFrame = new SiteFrame();
        siteFrameImporter.importSiteFrame(siteFrame, stopPlaceImporter);
    }

    /**
     * This test is implemented to reproduce an issue we had with lazy initialization exception
     * when returning a stop place that is already persisted, found by looking at imported key.
     * The test seems to be easier to reproduce if cache is disabled in {@link org.rutebanken.tiamat.repository.StopPlaceRepository}.
     */
    @Test
    public void lazyInitializationException() {

        int stopPlaces = 2;
        Random random = new Random();

        List<SiteFrame> siteFrames = new ArrayList<>();

        for(int siteFrameIndex = 0; siteFrameIndex < 2; siteFrameIndex++) {
            SiteFrame siteFrame = new SiteFrame();
            siteFrame.setStopPlaces(new StopPlacesInFrame_RelStructure());

            for (int stopPlaceIndex = 0; stopPlaceIndex < stopPlaces; stopPlaceIndex++) {

                StopPlace stopPlace = new StopPlace();
                stopPlace.setId(stopPlaceIndex * Math.abs(random.nextLong()));

                double longitude = 39.61441 * Math.abs(random.nextDouble());
                double latitude = -144.22765 * Math.abs(random.nextDouble());

                stopPlace.setCentroid(geometryFactory.createPoint(new Coordinate(longitude, latitude)));

                siteFrame.getStopPlaces().getStopPlace().add(stopPlace);
            }

            siteFrames.add(siteFrame);
        }

        siteFrameImporter.importSiteFrame(siteFrames.get(0), stopPlaceImporter);
        siteFrameImporter.importSiteFrame(siteFrames.get(1), stopPlaceImporter);
    }


    @Test
    public void uniqueByIdAndVersion() {

        List<org.rutebanken.netex.model.StopPlace> stopPlacesWithDuplicates = new ArrayList<>();

        org.rutebanken.netex.model.StopPlace stopPlace1 = new org.rutebanken.netex.model.StopPlace();
        stopPlace1.setId("100");
        stopPlace1.setVersion("10");

        org.rutebanken.netex.model.StopPlace stopPlace2 = new org.rutebanken.netex.model.StopPlace();
        stopPlace2.setId("100");
        stopPlace2.setVersion("1");

        stopPlacesWithDuplicates.add(stopPlace2);
        stopPlacesWithDuplicates.add(stopPlace1);

        Collection<org.rutebanken.netex.model.StopPlace> actual = siteFrameImporter.distinctByIdAndHighestVersion(stopPlacesWithDuplicates);

        assertThat(actual).hasSize(1);
        assertThat(actual).containsOnlyOnce(stopPlace1);
        assertThat(actual).extracting(org.rutebanken.netex.model.StopPlace::getVersion).containsOnly("10");

    }
}