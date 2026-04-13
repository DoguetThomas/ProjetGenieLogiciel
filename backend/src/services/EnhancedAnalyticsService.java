package services;

import dto.ElevationDto;
import dto.VerticalRatioDto;

public interface EnhancedAnalyticsService {

    /**
     * Retourne le dénivelé positif total pour une activité vélo.
     * Encapsulé dans un {@link ElevationDto} pour que le controller
     * puisse le sérialiser en JSON sans transformation supplémentaire.
     *
     * @param activityId l'identifiant de l'activité
     * @return un {@link ElevationDto} contenant {@code totalAscent} en mètres
     */
    ElevationDto getElevationGain(String activityId);

    /**
     * Retourne le rapport vertical moyen pour une activité course.
     * Basé sur : https://www.garmin.com/fr-FR/garmin-technology/running-science/running-dynamics/hill-score/
     * Encapsulé dans un {@link VerticalRatioDto} pour que le controller
     * puisse le sérialiser en JSON sans transformation supplémentaire.
     *
     * @param activityId l'identifiant de l'activité
     * @return un {@link VerticalRatioDto} contenant {@code verticalRatio} en pourcentage
     */
    VerticalRatioDto getVerticalRatio(String activityId);
}