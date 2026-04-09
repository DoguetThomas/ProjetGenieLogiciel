package services;

import dto.FitActivityDto;
import dto.TimedValueDto;
import model.*;
import dto.GeoDto;
import dto.SplitDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation du service d'import de fichiers FIT Strava like
 * Convertit un {@link FitActivityDto} en {@link ActivityImpl}
 * et l'injecte dans la liste partagée avec {@link StravaAnalyticsServiceImpl}
 */

public class ImportServiceImpl implements ImportService {

    // On doit d'abord récupérer la list des activités existantes pour ensuite convertir notre activity dans le bon format pour chaque variable


    // Liste partagée par référence avec StravaAnalyticsServiceImpl
    private final List<ActivityModel> activities;

    private final Traitement traitement;

    /**
     * @param activities liste des activités du service analytique récupéré par un getter dans StravaAnalyticsImpl.getActivities()
     */
    public ImportServiceImpl(List<ActivityModel> activities, Traitement traitement) {
        this.activities = activities;
        this.traitement = traitement;
    }



    @Override
    public void importFitActivity(FitActivityDto request) {
        if (request == null) return;

        // Calcul de l'allure moyenne en min/km
        double avgPace = 0.0;
        if (request.getTotalDistance() > 0 && request.getTotalTime() > 0) {
            avgPace = (request.getTotalTime() / 60.0) / request.getTotalDistance();
        }

        // Route GPS
        List<GpsPoint> route = new ArrayList<>();
        for (GeoDto geo : request.getRoutePoints()) {
            route.add(new GpsPoint(geo.getLatitude(), geo.getLongitude()));
        }

        // Construction de l'activité
        ActivityImpl activity = new ActivityImpl(
                request.getId(),
                request.getTotalDistance(),
                request.getAvgSpeed(),
                request.getSportType().toUpperCase(),
                (int) request.getTotalTime(),
                request.getAvgPower(),
                (double) request.getAvgHr(),
                (double) request.getMaxHr(),
                avgPace,
                null,
                route,
                new ArrayList<>() // splits calculés depuis les métriques
        );

        // Stocker les métriques FIT directement dans l'activité
        activity.setComputedMetrics(request.getComputedMetrics());
        activity.setSplits(computeSplitsFromMetrics(request.getComputedMetrics(), request.getTotalDistance()));

        // Dans importFitActivity(), temporairement
        System.out.println("SportType reçu : " + request.getSportType());

// Puis normalisation
        String sport = request.getSportType().toUpperCase();
        if (sport.contains("CYCL") || sport.contains("BIKE") || sport.contains("RIDE")) {
            sport = "BIKE";
        } else {
            sport = "RUN";
        }
        activity.setSport(sport);


        // Remplace si existe déjà, sinon ajoute
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i) != null && request.getId().equals(activities.get(i).getId())) {
                activities.set(i, activity);
                return;
            }
        }
        activities.add(activity);
    }

    /**
     * Calcule les splits par km depuis les métriques FIT.
     * Découpe les points en blocs correspondant à chaque kilomètre.
     */
    private List<Split> computeSplitsFromMetrics(Map<String, List<TimedValueDto>> metrics, double totalDistanceKm) {
        List<Split> splits = new ArrayList<>();
        List<TimedValueDto> hrPoints = metrics.getOrDefault("heart-rate", new ArrayList<>());
        List<TimedValueDto> pacePoints = metrics.getOrDefault("pace", new ArrayList<>());

        if (pacePoints.isEmpty() || totalDistanceKm <= 0) return splits;

        // Nombre de points par km
        int totalPoints = pacePoints.size();
        int totalKm = (int) Math.ceil(totalDistanceKm);
        int blockSize = totalPoints / totalKm;
        if (blockSize == 0) return splits;

        for (int km = 0; km < totalKm; km++) {
            int start = km * blockSize;
            int end = Math.min(start + blockSize, totalPoints);

            // Durée du split en secondes depuis la pace moyenne (min/km → secondes)
            double avgPaceVal = 0;
            int paceCount = 0;
            for (int j = start; j < end; j++) {
                if (pacePoints.get(j).getValue() != null) {
                    avgPaceVal += pacePoints.get(j).getValue().doubleValue();
                    paceCount++;
                }
            }
            int splitSeconds = paceCount > 0 ? (int) (avgPaceVal / paceCount * 60) : 0;

            // FC moyenne du split
            int hrSum = 0, hrCount = 0;
            for (int j = start; j < end && j < hrPoints.size(); j++) {
                if (hrPoints.get(j).getValue() != null) {
                    hrSum += hrPoints.get(j).getValue().intValue();
                    hrCount++;
                }
            }
            int avgHr = hrCount > 0 ? hrSum / hrCount : 0;

            splits.add(new Split(km + 1, splitSeconds, avgHr));
        }
        return splits;
    }

}
