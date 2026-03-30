package dto;

/**
 * DTO describing one kilometer split.
 */
public class SplitDto {

    private final int km;
    private final int splitSeconds;
    private final int avgHeartRate;

    /**
     * Creates a split entry.
     *
     * @param km split kilometer index
     * @param splitSeconds split duration in seconds
     * @param avgHeartRate average heart rate during the split
     */
    public SplitDto(int km, int splitSeconds, int avgHeartRate) {
        this.km = km;
        this.splitSeconds = splitSeconds;
        this.avgHeartRate = avgHeartRate;
    }

    /**
     * @return split kilometer index
     */
    public int getKm() {
        return km;
    }

    /**
     * @return split duration in seconds
     */
    public int getSplitSeconds() {
        return splitSeconds;
    }

    /**
     * @return average heart rate during the split
     */
    public int getAvgHeartRate() {
        return avgHeartRate;
    }
}