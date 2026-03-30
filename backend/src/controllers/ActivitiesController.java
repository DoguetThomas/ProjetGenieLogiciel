package controllers;

import services.AnalyticsService;

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
    private final Map<String, Function<String, Object>> metricsHandlers = new HashMap<>();

    public ActivitiesController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
        initMetrics();
    }

    private void initMetrics() {
        metricsHandlers.put("route", analyticsService::getRoute);
        metricsHandlers.put("altitude", analyticsService::getMetricsAltitude);
        metricsHandlers.put("speed", analyticsService::getMetricsSpeed);
        metricsHandlers.put("heart-rate", analyticsService::getMetricsHeartRate);
        metricsHandlers.put("power", analyticsService::getMetricsPower);
        metricsHandlers.put("cadence", analyticsService::getMetricsCadence);
        metricsHandlers.put("ground-time", analyticsService::getMetricsGroundTime);
        metricsHandlers.put("pace", analyticsService::getMetricsPace);
        metricsHandlers.put("zone", analyticsService::getMetricsZone);
    }

    @Override
    protected void defineRoutes() {

        get("/api/activities/all", (ex, p) -> json(analyticsService.getAllActivities()));

        get("/api/activities/{id}/summary", (ex, p) -> json(analyticsService.getSummary(p.get("id"))));

        get("/api/activities/{id}/metrics/{type}", (ex, p) -> {
            String id = p.get("id");
            String type = p.get("type");
            Function<String, Object> handler = metricsHandlers.get(type);
            if (handler == null) {
                throw new NoSuchElementException("Metric type not found: " + type);
            }
            return json(handler.apply(id));
        });
    }
}
