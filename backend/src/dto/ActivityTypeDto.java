package dto;

/**
 * Enumeration of supported activity types.
 */
public enum ActivityTypeDto {

    /** Running activity. */
    RUN("Run"),
    /** Cycling activity. */
    BIKE("Bike");

    private final String label;

    ActivityTypeDto(String label) {
        this.label = label;
    }

    /**
     * @return human-readable label for this activity type
     */
    public String getLabel() {
        return label;
    }
}