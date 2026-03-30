package dto;

import java.util.List;

/**
 * Container DTO for all available activities.
 */
public class AllActivitiesDto {

    private final List<ActivityDto> activities;

    /**
     * Creates an activities container.
     *
     * @param activities activities list
     */
    public AllActivitiesDto(List<ActivityDto> activities) {
        this.activities = activities;
    }

    /**
     * Adds an activity descriptor to the container.
     *
     * @param id unique activity identifier
     * @param sportType activity sport type
     * @param distanceKm activity distance in kilometers
     * @return current DTO for chaining
     */
    public AllActivitiesDto addActivity(String id, ActivityTypeDto sportType, double distanceKm) {
        this.activities.add(new ActivityDto(id, sportType, distanceKm));
        return this;
    }

    /**
     * @return list of activity descriptors
     */
    public List<ActivityDto> getActivities() {
        return activities;
    }
}