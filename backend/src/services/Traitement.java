package services;

import model.User;
import model.Activity;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Traitement {
    private List<services.StravaRecord> records;
    private Map<String, List<StravaRecord>> sortedRecords;
    private User user;

    public Traitement(String filePath, User user){
        this.records = StravaCsvReader.readCsv(filePath);
        this.sortedRecords = this.RecordSorter();
        this.user = user;
    }

    /**
     * Regroupe les enregistrements par activité
     * Utilise l'attribut "datafile" comme identifiant pour associer
     * chaque ligne à sa séance correspondante
     * * @return Une map qui regroupe les données par séance
     */
    private Map<String, List<StravaRecord>> RecordSorter(){
        Map<String, List<StravaRecord>> sortedMap = new HashMap<>();

        // une condition qui s'assure que les données ont bien été chargées
        if (this.records == null) {
            return sortedMap;
        }

        // crée une liste pour chaque activité
        for (StravaRecord record : this.records) {
            String activityId = record.getDatafile();

            // ignore les enregistrements invalides
            if (activityId == null || activityId.isEmpty()) {
                continue;
            }

        // crée une nouvelle liste si c'est le tout premier enregistrement de cette activité
        if (!sortedMap.containsKey(activityId)) {
            sortedMap.put(activityId, new ArrayList<>());
        }

        // ajout de l'enregistrement à la liste de son activité
        sortedMap.get(activityId).add(record);
        }

        return sortedMap;
    }

    private String determineSportType(String id){
        return null;
    }

    /**
     * Calcule la distance totale parcourue lors d'une activité.
     * Méthode qui recherche et retourne la valeur de distance maximale enregistrée.
     *
     * @param id L'identifiant de la séance (ex: "Activity_1").
     * @return La distance totale en mètres, ou 0.0 si l'activité est introuvable.
     */
    private Double getDist(String id) {

        // vérifie que la séance existe bien retourne 0.0 sinon
        if (this.sortedRecords == null || !this.sortedRecords.containsKey(id)) {
            return 0.0;
        }

        // récupère toutes les lignes de la séance
        List<StravaRecord> recordsForActivity = this.sortedRecords.get(id);

        double maxDistance = 0.0;

        // parcours toutes lignes pour trouver la distance la plus élevée
        for (StravaRecord record : recordsForActivity) {
            Double currentDist = record.getDistance();

            // met à jour le maximum
            if (currentDist != null && currentDist > maxDistance) {
                maxDistance = currentDist;
            }
        }
        // Conversion de la distance em km
        double Distancekm = maxDistance/1000 ;
        return Distancekm ;
    }

    /**
     * Calcule la durée totale d'une activité en secondes
     * Cherche le premier et le dernier instant enregistrés
     * pour calculer le temps écoulé entre les deux
     *
     * @param id L'identifiant de la séance
     * @return La durée totale en secondes (Double), ou 0.0 si introuvable
     */
    private Integer getDuration(String id){

        if (this.sortedRecords == null || !this.sortedRecords.containsKey(id)) {
            return 0;
        }

        List<StravaRecord> recordsForActivity = this.sortedRecords.get(id);

        // si la liste est vide, retourne 0.0
        if (recordsForActivity.isEmpty()) {
            return 0;
        }

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        // la boucle permet d'identifier le l'heure de départ et l'heure d'arrivée
        for (StravaRecord record : recordsForActivity) {
            LocalDateTime currentTime = record.getTimestamp();

            if (currentTime != null) {
                // si c'est le premier point lu ou s'il est plus ancien que startTime
                if (startTime == null || currentTime.isBefore(startTime)) {
                    startTime = currentTime;
                }
                // si c'est le premier point lu, ou s'il est plus récent que endTime
                if (endTime == null || currentTime.isAfter(endTime)) {
                    endTime = currentTime;
                }
            }
        }

        // au cas où aucune ligne n'avait d'heure valide
        if (startTime == null || endTime == null) {
            return 0;
        }

        // calcul de l'écart
        long DurationSeconds = Duration.between(startTime, endTime).getSeconds();

        return (int) DurationSeconds;
    }

    private Double getAvgSpeed(String id){

        this.getDuration(DurationSeconds)
        double AvgSpeed = DistanceKm /
        return AvgSpeed ;
    }


    private Double getAvgPace(String id){
        return null;
    }

    private Double getAvgHR(String id){
        return null;
    }

    private Double getMaxHR(String id){
        return null;
    }

    private Double getAvgPower(String id){
        return null;
    }

    /*il faut qu'on détermine comment représenter le Split
    public ??? getSplit(){
        return null;
    }
     */

    private Double[] calculateTimeInZones(){
        return null;
    }

    public List<Activity> getActivities(){
        return null;
    }


