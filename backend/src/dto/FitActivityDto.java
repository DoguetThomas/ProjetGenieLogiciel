package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Request payload used to sync normalized Strava activities from a FIT import.
 */
public class FitActivityDto {

    private String id;
    private String sportType;
    private double totalDistance;
    private long totalTime;
    private int avgHr;
    private int maxHr;
    private double avgSpeed;
    private double avgPower;
    private List<GeoDto> routePoints;
    private List<SplitDto> splits;
    private Map<String, List<TimedValueDto>> computedMetrics;
    private final String source = "fit-import";

    /**
     * Creates a FIT activity DTO used when importing a FIT file.
     *
     * @param id              unique identifier chosen for the imported activity
     *                        (used as activity id)
     * @param sportType       sport type string reported by the FIT session (e.g.
     *                        "Run", "Bike")
     * @param totalDistance   total distance of the activity in kilometers
     * @param totalTime       total elapsed time of the activity in seconds
     * @param avgHr           average heart rate (beats per minute) during the
     *                        activity
     * @param maxHr           maximum heart rate (beats per minute) recorded during
     *                        the activity
     * @param avgSpeed        average speed in kilometers per hour
     * @param avgPower        average power in watts (if available)
     * @param routePoints     ordered geographic points composing the activity route
     * @param splits          list of kilometer split entries describing pace per
     *                        kilometer
     * @param computedMetrics map of metric name to sampled time-series points
     *                        (TimedValueDto)
     */
    public FitActivityDto(String id,
            String sportType,
            double totalDistance,
            long totalTime,
            int avgHr,
            int maxHr,
            double avgSpeed,
            double avgPower,
            List<GeoDto> routePoints,
            List<SplitDto> splits,
            Map<String, List<TimedValueDto>> computedMetrics) {
        this.id = id;
        this.sportType = sportType;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.avgHr = avgHr;
        this.maxHr = maxHr;
        this.avgSpeed = avgSpeed;
        this.avgPower = avgPower;
        this.routePoints = routePoints != null ? routePoints : new ArrayList<>();
        this.splits = splits != null ? splits : new ArrayList<>();
        this.computedMetrics = computedMetrics != null ? computedMetrics : new HashMap<>();
    }

    public String getSource() {
        return source;
    }

    public String getId() {
        return id;
    }

    public String getSportType() {
        return sportType;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public int getAvgHr() {
        return avgHr;
    }

    public int getMaxHr() {
        return maxHr;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public double getAvgPower() {
        return avgPower;
    }

    public List<GeoDto> getRoutePoints() {
        return routePoints;
    }

    public List<SplitDto> getSplits() {
        return splits;
    }

    public Map<String, List<TimedValueDto>> getComputedMetrics() {
        return computedMetrics;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FitActivityDto{");
        sb.append("id=").append(id).append(',');
        sb.append("sportType=").append(sportType).append(',');
        sb.append("totalDistance=").append(totalDistance).append(',');
        sb.append("totalTime=").append(totalTime).append(',');
        sb.append("avgHr=").append(avgHr).append(',');
        sb.append("maxHr=").append(maxHr).append(',');
        sb.append("avgSpeed=").append(avgSpeed).append(',');
        sb.append("avgPower=").append(avgPower).append(',');

        // sb.append("routePoints=[");
        // for (GeoDto g : routePoints) {
        // sb.append('(').append(g.getLatitude()).append(',').append(g.getLongitude()).append(')').append(',');
        // }
        // if (!routePoints.isEmpty())
        // sb.setLength(sb.length() - 1);
        // sb.append("],");
        //
        // sb.append("splits=[");
        // for (SplitDto s : splits) {
        // sb.append('{').append("km=").append(s.getKm()).append(',')
        // .append("splitSeconds=").append(s.getSplitSeconds()).append(',')
        // .append("avgHr=").append(s.getAvgHeartRate()).append('}').append(',');
        // }
        // if (!splits.isEmpty())
        // sb.setLength(sb.length() - 1);
        // sb.append("],");
        //
        // sb.append("computedMetrics={");
        // for (Map.Entry<String, List<TimedValueDto>> e : computedMetrics.entrySet()) {
        // sb.append(e.getKey()).append(':');
        // List<TimedValueDto> points = e.getValue();
        // sb.append('[');
        // for (TimedValueDto tv : points) {
        // sb.append('(').append(tv.getTimestamp()).append(',').append(tv.getValue()).append(')').append(',');
        // }
        // if (!points.isEmpty())
        // sb.setLength(sb.length() - 1);
        // sb.append(']').append(',');
        // }
        // if (!computedMetrics.isEmpty())
        // sb.setLength(sb.length() - 1);
        // sb.append('}');

        sb.append(",source=").append(source);
        sb.append('}');
        return sb.toString();
    }
}
