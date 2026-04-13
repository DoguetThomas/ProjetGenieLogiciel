package model;
import java.util.List;
import java.util.Map;
import dto.TimedValueDto;

public class ActivityImpl implements ActivityModel{
    private String id;
    private Double distance;
    private Double avgSpeed;
    private String sport;
    private int duration;
    private Double avgPower;
    private Double avgHR;
    private Double maxHR;
    private Double avgPace;
    private List<Integer> zoneHR;
    private List<GpsPoint> route;
    private List<Split>splits ;

    public ActivityImpl(String id, Double distance, Double avgSpeed, String sport, int duration, Double avgPower, Double avgHR, Double maxHR, Double avgPace, List<Integer> zoneHR, List<GpsPoint> route, List<Split>splits) {
        this.id = id;
        this.distance = distance;
        this.avgSpeed = avgSpeed;
        this.sport = sport;
        this.duration = duration;
        this.avgPower = avgPower;
        this.avgHR = avgHR;
        this.maxHR = maxHR;
        this.avgPace = avgPace;
        this.zoneHR = zoneHR;
        this.route = route;
        this.splits = splits;
    }

    public ActivityImpl() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public Double getAvgSpeed() {
        return avgSpeed;
    }

    @Override
    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    @Override
    public String getSport() {
        return sport;
    }

    @Override
    public void setSport(String sport) {
        this.sport = sport;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public Double getAvgPower() {
        return avgPower;
    }

    @Override
    public void setAvgPower(Double avgPower) {
        this.avgPower = avgPower;
    }

    @Override
    public Double getAvgHR() {
        return avgHR;
    }

    @Override
    public void setAvgHR(Double avgHR) {
        this.avgHR = avgHR;
    }

    @Override
    public Double getMaxHR() {
        return maxHR;
    }

    @Override
    public void setMaxHR(double maxHR) {
        this.maxHR = maxHR;
    }

    @Override
    public Double getAvgPace() {
        return avgPace;
    }

    @Override
    public void setAvgPace(Double avgPace) {
        this.avgPace = avgPace;
    }

    @Override
    public void setZoneHR(List<Integer> zoneHR) {
        this.zoneHR = zoneHR;
    }

    @Override
    public List<Integer> getZoneHR() {
        return zoneHR;
    }


    @Override
    public List<GpsPoint> getRoute() {
        return this.route;
    }

    public void setRoute(List<GpsPoint> route) {
        this.route = route;
    }

    @Override
    public List<Split> getSplits() {
        return this.splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;

    }

    private Map<String, List<TimedValueDto>> computedMetrics;
    public Map<String, List<TimedValueDto>> getComputedMetrics() { return computedMetrics; }
    public void setComputedMetrics(Map<String, List<TimedValueDto>> m) { this.computedMetrics = m; }

    @Override
    public String toString() {
        return "ActivityImpl{" +
                "id='" + id + '\'' +
                ", distance=" + distance +
                ", avgSpeed=" + avgSpeed +
                ", sport='" + sport + '\'' +
                ", duration=" + duration +
                ", avgPower=" + avgPower +
                ", avgHR=" + avgHR +
                ", maxHR=" + maxHR +
                ", avgPace=" + avgPace +
                ", zoneHR=" + zoneHR +
                ", route=" + route +
                ", splits=" + splits +
                '}';
    }
}
