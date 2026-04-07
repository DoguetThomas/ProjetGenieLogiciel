package services;

import dto.*;

import java.util.ArrayList;
import java.util.List;

import model.ActivityImpl;
import model.ActivityModel;
import model.UserImpl;

public class StravaAnalyticsServiceImpl implements AnalyticsService {
    private List<ActivityModel> activities;
    private Traitement traitement;

    public StravaAnalyticsServiceImpl() {
        this.activities = new ArrayList<>();
        this.traitement = new Traitement("../data/strava.csv", new UserImpl(1, true, 0, 0));
        this.activities = this.traitement.getActivities();
    }

    @Override
    public AllActivitiesDto getAllActivities() {
        AllActivitiesDto allActivitiesDto = new AllActivitiesDto(new ArrayList<>());
        List<ActivityDto> activitiesDto = new ArrayList<>();
        for (ActivityModel activity : this.activities) {
            if (activity != null) {
                ActivityTypeDto sport = this.determineSport(activity);

                allActivitiesDto.addActivity(activity.getId(), sport, activity.getDistance());
            }
        }
        return allActivitiesDto;
    }

    private static ActivityTypeDto determineSport(ActivityModel activity) {
        if ("BIKE".equals(activity.getSport())) {
            return ActivityTypeDto.BIKE;
        } else {
            // Par défaut, ou si c'est "RUN"
            return ActivityTypeDto.RUN;
        }
    }

    @Override
    public SummaryDto getSummary(String id) {
        if (id == null) {
            return null;
        }
        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {
                ActivityTypeDto sport = determineSport(activity);
                double distanceKm = (activity.getDistance() != null) ? activity.getDistance() : 0.0;
                int maxHrInt = (activity.getMaxHR() != null) ? activity.getMaxHR().intValue() : 0;
                double avgHr = (activity.getAvgHR() != null) ? activity.getAvgHR() : 0.0;
                double avgSpeed = (activity.getAvgSpeed() != null) ? activity.getAvgSpeed() : 0.0;
                double avgPace = (activity.getAvgPace() != null) ? activity.getAvgPace() : 0.0;
                double avgPower = (activity.getAvgPower() != null) ? activity.getAvgPower() : 0.0;
                return new SummaryDto(
                        sport,
                        distanceKm,
                        activity.getDuration(), // Déjà un int
                        avgHr,
                        maxHrInt,
                        avgSpeed,
                        avgPace,
                        avgPower);
            }
        }
        return null;
    }

    @Override
    public RouteDto getRoute(String id) {
        if (id == null) {
            return null;
        }

        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {

                // Création d'un tracé GPS fictif (Mock)
                List<GeoDto> mockRoutePoints = new ArrayList<>();

                // Ajout de quelques points de coordonnées (Latitude, Longitude)
                mockRoutePoints.add(new GeoDto(48.117266, -1.677792));
                mockRoutePoints.add(new GeoDto(48.117800, -1.678200));
                mockRoutePoints.add(new GeoDto(48.118500, -1.679000));
                mockRoutePoints.add(new GeoDto(48.119200, -1.678500));
                mockRoutePoints.add(new GeoDto(48.119500, -1.677000));

                // Retourne le DTO de la route en lui passant uniquement la liste des points
                return new RouteDto(mockRoutePoints);
            }
        }
        // Retourne null si aucune activité correspondante n'a été trouvée
        return null;
    }

    private MetricDto getMetricData(String id, String metricName) {
        if (id == null) {
            return null;
        }
        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {
                ActivityTypeDto sport = determineSport(activity);
                List<TimedValueDto> mockPoints = new ArrayList<>();
                mockPoints.add(new TimedValueDto("00:00:00", 0.0));
                mockPoints.add(new TimedValueDto("00:01:00", 12.5));
                mockPoints.add(new TimedValueDto("00:02:00", 14.2));
                mockPoints.add(new TimedValueDto("00:03:00", 13.8));
                return new MetricDto(sport, metricName, mockPoints);
            }
        }
        return null;
    }

    @Override
    public MetricDto getMetricsAltitude(String id) {
        return getMetricData(id, "Altitude");
    }

    @Override
    public MetricDto getMetricsSpeed(String id) {
        return getMetricData(id, "Speed");
    }

    @Override
    public MetricDto getMetricsHeartRate(String id) {
        return getMetricData(id, "HeartRate");
    }

    @Override
    public MetricDto getMetricsPower(String id) {
        return getMetricData(id, "Power");
    }

    @Override
    public MetricDto getMetricsCadence(String id) {
        return getMetricData(id, "Cadence");
    }

    @Override
    public MetricDto getMetricsGroundTime(String id) {
        return getMetricData(id, "GroundTime");
    }

    @Override
    public PaceDto getMetricsPace(String id) {
        return null;
    }

    @Override
    public ZoneDto getMetricsZone(String id) {
        return null;
    }
}
