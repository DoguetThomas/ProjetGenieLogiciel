package dto;

/**
 * Generic timestamped numeric value used in metric time series.
 */
public class TimedValueDto {

    private final String timestamp;
    private final Number value;

    /**
     * Creates a timestamped value.
     *
     * @param timestamp timestamp of the sample
     * @param value numeric value of the sample
     */
    public TimedValueDto(String timestamp, Number value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    /**
     * @return sample timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @return sample numeric value
     */
    public Number getValue() {
        return value;
    }
}