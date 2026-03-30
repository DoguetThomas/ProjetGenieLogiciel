package dto;

/**
 * Lightweight activity descriptor used for activity listings.
 */
public class ActivityDto {

    private final String id;
    private final ActivityTypeDto sportType;
    private final double distanceKm;

    /**
     * Creates an activity descriptor.
     *
     * @param id unique activity identifier
     * @param sportType activity sport type
     * @param distanceKm activity distance in kilometers
     */
    public ActivityDto(String id, ActivityTypeDto sportType, double distanceKm) {
        this.id = id;
        this.sportType = sportType;
        this.distanceKm = distanceKm;
    }

    /**
     * @return unique activity identifier
     */
    public String getId() {
        return id;
    }

    /**
     * @return activity sport type
     */
    public ActivityTypeDto getSportType() {
        return sportType;
    }

    /**
     * @return activity distance in kilometers
     */
    public double getDistanceKm() {
        return distanceKm;
    }
}
