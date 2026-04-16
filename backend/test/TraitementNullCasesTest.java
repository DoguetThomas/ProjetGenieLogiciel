import services.Traitement;

import model.ActivityModel;
import model.StravaRecord;
import model.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TraitementNullCasesTest {

    private UserImpl mockUser;

    @BeforeEach
    public void setUp() {
        // On initialise un faux utilisateur pour éviter les NullPointerException dans les zones
        mockUser = new UserImpl(25, false, 180, 75);
        mockUser.setMaxHRUser(25, false);
        mockUser.setSeuilZoneHR(mockUser.getMaxHRUser());
    }

    @Test
    public void testConstructorWithNullRecords() {
        // Cas : le fichier CSV était illisible et a renvoyé une liste NULL
        Traitement traitement = new Traitement((List<StravaRecord>) null, mockUser);
        List<ActivityModel> activities = traitement.getActivities();

        assertTrue(activities.isEmpty(), "La liste d'activités doit être vide si les records sont null");
    }

    @Test
    public void testConstructorWithEmptyRecords() {
        // Cas : le fichier CSV était vide (0 lignes)
        Traitement traitement = new Traitement(new ArrayList<>(), mockUser);
        List<ActivityModel> activities = traitement.getActivities();

        assertTrue(activities.isEmpty(), "La liste d'activités doit être vide si la liste des records est vide");
    }

    @Test
    public void testRecordsWithNullOrEmptyDatafile() {
        // Cas : Les lignes existent, mais l'identifiant "datafile" (le nom de l'activité) est absent
        StravaRecord badRecord1 = new StravaRecord();
        badRecord1.setDatafile(null); // ID carrément null

        StravaRecord badRecord2 = new StravaRecord();
        badRecord2.setDatafile("");   // ID vide

        List<StravaRecord> badRecords = List.of(badRecord1, badRecord2);
        Traitement traitement = new Traitement(badRecords, mockUser);

        // Le filtre `if (activityId == null || activityId.isEmpty()) continue;` doit faire son job
        assertTrue(traitement.getActivities().isEmpty(), "Les records sans ID valide doivent être ignorés");
    }

    @Test
    public void testDivisionByZeroAndNullMetrics() {
        // Cas le plus sévère : Une activité existe bien, mais TOUTES ses données sont nulles ou à 0
        StravaRecord emptyRecord = new StravaRecord();
        emptyRecord.setDatafile("Ghost_Activity"); // L'activité existe !
        emptyRecord.setDistance(0.0);
        emptyRecord.setEnhancedSpeed(null); // Pas de vitesse
        emptyRecord.setHeartRate(null);     // Pas de cardio
        emptyRecord.setPower(null);         // Pas de puissance
        emptyRecord.setTimestamp(null);     // Pas de date/heure

        Traitement traitement = new Traitement(List.of(emptyRecord), mockUser);
        List<ActivityModel> activities = traitement.getActivities();

        // 1. On vérifie que l'activité a bien été créée (car elle a un ID valide)
        assertEquals(1, activities.size(), "L'activité doit être créée malgré l'absence de données");
        ActivityModel activity = activities.get(0);

        // 2. On vérifie que toutes tes sécurités "if null" et "count > 0" ont bien protégé le code !
        assertEquals(0.0, activity.getDistance(), "La distance doit être 0.0");
        assertEquals(0, activity.getDuration(), "La durée doit être 0 car il n'y a pas de timestamp");
        assertEquals(0.0, activity.getAvgSpeed(), "La vitesse doit être 0.0 (Le count == 0 a empêché la division par 0)");
        assertNull(activity.getAvgPace(), "L'allure doit être null pour éviter la division par zéro");
        assertEquals("Inconnu", activity.getSport(), "Le sport doit être Inconnu si la vitesse est à 0");
        assertEquals(0.0, activity.getAvgHR(), "La FC Moyenne doit être 0.0");
        assertEquals(0.0, activity.getMaxHR(), "La FC Max doit être 0.0");
        assertEquals(0.0, activity.getAvgPower(), "La puissance doit être 0.0");

        // Bonus : tes splits et itinéraires doivent aussi être gérés proprement
        assertTrue(activity.getSplits().isEmpty(), "Les splits doivent être vides");
        assertTrue(activity.getRoute().isEmpty(), "La route doit être vide sans coordonnées GPS");
    }

    @Test
    public void testRecordSorter_WithInvalidDatafiles() {
        // Point avec datafile null
        StravaRecord rNull = new StravaRecord(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, LocalDateTime.now());

        // Point avec datafile vide
        StravaRecord rEmpty = new StravaRecord(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                "", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, LocalDateTime.now());

        Traitement traitement = new Traitement(List.of(rNull, rEmpty), mockUser);

        // La map triée doit être vide car les deux records ont été ignorés
        assertTrue(traitement.getActivities().isEmpty(), "Les activités sans ID valide doivent être ignorées.");
    }

    @Test
    public void testConstructor_WithInvalidFilePath() {
        try {
            // Teste l'appel du vrai constructeur avec un faux chemin
            Traitement traitement = new Traitement("chemin/inexistant.csv", mockUser);
            assertNotNull(traitement.getRecords(), "La liste doit être initialisée même si la lecture échoue");
        } catch (Exception e) {
            // Le test passe si une exception est levée
        }
    }
}