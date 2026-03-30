package services;

import dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StravaAnalyticsServiceImpl implements AnalyticsService{
    //liste des points qui seront récoltés sur le csv
    private final List<StravaRecord> records;
    //on fait tourner la méthode readCSV pour récupérer toute les lignes du CSV
    public StravaAnalyticsServiceImpl(String filePath) {
        this.records = StravaCsvReader.readCsv(filePath);
    }

    @Override
    public AllActivitiesDto getAllActivities() {
        //on va déterminer les distances et les vitesses maximales de chaque activités
        Map<String, Double> maxDistances = new HashMap<>();
        Map<String, Double> maxSpeeds = new HashMap<>();
        //on parcourt chaque points pour les déterminer
        for (StravaRecord record : this.records) {
            String activityId = record.getDatafile();
            //on passe les activités indétéctables
            if (activityId == null || activityId.trim().isEmpty()) {
                continue;
            }
            Double distance = record.getDistance();
            if (distance != null) {
                //récupération de la distance précédente si existe (0 sinon)
                double previousMax = maxDistances.getOrDefault(activityId, 0.0);
                //comparaison et remplacement si besoin;
                if (distance > previousMax) {
                    maxDistances.put(activityId, distance);
                }
            }
            Double speed = record.getEnhancedSpeed();
            if (speed != null) {
                //récupération de la vitesse max si existe (0 sinon)
                double previousMaxSpeed = maxSpeeds.getOrDefault(activityId, 0.0);
                //comparaison et remplacement si besoin;
                if (speed > previousMaxSpeed) {
                    maxSpeeds.put(activityId, speed);
                }
            }
        }
        // 2. Création du DTO de retour
        AllActivitiesDto resultDto = new AllActivitiesDto(new ArrayList<>());
        for (Map.Entry<String, Double> entry : maxDistances.entrySet()) {
            String id = entry.getKey();
            //récupération de la distance et conversion en Km et récupération de la vitesse
            double distanceKm = entry.getValue() / 1000.0;
            double maxSpeedForActivity = maxSpeeds.getOrDefault(id, 0.0);
            //détermination du type de sport (j'ai fixé a 6 m/s mais discutable)
            ActivityTypeDto sportType;
            if (maxSpeedForActivity > 6) {
                sportType = ActivityTypeDto.BIKE;
            } else {
                sportType = ActivityTypeDto.RUN;
            }
            //ajout dans le allActivitiesDto l'activité détéctée et analysée
            resultDto.addActivity(id, sportType, distanceKm);
        }
        return resultDto;
    }

    @Override
    public SummaryDto getSummary(String id) {
        return null;
    }

    @Override
    public RouteDto getRoute(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsAltitude(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsSpeed(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsHeartRate(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsPower(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsCadence(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsGroundTime(String id) {
        return null;
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
