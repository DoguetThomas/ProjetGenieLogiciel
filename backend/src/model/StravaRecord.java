package model;

import java.time.LocalDateTime;

public class StravaRecord {
    private Double airPower;
    private Double formPower;
    private Double groundTime;
    private Double legSpringStiffness;
    private Double power;
    private Double verticalOscillation;
    private Double cadence;
    private String datafile;
    private Double distance;
    private Double enhancedAltitude;
    private Double enhancedSpeed;
    private Double heartRate;
    private Double positionLat;
    private Double positionLong;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "StravaRecord{" +
                "datafile='" + datafile + '\'' +
                ", distance=" + distance +
                ", timestamp=" + timestamp +
                '}';
    }
    public Double getAirPower() {
        return airPower;
    }

    public Double getFormPower() {
        return formPower;
    }

    public Double getGroundTime() {
        return groundTime;
    }

    public Double getLegSpringStiffness() {
        return legSpringStiffness;
    }

    public Double getPower() {
        return power;
    }

    public Double getVerticalOscillation() {
        return verticalOscillation;
    }

    public Double getCadence() {
        return cadence;
    }

    public String getDatafile() {
        return datafile;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getEnhancedAltitude() {
        return enhancedAltitude;
    }

    public Double getEnhancedSpeed() {
        return enhancedSpeed;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public Double getPositionLat() {
        return positionLat;
    }

    public Double getPositionLong() {
        return positionLong;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setAirPower(Double airPower) {
        this.airPower = airPower;
    }

    public void setFormPower(Double formPower) {
        this.formPower = formPower;
    }

    public void setGroundTime(Double groundTime) {
        this.groundTime = groundTime;
    }

    public void setLegSpringStiffness(Double legSpringStiffness) {
        this.legSpringStiffness = legSpringStiffness;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public void setVerticalOscillation(Double verticalOscillation) {
        this.verticalOscillation = verticalOscillation;
    }

    public void setCadence(Double cadence) {
        this.cadence = cadence;
    }

    public void setDatafile(String datafile) {
        this.datafile = datafile;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setEnhancedAltitude(Double enhancedAltitude) {
        this.enhancedAltitude = enhancedAltitude;
    }

    public void setEnhancedSpeed(Double enhancedSpeed) {
        this.enhancedSpeed = enhancedSpeed;
    }

    public void setHeartRate(Double heartRate) {
        this.heartRate = heartRate;
    }

    public void setPositionLat(Double positionLat) {
        this.positionLat = positionLat;
    }

    public void setPositionLong(Double positionLong) {
        this.positionLong = positionLong;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
