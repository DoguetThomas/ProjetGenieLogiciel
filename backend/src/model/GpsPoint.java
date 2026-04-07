package model;

// classe GpsPoint pour stocker les coordonnées de chaque point
public class GpsPoint {
    private Double latitude ;
    private Double longitude;

    public GpsPoint (Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude () {return latitude ;}
    public Double getLongitude () {return longitude ;}

    @Override
    public String toString() {
        return "[" + latitude + ", " + longitude + "]";
    }
}
