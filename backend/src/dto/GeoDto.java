package dto;

/**
 * Geographic point used in route payloads.
 */
public class GeoDto {

    private final double latitude;
    private final double longitude;

    /**
     * Creates a geographic point.
     *
     * @param latitude latitude in decimal degrees
     * @param longitude longitude in decimal degrees
     */
    public GeoDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return latitude in decimal degrees
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return longitude in decimal degrees
     */
    public double getLongitude() {
        return longitude;
    }
}
