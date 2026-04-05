package services;

import model.StravaRecord;
import model.UserModel;
import model.ActivityModel;
import model.ActivityImpl;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Traitement {
    private List<StravaRecord> records;
    private Map<String, List<StravaRecord>> sortedRecords;
    private UserModel user;

    public Traitement(String filePath, UserModel user) {
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
    private Map<String, List<StravaRecord>> RecordSorter() {
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

    /**
     * Déduit le type de sport en se basant sur la vitesse moyenne
     * Si la vitesse >  15 km/h, l'activité est considérée comme du vélo.
     * Sinon, elle est considérée comme de la course à pied.
     *
     * @param id L'identifiant de la séance.
     * @return "Vélo", "Course à pied", ou "Inconnu" si la vitesse ne peut pas être calculée.
     */
    private String determineSportType(String id) {
        // On récupère la vitesse moyenne grâce à la méthode AvgSpeed
        Double avgSpeed = this.getAvgSpeed(id);

        // Si la vitesse n'est pas calculable
        if (avgSpeed == null || avgSpeed.isNaN()) {
            return "Inconnu";
        }

        // Seuil
        double seuilVitesse = 15.0;

        if (avgSpeed > seuilVitesse) {
            return "Vélo";
        } else {
            return "Course à pied";
        }
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
        double distancekm = maxDistance / 1000;
        return distancekm;
    }

    /**
     * Calcule la durée totale d'une activité en secondes
     * Cherche le premier et le dernier instant enregistrés
     * pour calculer le temps écoulé entre les deux
     *
     * @param id L'identifiant de la séance
     * @return La durée totale en secondes (Double), ou 0.0 si introuvable
     */
    private Integer getDuration(String id) {

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
        long durationSeconds = Duration.between(startTime, endTime).getSeconds();

        return (int) durationSeconds;
    }

    /**
     * Calcule la vitesse moyenne en km/h d'une activité
     * @param id L'identifiant de l'activité
     * @return La vitesse moyenne en km/h : AvgSpeed
     */
    private Double getAvgSpeed(String id){
        // conversion du temps de secondes à heure
        double durationHour = this.getDuration(id) / 3600.0;
        // calcul distance
        double avgSpeed = this.getDist(id) / durationHour;
        return avgSpeed ;
    }


    /**
     * Calcule l'allure moyenne globale de l'activité
     * * @param id L'identifiant de la séance
     * @return L'allure en secondes par kilomètre (voir PaceDTO)
     */
    public Double getAvgPace(String id) {
        Double distanceKm = this.getDist(id);
        Integer duration = this.getDuration(id);

        // éviter la division par 0
        if (distanceKm <= 0) {
            return 0.0;
        }

        return duration / distanceKm;
    }

    /**
     * Calcule la FC moyenne pour une activité
     * @param id L'identifiant de l'activité
     * @return La FC moyenne sous forme de double
     */
    private Double getAvgHR(String id) {
        if (this.sortedRecords == null) {
            return 0.0;
        }
        // On récupère la liste des points Cardio pour l'activité ciblée par l'ID
        List<StravaRecord> recordsForActivity = this.sortedRecords.get(id);

        if (recordsForActivity.isEmpty ()) {
            // On retourne 0.0 si la liste est vide
            return 0.0;
        }
        // On crée une variable pour stocker la somme de toutes les FC et un compteur pour
        // combien de points on additionne
        double totalHR = 0.0 ;
        int count = 0;

        for (StravaRecord record : recordsForActivity) {
            Double HR = record.getHeartRate();

            // On vérifie qu'il existe bien une valeur de fréquence cardiaque pour chaque ligne
            if (HR != null && HR > 0) {
            totalHR += HR;
            count++;
            }
        }
        // Pour éviter la divison par 0
        if (count==0){
            return 0.0;
        }
        double avgHR = totalHR / count ;
        return avgHR;
    }
    /**
     * Calcule la FC max pour une activité
     * @param id L'identifiant de l'activité
     * @return La FC max sous forme de double
     */
    private Double getMaxHR(String id) {
        if (this.sortedRecords == null || !this.sortedRecords.containsKey(id)) {
            return 0.0;
        }
        List<StravaRecord> recordsForActivity = this.sortedRecords.get(id);
        // On vérifie que la liste crée n'est pas vide et existe bien
        if (recordsForActivity == null || recordsForActivity.isEmpty()) {
            return 0.0;
        }
        //On initialise le max à 0
        double maxHR = 0.0;

        for (StravaRecord record : recordsForActivity) {
            Double HR = record.getHeartRate();

            // On regarde la FC à chaque ligne et si elle est supérieur à notre max actuel
            if (HR != null && HR > maxHR) {
                // Elle devient notre nouveau max
                maxHR= HR;
            }
        }

        return maxHR;
    }

    /**
     * Calcule la puissance moyenne pour une activité
     * @param id L'identifiant de l'activité
     * @return La puissance moyenne sous forme de double
     */
    private Double getAvgPower(String id) {
        if (this.sortedRecords == null) {
            return 0.0;
        }
        // On récupère la liste des points pour l'activité ciblée par l'ID
        List<StravaRecord> recordsForActivity = this.sortedRecords.get(id);

        if (recordsForActivity == null || recordsForActivity.isEmpty ()) {
            // On retourne 0.0 si la liste est vide
            return 0.0;
        }
        // On crée une variable pour stocker la somme de toutes les puissances et un compteur pour
        // combien de points on additionne
        double totalPower = 0.0 ;
        int count = 0;

        for (StravaRecord record : recordsForActivity) {
            Double power = record.getPower();

            // On vérifie qu'il existe bien une valeur de puissance pour chaque ligne
            if (power != null ) {
                totalPower += power;
                count++;
            }
        }
        // Pour éviter la divison par 0
        if (count==0){
            return 0.0;
        }
        double avgPower = totalPower / count ;
        return avgPower;
    }

    /*il faut qu'on détermine comment représenter le Split
    public ??? getSplit(){
        return null;
    }
     */

    private Double[] calculateTimeInZones() {
        return null;
    }


    /**
     * Transforme les données triées par activité en une liste d'objets ActivityModel
     * * @return La liste de toutes les activités prêtes à être affichées.
     */
    /*public List<ActivityModel> getActivities() {
        List<ActivityModel> activityList = new ArrayList<>();

        // vérifie si les données sont bien chargées
        if (this.sortedRecords == null || this.sortedRecords.isEmpty()) {
            return activityList;
        }

        for (String id : this.sortedRecords.keySet()) {

            // crée une nouvelle instance de ton implémentation
            ActivityImpl activity = new ActivityImpl();

            // remplit l'objet avec les résultats des méthodes
            activity.setId(id);
            activity.setDistance(this.getDist(id));
            activity.setDuration(this.getDuration(id));
            activity.setAvgSpeed(this.getAvgSpeed(id));
            activity.setAvgPace(this.getAvgPace(id));
            activity.setAvgHR(this.getAvgHR(id));
            activity.setMaxHR(this.getMaxHR(id));
            activity.setAvgPower(this.getAvgPower(id));

            activity.setSport(this.determineSportType(id));

            // ajoute à la liste finale
            activityList.add(activity);
        }

        return activityList;*/
    }



}


