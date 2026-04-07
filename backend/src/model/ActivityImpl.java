package model;
import java.util.List;

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
    private int tz1;
    private int tz2;
    private int tz3;
    private int tz4;
    private int tz5;
    private List<GpsPoint> route;
    private List<Split>splits ;


    public ActivityImpl(String id, Double distance, Double avgSpeed, String sport, int duration, Double avgPower, Double avgHR, Double maxHR, Double avgPace, int tz1, int tz2, int tz3, int tz4, int tz5, List<GpsPoint> route, List<Split>splits) {
        this.id = id;
        this.distance = distance;
        this.avgSpeed = avgSpeed;
        this.sport = sport;
        this.duration = duration;
        this.avgPower = avgPower;
        this.avgHR = avgHR;
        this.maxHR = maxHR;
        this.avgPace = avgPace;
        this.tz1 = tz1;
        this.tz2 = tz2;
        this.tz3 = tz3;
        this.tz4 = tz4;
        this.tz5 = tz5;
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
    public void setMaxHR(Double maxHR) {
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
    public int getTz1() {
        return tz1;
    }

    public void setTz1(int tz1) {
        this.tz1 = tz1;
    }

    @Override
    public int getTz2() {
        return tz2;
    }

    public void setTz2(int tz2) {
        this.tz2 = tz2;
    }

    @Override
    public int getTz3() {
        return tz3;
    }

    public void setTz3(int tz3) {
        this.tz3 = tz3;
    }

    @Override
    public int getTz4() {
        return tz4;
    }

    public void setTz4(int tz4) {
        this.tz4 = tz4;
    }

    @Override
    public int getTz5() {
        return tz5;
    }

    public void setTz5(int tz5) {
        this.tz5 = tz5;
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

    @Override
    public String toString() {
        return "ActivityImpl{" +
                "ID='" + id + '\'' +
                ", distance=" + distance +
                ", avgSpeed=" + avgSpeed +
                ", sport='" + sport + '\'' +
                ", Duration=" + duration +
                ", avgPower=" + avgPower +
                ", avgHR=" + avgHR +
                ", maxHR=" + maxHR +
                ", avgPace=" + avgPace +
                ", TZ1=" + tz1 +
                ", TZ2=" + tz2 +
                ", TZ3=" + tz3 +
                ", TZ4=" + tz4 +
                ", TZ5=" + tz5 +
                '}';
    }
}
