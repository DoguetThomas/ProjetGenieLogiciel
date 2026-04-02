package model;

public class ActivityImpl implements ActivityModel{
    private String id;
    private Double distance;
    private Double avgSpeed;
    private String sport;
    private Double duration;
    private Double avgPower;
    private Double avgHR;
    private Double maxHR;
    private Double avgPace;
    private Double tz1;
    private Double tz2;
    private Double tz3;
    private Double tz4;
    private Double tz5;

    public ActivityImpl(String id, Double distance, Double avgSpeed, String sport, Double duration, Double avgPower, Double avgHR, Double maxHR, Double avgPace, Double tz1, Double tz2, Double tz3, Double tz4, Double tz5) {
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
    }


    @Override
    public String getId() {
        return "";
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public Double getDistance() {
        return 0.0;
    }


    @Override
    public void setDistance(Double distance) {

    }

    @Override
    public Double getAvgSpeed() {
        return 0.0;
    }


    @Override
    public void setAvgSpeed(Double avgSpeed) {

    }

    @Override
    public String getSport() {
        return "";
    }


    @Override
    public void setSport(String sport) {

    }

    @Override
    public Double getDuration(Double id) {
        return 0.0;
    }

    @Override
    public void setDuration(Double duration) {

    }

    @Override
    public Double getAvgPower() {
        return 0.0;
    }

    @Override
    public void setAvgPower(Double avgPower) {

    }

    @Override
    public Double getAvgHR() {
        return 0.0;
    }


    @Override
    public void setAvgHR(Double avgHR) {

    }

    @Override
    public Double getMaxHR() {
        return 0.0;
    }


    @Override
    public void setMaxHR(Double maxHR) {

    }

    @Override
    public Double getAvgPace() {
        return 0.0;
    }

    @Override
    public void setAvgPace(Double avgPace) {

    }

    @Override
    public Double getTz1() {
        return 0.0;
    }


    @Override
    public void setTz1(Double tz1) {

    }

    @Override
    public Double getTz2() {
        return 0.0;
    }


    @Override
    public void setTz2(Double tz2) {

    }

    @Override
    public Double getTz3() {
        return 0.0;
    }


    @Override
    public void setTz3(Double tz3) {

    }

    @Override
    public Double getTz4() {
        return 0.0;
    }


    @Override
    public void setTz4(Double tz4) {

    }

    @Override
    public Double getTz5() {
        return 0.0;
    }


    @Override
    public void setTz5(Double tz5) {

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
