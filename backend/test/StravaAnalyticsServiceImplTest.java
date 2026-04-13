import dto.*;
import exceptions.ActivityNotFoundException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.StravaAnalyticsServiceImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StravaAnalyticsServiceImplTest {

    private StravaAnalyticsServiceImpl service;
    private List<ActivityModel> activities;

    @BeforeEach
    void setUp() throws Exception {
        service = new StravaAnalyticsServiceImpl();
        activities = new ArrayList<>();

        Field field = StravaAnalyticsServiceImpl.class.getDeclaredField("activities");
        field.setAccessible(true);
        field.set(service, activities);
    }


    @Test
    void getAllActivities_shouldReturnCorrectSize() {
        activities.add(createRealActivity("ACT-001", "BIKE", 10.0));
        activities.add(createRealActivity("ACT-002", "RUN", 5.0));

        AllActivitiesDto result = service.getAllActivities();

        assertEquals(2, result.getActivities().size());
    }

    @Test
    void getSummary_shouldReturnBikeStats() throws ActivityNotFoundException {
        activities.add(createRealActivity("ACT-001", "BIKE", 25.0));

        SummaryDto summary = service.getSummary("ACT-001");

        assertEquals(ActivityTypeDto.BIKE, summary.getSportType());
        assertEquals(25.0, summary.getDistanceKm());
    }

    @Test
    void getSummary_shouldReturnRunStatsWithDefaultValues() throws ActivityNotFoundException {
        ActivityImpl runAct = new ActivityImpl("ACT-001", null, null, "RUN", 1000,
                null, null, null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        activities.add(runAct);

        SummaryDto summary = service.getSummary("ACT-001");

        assertEquals(ActivityTypeDto.RUN, summary.getSportType());
        assertEquals(0.0, summary.getDistanceKm());
        assertEquals(0, summary.getMaxHeartRate());
    }


    @Test
    void getSummary_shouldThrowExceptionIfNotFound() {
        assertThrows(ActivityNotFoundException.class, () -> service.getSummary("UNKNOWN"));
    }

    @Test
    void findActivityById_shouldThrowExceptionIfIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.getSummary(null));
    }


    @Test
    void getRoute_shouldConvertGpsPoints() throws ActivityNotFoundException {
        List<GpsPoint> route = new ArrayList<>();
        route.add(new GpsPoint(48.0, -1.0));
        ActivityImpl act = createRealActivity("ACT-001", "RUN", 10.0);

        ActivityImpl actWithRoute = new ActivityImpl(act.getId(), act.getDistance(), 15.0, "RUN", 3600, 180.0, 140.0, 160.0, 5.0, new ArrayList<>(), route, new ArrayList<>());
        activities.clear();
        activities.add(actWithRoute);

        RouteDto routeDto = service.getRoute("ACT-001");
        assertEquals(1, routeDto.getPoints().size());
        assertEquals(48.0, routeDto.getPoints().get(0).getLatitude());
    }

    @Test
    void getMetricsPace_shouldConvertSplits() throws ActivityNotFoundException {
        List<Split> splits = new ArrayList<>();
        splits.add(new Split(1, 300, 150));

        ActivityImpl actWithSplits = new ActivityImpl("ACT-001", 1.0, 12.0, "RUN", 300, 180.0, 150.0, 150.0, 5.0, new ArrayList<>(), new ArrayList<>(), splits);
        activities.add(actWithSplits);

        PaceDto result = service.getMetricsPace("ACT-001");
        assertEquals(1, result.getSplits().size());
        assertEquals(300, result.getSplits().get(0).getSplitSeconds());
    }

    @Test
    void getMetricsZone_shouldReturnNullIfIdNull() {
        assertNull(service.getMetricsZone(null));
    }

    @Test
    void getMetricsZone_shouldReturnNullIfNotFound() {
        assertNull(service.getMetricsZone("NOT-IN-LIST"));
    }



    private ActivityImpl createRealActivity(String id, String sport, double distance) {
        return new ActivityImpl(
                id,
                distance,
                15.0,
                sport,
                3600,
                180.0,
                140.0,
                160.0,
                5.0,
                new ArrayList<>(), // zonesHR
                new ArrayList<>(), // route
                new ArrayList<>()  // splits
        );
    }
}