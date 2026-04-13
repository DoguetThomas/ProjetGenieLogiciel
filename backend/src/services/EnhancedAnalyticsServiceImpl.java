package services;

import model.StravaRecord;
import model.UserModel;

import java.util.List;
import java.util.Map;

public class EnhancedAnalyticsServiceImpl implements EnhancedAnalyticsService{
    private Traitement traitement;
    private Map<String, List<StravaRecord>> records;
    public EnhancedAnalyticsServiceImpl(){
        UserModel user = UserSession.getInstance();

        this.traitement = new Traitement("../data/strava.csv", user);
        this.records = this.traitement.getSortedRecords();
    }
    @Override
    public double getElevationGain(String activityId){
        List<StravaRecord> record = records.get(activityId);
        if (record == null) return 0.0;

        double TotalELevation = 0;
        Double prev = (double) 0;

        for(StravaRecord sr:record){
            Double curr = sr.getEnhancedAltitude();
            if (prev != null && curr != null && curr > prev) {
                TotalELevation += (curr - prev);
                }
                prev = curr;
            }
        return TotalELevation;
    }

    /**
     * Get the running form score based on the
     * https://www.garmin.com/fr-FR/garmin-technology/running-science/running-dynamics/hill-score/
     * @param activityId the activity identifier
     * @return the value as double
     */
    /**
     * Calcule le rapport vertical moyen pour les activités de course à pied.
     * Exploite la cadence, l'oscillation verticale et le temps de contact au sol.
     * * @param id L'identifiant de la séance
     * @return La moyenne du rapport vertical en pourcentage (Double)
     */
    @Override
    public double getVerticalRatio(String id) {

        if (!"RUN".equals(this.traitement.determineSportType(id))) {
            System.out.println("1");
            return 0.0; // Retourne 0 si ce n'est pas de la course
        }

        if (records == null || !records.containsKey(id)) {
            System.out.println("2");
            return 0.0;
        }
        List<StravaRecord> recordsForActivity = this.records.get(id);

        if (recordsForActivity == null || recordsForActivity.isEmpty()) {
            System.out.println("3");
            return 0.0;
        }

        double totalRatio = 0.0;
        int count = 0;
        System.out.println(records);
        for (StravaRecord record : recordsForActivity) {
            Double speed = record.getEnhancedSpeed();
            // Vitesse (pour estimer la foulée)
            Double cadence = record.getCadence();
            // Donnée biomécanique 1
            Double verticalOscillation = record.getVerticalOscillation(); // Donnée biomécanique 2
            System.out.println(verticalOscillation);
            Double groundTime = record.getGroundTime();           // Donnée biomécanique 3

            // Ajout uniquement de la vérification pour éviter le NullPointerException et la division par zéro
            if (speed == null || cadence == null || verticalOscillation == null || cadence == 0.0) {
                continue;
            }

            double strideLengthMm = (speed / (cadence / 60.0)) * 1000.0;

            if (strideLengthMm == 0.0) {
                continue;
            }
            double pointRatio = (verticalOscillation / strideLengthMm) * 100.0;

            totalRatio += pointRatio;
            count++;
        }
        System.out.println(count);
        System.out.println(totalRatio);

        if (count > 0 && totalRatio > 0) {
            return (totalRatio / count);
        }
        return 0.0;
    }
    }
