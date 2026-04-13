package services;

import dto.ElevationDto;
import dto.VerticalRatioDto;
import model.StravaRecord;
import model.UserModel;

import java.util.List;
import java.util.Map;

public class EnhancedAnalyticsServiceImpl implements EnhancedAnalyticsService {
    private Traitement traitement;
    private Map<String, List<StravaRecord>> records;

    public EnhancedAnalyticsServiceImpl() {
        UserModel user = UserSession.getInstance();
        this.traitement = new Traitement("../data/strava.csv", user);
        this.records = this.traitement.getSortedRecords();
    }

    /**
     * Calcule le dénivelé positif total en parcourant chaque point d'altitude
     * et en cumulant uniquement les gains (différences positives entre deux points).
     *
     * @param activityId l'identifiant de l'activité
     * @return un {@link ElevationDto} avec le dénivelé positif total en mètres,
     *         ou 0.0 si l'activité est introuvable
     */
    @Override
    public ElevationDto getElevationGain(String activityId) {
        List<StravaRecord> record = records.get(activityId);
        if (record == null) return new ElevationDto(0.0); // ← était : return 0.0

        double totalElevation = 0;
        Double prev = 0.0;

        for (StravaRecord sr : record) {
            Double curr = sr.getEnhancedAltitude();
            if (prev != null && curr != null && curr > prev) {
                totalElevation += (curr - prev);
            }
            prev = curr;
        }

        return new ElevationDto(totalElevation); // ← était : return totalElevation
    }

    /**
     * Calcule le rapport vertical moyen pour une activité course à pied.
     * Formule : (oscillation verticale / longueur de foulée) * 100
     * La longueur de foulée est estimée depuis la vitesse et la cadence.
     * Retourne 0.0 si l'activité n'est pas de type RUN ou si les données
     * biomécanique sont insuffisantes.
     *
     * @param activityId l'identifiant de l'activité
     * @return un {@link VerticalRatioDto} avec le rapport vertical en pourcentage
     */
    @Override
    public VerticalRatioDto getVerticalRatio(String activityId) {
        if (!"RUN".equals(this.traitement.determineSportType(activityId))) {
            return new VerticalRatioDto(0.0); // ← était : return 0.0
        }

        if (records == null || !records.containsKey(activityId)) {
            return new VerticalRatioDto(0.0);
        }

        List<StravaRecord> recordsForActivity = this.records.get(activityId);
        if (recordsForActivity == null || recordsForActivity.isEmpty()) {
            return new VerticalRatioDto(0.0);
        }

        double totalRatio = 0.0;
        int count = 0;

        for (StravaRecord record : recordsForActivity) {
            Double speed = record.getEnhancedSpeed();
            Double cadence = record.getCadence();
            Double verticalOscillation = record.getVerticalOscillation();

            if (speed == null || cadence == null || verticalOscillation == null || cadence == 0.0) {
                continue;
            }

            double strideLengthMm = (speed / (cadence / 60.0)) * 1000.0;
            if (strideLengthMm == 0.0) continue;

            totalRatio += (verticalOscillation / strideLengthMm) * 100.0;
            count++;
        }

        return new VerticalRatioDto(count > 0 ? totalRatio / count : 0.0); // ← était : return totalRatio / count
    }
}