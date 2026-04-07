package services;

public interface EnhancedAnalyticsService {

    /**
     * Get the altitude gain for bike activities (only)
     *
     * @param activityId the activity identifier
     * @return the value as double
     */
    double getElevationGain(String activityId);

    /**
     * Get the running form score based on the
     * https://www.garmin.com/fr-FR/garmin-technology/running-science/running-dynamics/hill-score/
     * @param activityId the activity identifier
     * @return the value as double
     */
    double getVerticalRatio(String activityId);

}
