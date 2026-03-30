package services;

import dto.AllActivitiesDto;
import dto.MetricDto;
import dto.PaceDto;
import dto.RouteDto;
import dto.SummaryDto;
import dto.ZoneDto;

public interface AnalyticsService {

    public AllActivitiesDto getAllActivities();

    public SummaryDto getSummary(String id);

    public RouteDto getRoute(String id);

    public MetricDto getMetricsAltitude(String id);

    public MetricDto getMetricsSpeed(String id);

    public MetricDto getMetricsHeartRate(String id);

    public MetricDto getMetricsPower(String id);

    public MetricDto getMetricsCadence(String id);

    public MetricDto getMetricsGroundTime(String id);

    public PaceDto getMetricsPace(String id);

    public ZoneDto getMetricsZone(String id);

}
