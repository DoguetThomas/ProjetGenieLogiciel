package model;

public class Activity {
    private String ID;
    private Double distance;
    private Double avgSpeed;
    private String sport;
    private Double Duration;
    private Double avgPower;
    private Double avgHR;
    private Double maxHR;
    private Double avgPace;
    private Double TZ1;
    private Double TZ2;
    private Double TZ3;
    private Double TZ4;
    private Double TZ5;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Double getDuration() {
        return Duration;
    }

    public void setDuration(Double duration) {
        Duration = duration;
    }

    public Double getAvgPower() {
        return avgPower;
    }

    public void setAvgPower(Double avgPower) {
        this.avgPower = avgPower;
    }

    public Double getAvgHR() {
        return avgHR;
    }

    public void setAvgHR(Double avgHR) {
        this.avgHR = avgHR;
    }

    public Double getMaxHR() {
        return maxHR;
    }

    public void setMaxHR(Double maxHR) {
        this.maxHR = maxHR;
    }

    public Double getAvgPace() {
        return avgPace;
    }

    public void setAvgPace(Double avgPace) {
        this.avgPace = avgPace;
    }

    public Double getTZ1() {
        return TZ1;
    }

    public void setTZ1(Double TZ1) {
        this.TZ1 = TZ1;
    }

    public Double getTZ2() {
        return TZ2;
    }

    public void setTZ2(Double TZ2) {
        this.TZ2 = TZ2;
    }

    public Double getTZ3() {
        return TZ3;
    }

    public void setTZ3(Double TZ3) {
        this.TZ3 = TZ3;
    }

    public Double getTZ4() {
        return TZ4;
    }

    public void setTZ4(Double TZ4) {
        this.TZ4 = TZ4;
    }

    public Double getTZ5() {
        return TZ5;
    }

    public void setTZ5(Double TZ5) {
        this.TZ5 = TZ5;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Activity{" +
                "ID='" + ID + '\'' +
                ", distance=" + distance +
                ", avgSpeed=" + avgSpeed +
                ", sport='" + sport + '\'' +
                ", Duration=" + Duration +
                '}';
    }
}
