package services;

import dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exceptions.ActivityNotFoundException;
import model.*;

public class StravaAnalyticsServiceImpl implements AnalyticsService{
    private List<ActivityModel> activities;
    private Traitement traitement;

    public StravaAnalyticsServiceImpl(){
        this.activities = new ArrayList<>();
        this.traitement = new Traitement("../data/strava.csv", new UserImpl(1, true, 0, 0));
        this.activities = this.traitement.getActivities();
    }
    @Override
    public AllActivitiesDto getAllActivities(){
        AllActivitiesDto allActivitiesDto = new AllActivitiesDto(new ArrayList<>());
       List<ActivityDto> activitiesDto = new ArrayList<>();
        for (ActivityModel activity : this.activities){
            if (activity !=null ){
                ActivityTypeDto sport = this.determineSport(activity);

                allActivitiesDto.addActivity(activity.getId(), sport, activity.getDistance());
            }
        }
        return allActivitiesDto;
    }

    private static ActivityTypeDto determineSport(ActivityModel activity){
        if ("BIKE".equals(activity.getSport())) {
            return ActivityTypeDto.BIKE;
        } else {
            // Par défaut, ou si c'est "RUN"
            return ActivityTypeDto.RUN;
        }
    }

    @Override
    public SummaryDto getSummary(String id) throws ActivityNotFoundException {
        if (id == null) {
            return null;
        }
        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {
                ActivityTypeDto sport = determineSport(activity);
                double distanceKm = (activity.getDistance() != null) ? activity.getDistance(): 0.0;
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
                        avgPower
                );
            }
        }
        throw new ActivityNotFoundException("Activité inconnue");
    }

    @Override
    public RouteDto getRoute(String id) throws ActivityNotFoundException {
        if (id == null) {
            return null;
        }

        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {

                // Création d'un tracé GPS fictif (Mock)
                List<GpsPoint> pointsGps = activity.getRoute();
                List<GeoDto> route = new ArrayList<>();
                // Ajout de quelques points de coordonnées (Latitude, Longitude)
                for (GpsPoint point : pointsGps){
                    route.add(new GeoDto(point.getLatitude(), point.getLongitude()));
                }


                // Retourne le DTO de la route en lui passant uniquement la liste des points
                return new RouteDto(route);
            }
        }
        // Retourne null si aucune activité correspondante n'a été trouvée
        throw new ActivityNotFoundException("Activité inconnue");
    }

    private MetricDto getMetricData(String id, String metricName) throws ActivityNotFoundException {
        if (id == null) {
            return null;
        }
        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {
                ActivityTypeDto sport = determineSport(activity);
                List<TimedValueDto> points = new ArrayList<>();
                for (Map.Entry<String, Number> entry : this.traitement.getMetricList(activity.getId(), metricName).entrySet()){
                    points.add(new TimedValueDto(entry.getKey(), entry.getValue()));
                }

                return new MetricDto(sport, metricName, points);
            }
        }
        throw new ActivityNotFoundException("Activité inconnue");
    }

    @Override
    public MetricDto getMetricsAltitude(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Altitude");
    }

    @Override
    public MetricDto getMetricsSpeed(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Speed");
    }

    @Override
    public MetricDto getMetricsHeartRate(String id) throws ActivityNotFoundException {
        return getMetricData(id, "HeartRate");
    }

    @Override
    public MetricDto getMetricsPower(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Power");
    }

    @Override
    public MetricDto getMetricsCadence(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Cadence");
    }

    @Override
    public MetricDto getMetricsGroundTime(String id) throws ActivityNotFoundException {
        return getMetricData(id, "GroundTime");
    }

    @Override
    public PaceDto getMetricsPace(String id) {
        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {
                List<SplitDto> liste = new ArrayList<>();
                List<Split> splits= activity.getSplits();
                for (Split split : splits){
                    SplitDto splitDto = new SplitDto(split.getKm(), split.getDurationSeconds(), split.getAvgHeartRate());
                    liste.add(splitDto);
                }
                return new PaceDto(liste);
            }
        }
        return null;
    }

    @Override
    public ZoneDto getMetricsZone(String id) {
        return null;
    }
}
