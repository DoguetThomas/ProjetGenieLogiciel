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

    private Map<String, List<StravaRecord>> RecordSorter(){
        Map<String, List<StravaRecord>> sortedMap = new HashMap<>();

        if (this.records == null) {
            return sortedMap;
        }

        for (StravaRecord record : this.records) {
            String activityId = record.getDatafile();

            if (activityId == null || activityId.isEmpty()) {
                continue;
            }

        if (!sortedMap.containsKey(activityId)) {
            sortedMap.put(activityId, new ArrayList<>());
        }

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


