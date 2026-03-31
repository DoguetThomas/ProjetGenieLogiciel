package model;

/**
 * Représente une activité sportive avec ses différentes métriques.
 */
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

    /**
     * Récupère l'identifiant unique de l'activité.
     * * @return L'identifiant de l'activité sous forme de chaîne de caractères.
     */
    public String getID() {
        return ID;
    }

    /**
     * Définit l'identifiant unique de l'activité.
     * * @param ID Le nouvel identifiant de l'activité.
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Récupère la distance parcourue lors de l'activité.
     * * @return La distance de l'activité.
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Définit la distance parcourue lors de l'activité.
     * * @param distance La nouvelle distance de l'activité.
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * Récupère la vitesse moyenne de l'activité.
     * * @return La vitesse moyenne.
     */
    public Double getAvgSpeed() {
        return avgSpeed;
    }

    /**
     * Définit la vitesse moyenne de l'activité.
     * * @param avgSpeed La nouvelle vitesse moyenne.
     */
    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    /**
     * Récupère le type de sport pratiqué lors de l'activité.
     * * @return Le nom du sport.
     */
    public String getSport() {
        return sport;
    }

    /**
     * Définit le type de sport pratiqué lors de l'activité.
     * * @param sport Le nouveau nom du sport.
     */
    public void setSport(String sport) {
        this.sport = sport;
    }

    /**
     * Récupère la durée totale de l'activité.
     * * @return La durée de l'activité.
     */
    public Double getDuration() {
        return Duration;
    }

    /**
     * Définit la durée totale de l'activité.
     * * @param duration La nouvelle durée de l'activité.
     */
    public void setDuration(Double duration) {
        Duration = duration;
    }

    /**
     * Récupère la puissance moyenne développée pendant l'activité.
     * * @return La puissance moyenne.
     */
    public Double getAvgPower() {
        return avgPower;
    }

    /**
     * Définit la puissance moyenne développée pendant l'activité.
     * * @param avgPower La nouvelle puissance moyenne.
     */
    public void setAvgPower(Double avgPower) {
        this.avgPower = avgPower;
    }

    /**
     * Récupère la fréquence cardiaque moyenne (Heart Rate) de l'activité.
     * * @return La fréquence cardiaque moyenne.
     */
    public Double getAvgHR() {
        return avgHR;
    }

    /**
     * Définit la fréquence cardiaque moyenne de l'activité.
     * * @param avgHR La nouvelle fréquence cardiaque moyenne.
     */
    public void setAvgHR(Double avgHR) {
        this.avgHR = avgHR;
    }

    /**
     * Récupère la fréquence cardiaque maximale atteinte pendant l'activité.
     * * @return La fréquence cardiaque maximale.
     */
    public Double getMaxHR() {
        return maxHR;
    }

    /**
     * Définit la fréquence cardiaque maximale de l'activité.
     * * @param maxHR La nouvelle fréquence cardiaque maximale.
     */
    public void setMaxHR(Double maxHR) {
        this.maxHR = maxHR;
    }

    /**
     * Récupère l'allure moyenne (Pace) de l'activité.
     * * @return L'allure moyenne.
     */
    public Double getAvgPace() {
        return avgPace;
    }

    /**
     * Définit l'allure moyenne de l'activité.
     * * @param avgPace La nouvelle allure moyenne.
     */
    public void setAvgPace(Double avgPace) {
        this.avgPace = avgPace;
    }

    /**
     * Récupère le temps passé dans la zone d'entraînement 1 (TZ1).
     * * @return Le temps en zone 1.
     */
    public Double getTZ1() {
        return TZ1;
    }

    /**
     * Définit le temps passé dans la zone d'entraînement 1 (TZ1).
     * * @param TZ1 Le nouveau temps en zone 1.
     */
    public void setTZ1(Double TZ1) {
        this.TZ1 = TZ1;
    }

    /**
     * Récupère le temps passé dans la zone d'entraînement 2 (TZ2).
     * * @return Le temps en zone 2.
     */
    public Double getTZ2() {
        return TZ2;
    }

    /**
     * Définit le temps passé dans la zone d'entraînement 2 (TZ2).
     * * @param TZ2 Le nouveau temps en zone 2.
     */
    public void setTZ2(Double TZ2) {
        this.TZ2 = TZ2;
    }

    /**
     * Récupère le temps passé dans la zone d'entraînement 3 (TZ3).
     * * @return Le temps en zone 3.
     */
    public Double getTZ3() {
        return TZ3;
    }

    /**
     * Définit le temps passé dans la zone d'entraînement 3 (TZ3).
     * * @param TZ3 Le nouveau temps en zone 3.
     */
    public void setTZ3(Double TZ3) {
        this.TZ3 = TZ3;
    }

    /**
     * Récupère le temps passé dans la zone d'entraînement 4 (TZ4).
     * * @return Le temps en zone 4.
     */
    public Double getTZ4() {
        return TZ4;
    }

    /**
     * Définit le temps passé dans la zone d'entraînement 4 (TZ4).
     * * @param TZ4 Le nouveau temps en zone 4.
     */
    public void setTZ4(Double TZ4) {
        this.TZ4 = TZ4;
    }

    /**
     * Récupère le temps passé dans la zone d'entraînement 5 (TZ5).
     * * @return Le temps en zone 5.
     */
    public Double getTZ5() {
        return TZ5;
    }

    /**
     * Définit le temps passé dans la zone d'entraînement 5 (TZ5).
     * * @param TZ5 Le nouveau temps en zone 5.
     */
    public void setTZ5(Double TZ5) {
        this.TZ5 = TZ5;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet Activity,
     * incluant l'ID, la distance, la vitesse moyenne, le sport et la durée.
     * * @return Une chaîne de caractères représentant l'activité.
     */
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