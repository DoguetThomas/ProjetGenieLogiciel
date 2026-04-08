package controllers;

import exceptions.ActivityNotFoundException;
import services.AnalyticsService;
import services.EnhancedAnalyticsService;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Controller for handling activity-related endpoints, including fetching activity summaries and various metrics.
 * 
 * Do not modify this class.
 */
public class ActivitiesController extends AbstractController {

    private final AnalyticsService analyticsService;

    private final EnhancedAnalyticsService enhancedAnalyticsService;
    private final Map<String, Function<String, Object>> metricsHandlers = new HashMap<>();

    public ActivitiesController(AnalyticsService analyticsService, EnhancedAnalyticsService enhancedAnalyticsService) {
        this.analyticsService = analyticsService;

        this.enhancedAnalyticsService = enhancedAnalyticsService;

    }



    @Override
    protected void defineRoutes() {

        get("/api/activities/all", (ex, p) -> json(analyticsService.getAllActivities()));

        get("/api/activities/{id}/summary", (ex, p) -> json(analyticsService.getSummary(p.get("id"))));
        // Explicit metric endpoints (allows per-endpoint exception handling)
        get("/api/activities/{id}/metrics/route", (ex, p) -> json(analyticsService.getRoute(p.get("id"))));
        get("/api/activities/{id}/metrics/altitude", (ex, p) -> json(analyticsService.getMetricsAltitude(p.get("id"))));
        get("/api/activities/{id}/metrics/speed", (ex, p) -> json(analyticsService.getMetricsSpeed(p.get("id"))));
        get("/api/activities/{id}/metrics/heart-rate",
                (ex, p) -> json(analyticsService.getMetricsHeartRate(p.get("id"))));
        get("/api/activities/{id}/metrics/power", (ex, p) -> json(analyticsService.getMetricsPower(p.get("id"))));
        get("/api/activities/{id}/metrics/cadence", (ex, p) -> json(analyticsService.getMetricsCadence(p.get("id"))));
        get("/api/activities/{id}/metrics/ground-time",
                (ex, p) -> json(analyticsService.getMetricsGroundTime(p.get("id"))));
        get("/api/activities/{id}/metrics/pace", (ex, p) -> json(analyticsService.getMetricsPace(p.get("id"))));
        get("/api/activities/{id}/metrics/zone", (ex, p) -> json(analyticsService.getMetricsZone(p.get("id"))));

        get("/api/activities/{id}/metrics/{type}", (ex, p) -> {
            String id = p.get("id");
            String type = p.get("type");
            Function<String, Object> handler = metricsHandlers.get(type);
            if (handler == null) {
                throw new NoSuchElementException("Metric type not found: " + type);
            }
            return json(handler.apply(id));
        });

        get("/api/activities/{id}/elevation", (ex, p) -> json(enhancedAnalyticsService.getElevationGain(p.get("id"))));

        get("/api/activities/{id}/vertical-ratio", (ex, p) -> json(enhancedAnalyticsService.getVerticalRatio(p.get("id"))));

    }
}
