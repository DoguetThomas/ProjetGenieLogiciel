package dto;

/**
 * Enumeration of user profile gender values.
 */
public enum GenderDto {

    /** Male gender value. */
    MALE("Male"),
    /** Female gender value. */
    FEMALE("Female");

    private final String label;

    GenderDto(String label) {
        this.label = label;
    }

    /**
     * @return human-readable label for this gender
     */
    public String getLabel() {
        return label;
    }
}