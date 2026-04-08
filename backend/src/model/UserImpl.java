package model;

import java.util.ArrayList;

public class UserImpl implements UserModel{

    private int age;
    private boolean genre;
    private double height;
    private double weight;
    private double maxHRUser;
    private ArrayList<Double> seuilZoneHR;

    public UserImpl(){
    }

    public UserImpl(int age, boolean genre, double height, double weight) {
        this.age = age;
        this.genre = genre;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;

    }

    @Override
    public boolean getGenre() {
        return genre;
    }

    @Override
    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Permet d'estimer le HRMax de l'User
     * @param age de l'utilisateur
     * @param genre de l'utilisateur true = Female et false = Homme
     */
    @Override
    public void setMaxHRUser(int age, boolean genre) {
        this.age = age;
        this.genre = genre;

        // lever une exception pour l'age

        if (genre){
            maxHRUser = 226 - age;
        } else {
            maxHRUser = 220 - age;
        }
    }

    /**
     * Récupère la FCmax théorique de l'User
     * @return maxHRUser
     */
    @Override
    public double getMaxHRUser() {
        return maxHRUser;
    }


    /**
     * Définit les 4 seuils de HR pour séparer les 5 zones de HR de Garmin en format liste
     * @param maxHRUser de l'utilisateur
     */
    public void setSeuilZoneHR(double maxHRUser) {
        seuilZoneHR = new ArrayList<>();
        seuilZoneHR.add(maxHRUser * 0.60); // seuil Z1/Z2
        seuilZoneHR.add(maxHRUser * 0.70); // seuil Z2/Z3
        seuilZoneHR.add(maxHRUser * 0.80); // seuil Z3/Z4
        seuilZoneHR.add(maxHRUser * 0.90); // seuil Z4/Z5
    }

    /**
     * Récupère les 4 seuils de HR pour séparer les 5 zones de HR de Garmin
     * @return SeuilZoneHR
     */
    @Override
    public ArrayList<Double> getSeuilZoneHR() {
        return seuilZoneHR;
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "age=" + age +
                ", genre=" + genre +
                ", height=" + height +
                ", weight=" + weight +
                ", maxHRUser=" + maxHRUser +
                ", seuilZoneHR=" + seuilZoneHR +
                '}';
    }
}
