package services;

import dto.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.ActivityNotFoundException;
import model.*;

public class StravaAnalyticsServiceImpl implements AnalyticsService {
    private List<ActivityModel> activities;
    private final Traitement traitement;

    /**
     * Initialise le service analytique en utilisant l'instance partagée de l'utilisateur via {@link UserSession}
     * Cela garantit que {@link Traitement} utilise le même {@link model.UserModel}
     * que {@link StravaUserProfileServiceImpl}.
     * Si l'utilisateur met à jour son profil depuis le frontend, les seuils de zones cardiaques recalculés
     * sont automatiquement pris en compte lors des prochains appels analytiques.
     */


    public StravaAnalyticsServiceImpl() {
        this.activities = new ArrayList<>();

        // Récupération de l'instance partagée — jamais de new UserImpl() ici
        UserModel user = UserSession.getInstance();

        this.traitement = new Traitement("../data/strava.csv", UserSession.getInstance());
        this.activities = this.traitement.getActivities();
    }

    private ActivityModel findActivityById(String id) throws ActivityNotFoundException {
        if (id == null) throw new IllegalArgumentException("L'ID ne peut pas être null");

        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {
                return activity;
            }
        }
        throw new ActivityNotFoundException("Activité inconnue pour l'id : " + id);
    }

    /**
     * Récupère la liste complète des activités.
     * * @return AllActivitiesDto Un objet contenant la liste des activités simplifiées (id, sport, distance).
     */
    @Override
    public AllActivitiesDto getAllActivities() {
        AllActivitiesDto allActivitiesDto = new AllActivitiesDto(new ArrayList<>());
        for (ActivityModel activity : this.activities){
            if (activity !=null ){
                ActivityTypeDto sport = determineSport(activity);

                allActivitiesDto.addActivity(activity.getId(), sport, activity.getDistance());
            }
        }
        return allActivitiesDto;
    }

    /**
     * Détermine le type de sport associé à une activité (Vélo ou Course à pied).
     * * @param activity L'activité dont on souhaite déterminer le sport.
     * @return ActivityTypeDto L'énumération correspondant au sport (BIKE ou RUN).
     */
    private static ActivityTypeDto determineSport(ActivityModel activity){
        if ("BIKE".equals(activity.getSport())) {
            return ActivityTypeDto.BIKE;
        } else {
            // Par défaut, ou si c'est "RUN"
            return ActivityTypeDto.RUN;
        }
    }

    /**
     * Récupère le résumé détaillé des statistiques d'une activité spécifique.
     * * @param id L'identifiant unique de l'activité.
     * @return SummaryDto Les statistiques globales de l'activité (distance, durée, FC, vitesse, allure, puissance).
     * @throws ActivityNotFoundException Si aucune activité correspondante n'est trouvée.
     */
    @Override
    public SummaryDto getSummary(String id) throws ActivityNotFoundException {
        ActivityModel activity = findActivityById(id);
        ActivityTypeDto sport = determineSport(activity);
        double distanceKm = (activity.getDistance() != null) ? activity.getDistance() : 0.0;
        int maxHrInt = (activity.getMaxHR() != null) ? activity.getMaxHR().intValue() : 0;
        double avgHr = (activity.getAvgHR() != null) ? activity.getAvgHR() : null;
        double avgSpeed = (activity.getAvgSpeed() != null) ? activity.getAvgSpeed() : 0.0;
        double avgPace = (activity.getAvgPace() != null) ? activity.getAvgPace() : 0.0;
        double avgPower = (activity.getAvgPower() != null) ? activity.getAvgPower() : 0.0;

        return new SummaryDto(
                sport,
                distanceKm,
                activity.getDuration(), // Déjà un int
                avgHr,
                maxHrInt,
                avgSpeed,
                avgPace,
                avgPower
        );
    }

    /**
     * Récupère le tracé GPS d'une activité.
     * * @param id L'identifiant unique de l'activité.
     * @return RouteDto Un objet contenant la liste des coordonnées géographiques (latitude, longitude).
     * @throws ActivityNotFoundException Si aucune activité correspondante n'est trouvée.
     */
    @Override
    public RouteDto getRoute(String id) throws ActivityNotFoundException {
        ActivityModel activity = findActivityById(id);
        List<GpsPoint> pointsGps = activity.getRoute();
        List<GeoDto> route = new ArrayList<>();
        for (GpsPoint point : pointsGps){
            route.add(new GeoDto(point.getLatitude(), point.getLongitude()));
        }
        return new RouteDto(route);
    }

    /**
     * Méthode utilitaire générique pour récupérer les données temporelles d'une métrique spécifique.
     * * @param id L'identifiant unique de l'activité.
     * @param metricName Le nom de la métrique à extraire (ex: "Altitude", "Speed").
     * @return MetricDto Un objet contenant les paires de valeurs temporelles pour la métrique demandée.
     * @throws ActivityNotFoundException Si aucune activité correspondante n'est trouvée.
     */
    private MetricDto getMetricData(String id, String metricName) throws ActivityNotFoundException {
        ActivityModel activity = findActivityById(id);
        ActivityTypeDto sport = determineSport(activity);
        List<TimedValueDto> points = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Map<LocalDateTime, Number> rawData = this.traitement.getMetricList(activity.getId(), metricName);
        Map<LocalDateTime, Number> sortedData = new TreeMap<>(rawData);
        for (Map.Entry<LocalDateTime, Number> entry : sortedData.entrySet()){
            String timeText = formatter.format(entry.getKey());
            points.add(new TimedValueDto(timeText, entry.getValue()));
        }
        return new MetricDto(sport, metricName, points);
    }

    /**
     * Récupère les données d'évolution de l'altitude d'une activité.
     * * @param id L'identifiant unique de l'activité.
     * @return MetricDto Les données d'altitude formatées avec leurs horodatages.
     * @throws ActivityNotFoundException Si l'activité n'est pas trouvée.
     */
    @Override
    public MetricDto getMetricsAltitude(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Altitude");
    }

    /**
     * Récupère les données d'évolution de la vitesse d'une activité.
     * * @param id L'identifiant unique de l'activité.
     * @return MetricDto Les données de vitesse formatées avec leurs horodatages.
     * @throws ActivityNotFoundException Si l'activité n'est pas trouvée.
     */
    @Override
    public MetricDto getMetricsSpeed(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Speed");
    }

    /**
     * Récupère les données d'évolution de la fréquence cardiaque d'une activité.
     * * @param id L'identifiant unique de l'activité.
     * @return MetricDto Les données de fréquence cardiaque formatées avec leurs horodatages.
     * @throws ActivityNotFoundException Si l'activité n'est pas trouvée.
     */
    @Override
    public MetricDto getMetricsHeartRate(String id) throws ActivityNotFoundException {
        return getMetricData(id, "HeartRate");
    }

    /**
     * Récupère les données d'évolution de la puissance d'une activité.
     * * @param id L'identifiant unique de l'activité.
     * @return MetricDto Les données de puissance formatées avec leurs horodatages.
     * @throws ActivityNotFoundException Si l'activité n'est pas trouvée.
     */
    @Override
    public MetricDto getMetricsPower(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Power");
    }

    /**
     * Récupère les données d'évolution de la cadence d'une activité.
     * * @param id L'identifiant unique de l'activité.
     * @return MetricDto Les données de cadence formatées avec leurs horodatages.
     * @throws ActivityNotFoundException Si l'activité n'est pas trouvée.
     */
    @Override
    public MetricDto getMetricsCadence(String id) throws ActivityNotFoundException {
        return getMetricData(id, "Cadence");
    }

    /**
     * Récupère les données d'évolution du temps de contact au sol d'une activité.
     * * @param id L'identifiant unique de l'activité.
     * @return MetricDto Les données de temps de contact au sol formatées avec leurs horodatages.
     * @throws ActivityNotFoundException Si l'activité n'est pas trouvée.
     */
    @Override
    public MetricDto getMetricsGroundTime(String id) throws ActivityNotFoundException {
        return getMetricData(id, "GroundTime");
    }

    /**
     * Récupère les données d'allure (pace) avec les temps intermédiaires (splits) par kilomètre.
     * * @param id L'identifiant unique de l'activité.
     * @return PaceDto Un objet contenant la liste des temps intermédiaires, ou null si l'activité n'est pas trouvée.
     */
    @Override
    public PaceDto getMetricsPace(String id) throws ActivityNotFoundException{
        ActivityModel activity = findActivityById(id);
        List<SplitDto> liste = new ArrayList<>();
        List<Split> splits= activity.getSplits();
        for (Split split : splits){
            SplitDto splitDto = new SplitDto(split.getKm(), split.getDurationSeconds(), split.getAvgHeartRate());
            liste.add(splitDto);
        }
        return new PaceDto(liste);
    }

    /**
     * Récupérer les duration pour chaque zone pour une activité
     * @param id de l'activité
     * @return un {@link ZoneDto} contenant 5 pourcentages (Z1 à Z5)
     */
    @Override
    public ZoneDto getMetricsZone(String id) {
        // Profil non renseigné → zones vides
        UserModel user = UserSession.getInstance();
        if (user == null || user.getSeuilZoneHR() == null) {
            return new ZoneDto(new int[]{0, 0, 0, 0, 0});
        }

        if (id == null) {
            return null;
        }

        for (ActivityModel activity : this.activities) {
            if (activity != null && id.equals(activity.getId())) {

                ArrayList<Integer> rawZones = this.traitement.getTimeInZones(id);

                // Calcul du total pour convertir en pourcentages
                int total = 0;
                for (int z : rawZones) {
                    total += z;
                }

                // Conversion en pourcentages
                int[] percentages = new int[rawZones.size()];
                if (total > 0) {
                    for (int i = 0; i < rawZones.size(); i++) {
                        percentages[i] = (int) Math.round((rawZones.get(i) * 100.0) / total);
                    }
                }

                return new ZoneDto(percentages);
            }
        }
        return null;
    }
}
