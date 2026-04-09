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

    // On doit d'abord récupérer la list des activités existantes

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

    }
}
