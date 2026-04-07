package services;

import dto.AllActivitiesDto;
import dto.MetricDto;
import dto.PaceDto;
import dto.RouteDto;
import dto.SummaryDto;
import dto.ZoneDto;
import exceptions.ActivityNotFoundException;

public interface AnalyticsService {

    public AllActivitiesDto getAllActivities();

    public SummaryDto getSummary(String id) throws ActivityNotFoundException;

    public RouteDto getRoute(String id) throws ActivityNotFoundException;

    public MetricDto getMetricsAltitude(String id) throws ActivityNotFoundException;

    public MetricDto getMetricsSpeed(String id) throws ActivityNotFoundException;

    public MetricDto getMetricsHeartRate(String id) throws ActivityNotFoundException;

    public MetricDto getMetricsPower(String id) throws ActivityNotFoundException;

    public MetricDto getMetricsCadence(String id) throws ActivityNotFoundException;

    public MetricDto getMetricsGroundTime(String id) throws ActivityNotFoundException;

    public PaceDto getMetricsPace(String id);

    public ZoneDto getMetricsZone(String id);

}
