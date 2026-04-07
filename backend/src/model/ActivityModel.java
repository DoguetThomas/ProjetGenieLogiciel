package model;

import java.util.List;

public interface ActivityModel {

    /**
     * Récupère l'identifiant unique de l'activité.
     * * @return L'identifiant de l'activité sous forme de chaîne de caractères.
     */

    public String getId();

    /**
     * Définit l'identifiant unique de l'activité.
     * * @param ID Le nouvel identifiant de l'activité.
     */
    public void setId(String id);

    /**
     * Récupère la distance parcourue lors de l'activité.
     * * @return La distance de l'activité.
     */
    public Double getDistance();

    /**
     * Définit la distance parcourue lors de l'activité.
     * * @param distance La nouvelle distance de l'activité.
     */
    public void setDistance(Double distance);

    /**
     * Récupère la vitesse moyenne de l'activité.
     * * @return La vitesse moyenne.
     */
    public Double getAvgSpeed();

    /**
     * Définit la vitesse moyenne de l'activité.
     * * @param avgSpeed La nouvelle vitesse moyenne.
     */
    public void setAvgSpeed(Double avgSpeed);

    /**
     * Récupère le type de sport pratiqué lors de l'activité.
     * * @return Le nom du sport.
     */
    public String getSport();

    /**
     * Définit le type de sport pratiqué lors de l'activité.
     * * @param sport Le nouveau nom du sport.
     */
    public void setSport(String sport);

    /**
     * Récupère la durée totale de l'activité.
     * * @return La durée de l'activité.
     */
    public int getDuration();

    /**
     * Définit la durée totale de l'activité.
     * * @param duration La nouvelle durée de l'activité.
     */
    public void setDuration(int duration);

    /**
     * Récupère la puissance moyenne développée pendant l'activité.
     * * @return La puissance moyenne.
     */
    public Double getAvgPower();

    /**
     * Définit la puissance moyenne développée pendant l'activité.
     * * @param avgPower La nouvelle puissance moyenne.
     */
    public void setAvgPower(Double avgPower);

    /**
     * Récupère la fréquence cardiaque moyenne (Heart Rate) de l'activité.
     * * @return La fréquence cardiaque moyenne.
     */
    public Double getAvgHR();

    /**
     * Définit la fréquence cardiaque moyenne de l'activité.
     * * @param avgHR La nouvelle fréquence cardiaque moyenne.
     */
    public void setAvgHR(Double avgHR);

    /**
     * Récupère la fréquence cardiaque maximale atteinte pendant l'activité.
     * * @return La fréquence cardiaque maximale.
     */
    public Double getMaxHR();

    /**
     * Définit la fréquence cardiaque maximale de l'activité.
     * * @param maxHR La nouvelle fréquence cardiaque maximale.
     */
    public void setMaxHR(Double maxHR);

    /**
     * Récupère l'allure moyenne (Pace) de l'activité.
     * * @return L'allure moyenne.
     */
    public Double getAvgPace();

    /**
     * Définit l'allure moyenne de l'activité.
     * * @param avgPace La nouvelle allure moyenne.
     */
    public void setAvgPace(Double avgPace);



    public void setZoneHR(List<Integer> zoneHR);

    public List<Integer> getZoneHR();
  
    public List<Split> getSplits();

    public List<GpsPoint> getRoute();
}
