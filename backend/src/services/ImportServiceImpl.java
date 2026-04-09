package services;

import dto.FitActivityDto;
import model.*;
import dto.GeoDto;
import dto.SplitDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du service d'import de fichiers FIT Strava like
 * Convertit un {@link FitActivityDto} en {@link ActivityImpl}
 * et l'injecte dans la liste partagée avec {@link StravaAnalyticsServiceImpl}
 */

public class ImportServiceImpl implements ImportService {

    // On doit d'abord récupérer la list des activités existantes pour ensuite convertir notre activity dans le bon format pour chaque variable


    // Liste partagée par référence avec StravaAnalyticsServiceImpl
    private final List<ActivityModel> activities;

    /**
     * @param activities liste des activités du service analytique récupéré par un getter dans StravaAnalyticsImpl.getActivities()
     */
    public ImportServiceImpl(List<ActivityModel> activities) {
        this.activities = activities;
    }


    @Override
    public void importFitActivity(FitActivityDto request) {
        if (request == null) {return;}

        // vérifie qu'une activité avec cet id n'existe pas déjà
        for (ActivityModel existing : this.activities) {
            if (existing != null && request.getId().equals(existing.getId())) {
                return;
            }
        }

        // Convertir la route : GeoDto vers GpsPoint
        List<GpsPoint> route = new ArrayList<>();
        for (GeoDto geo : request.getRoutePoints()) {
            route.add(new GpsPoint(geo.getLatitude(), geo.getLongitude()));
        }

        // Convertion des splits : SplitDto vers Split
        // Split(km, durationSeconds, avgHeartRate) — voir Split.java
        List<Split> splits = new ArrayList<>();
        for (SplitDto s : request.getSplits()) {
            splits.add(new Split(s.getKm(), s.getSplitSeconds(), s.getAvgHeartRate()));
        }

        // Calcul de l'allure moyenne en min/km
        //    pace = (durée en secondes / 60) / distance en km
        double avgPace = 0.0;
        if (request.getTotalDistance() > 0 && request.getTotalTime() > 0) {
            avgPace = (request.getTotalTime() / 60.0) / request.getTotalDistance();
        }

        // Construction l'ActivityImpl avec tous les champs
        // zoneHR renvoie null car calculé plus tard
        ActivityImpl activity = new ActivityImpl(
                request.getId(),
                request.getTotalDistance(),
                request.getAvgSpeed(),
                request.getSportType(),
                (int) request.getTotalTime(),
                request.getAvgPower(),
                (double) request.getAvgHr(),
                (double) request.getMaxHr(),
                avgPace,
                null,
                route,
                splits
        );

        //Ajout à la liste partagée
        this.activities.add(activity);



    }
}
