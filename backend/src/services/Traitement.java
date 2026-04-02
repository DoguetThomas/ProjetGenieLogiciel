package services;

import model.User;
import model.Activity;
import model.StravaRecord;

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

    private Double getDist(String id){
        return null;
    }

    private Double getDuration(String id){
        return null;
    }

    private Double getAvgSpeed(String id){
        return null;
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


