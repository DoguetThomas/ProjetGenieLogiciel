import model.ActivityImpl;
import model.Split;
import org.junit.jupiter.api.Test;

import model.GpsPoint;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityImplTest {

    @Test
    void testCreationEtLectureActivite() {
        GpsPoint point1 = new GpsPoint(48.117266, -1.677792);
        GpsPoint point2 = new GpsPoint(48.118000, -1.680000);
        List<GpsPoint> route = new ArrayList<>();
        route.add(point1);
        route.add(point2);

        Split split1 = new Split(2,10,180);
        Split split2 = new Split(5,25,180);
        List<Split> Splits = new ArrayList<>();
        Splits.add(split1);
        Splits.add(split2);

        List<Integer> zonesHR = new ArrayList<>();
        zonesHR.add(600);
        zonesHR.add(1200);
        zonesHR.add(1000);
        zonesHR.add(500);
        zonesHR.add(300);

        ActivityImpl activite = new ActivityImpl(
                "ACT-999",
                25000.0,
                25.5,
                "BIKE",
                3600,
                200.0,
                145.0,
                185.0,
                2.35,
                zonesHR,
                route,
                Splits
        );


        assertEquals("ACT-999", activite.getId(), "L'ID doit être ACT-999");
        assertEquals(25000.0, activite.getDistance(), "La distance doit être 25000.0");
        assertEquals("BIKE", activite.getSport(), "Le sport doit être BIKE");
        assertEquals(3600, activite.getDuration(), "La durée doit être 3600");

    }
}