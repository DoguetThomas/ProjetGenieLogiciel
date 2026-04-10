import java.time.LocalDateTime;
import model.ActivityImpl;
import model.ActivityModel;
import model.StravaRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.UserImpl;
import services.Traitement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TraitementTest {
    private Traitement traitement;
    private UserImpl User1;
    private StravaRecord Record1;
    private StravaRecord Record2;
    private StravaRecord Record3;

    @BeforeEach
    public void setUp() {
        // Initialisation de l'utilisateur (Femme, 22 ans)
        User1 = new UserImpl(22, true, 170, 60);
        User1.setMaxHRUser(22, true); // FC Max = 226 - 22 = 204
        User1.setSeuilZoneHR(User1.getMaxHRUser()); // Génération des 5 zones

        Record1 = new StravaRecord(0.0, 0.0, 0.0, 0.0, 200.0, 0.0, 80.0,
                "Activity1", 100.0, 100.0, 10.0, 140.0, 48.0, 2.0,
                LocalDateTime.of(2023, 12, 12, 12, 12, 10));

        Record2 = new StravaRecord(0.0, 0.0, 0.0, 0.0, 220.0, 0.0, 80.0,
                "Activity1", 200.0, 100.0, 10.0, 150.0, 48.0, 2.0,
                LocalDateTime.of(2023, 12, 12, 12, 12, 11));


        Record3 = new StravaRecord(0.0, 0.0, 0.0, 0.0, 210.0, 0.0, 80.0,
                "Activity1", 300.0, 100.0, 10.0, 160.0, 48.0, 2.0,
                LocalDateTime.of(2023, 12, 12, 12, 12, 12));

        // On injecte les records dans le traitement via ton super constructeur de test !
        List<StravaRecord> records = List.of(Record1, Record2, Record3);
        traitement = new Traitement(records, User1);
    }

    @Test
    public void testGetActivitiesMetrics() {

        List<ActivityModel> activities = traitement.getActivities();

        assertEquals(1, activities.size(), "Il devrait y avoir une seule activité dans la liste");
        ActivityModel activity = activities.get(0);

        assertEquals("Activity1", activity.getId());

        assertEquals(0.3, activity.getDistance(), 0.001, "La distance doit être 0.3 km");

        assertEquals(2, activity.getDuration(), "La durée doit être de 2 secondes");

        assertEquals(36.0, activity.getAvgSpeed(), 0.01, "La vitesse moyenne doit être 36 km/h");

        assertEquals("BIKE", activity.getSport());

        assertEquals(150.0, activity.getAvgHR(), "Moyenne de 140, 150 et 160 = 150");
        assertEquals(160.0, activity.getMaxHR(), "Le max est 160");

        assertEquals(210.0, activity.getAvgPower(), "Moyenne de 200, 220 et 210 = 210");
    }

    @Test
    public void testGetActivitiesWithEmptyRecords() {
        Traitement traitementVide = new Traitement(new ArrayList<>(), User1);
        List<ActivityModel> activities = traitementVide.getActivities();

        assertTrue(activities.isEmpty(), "La liste doit être vide si aucun record n'est fourni");
    }

    @Test
    public void testTimeInZonesCalculation() {
        // Test spécifique pour les zones cardiaques
        List<ActivityModel> activities = traitement.getActivities();
        ActivityModel activity = activities.get(0);

        List<Integer> zones = activity.getZoneHR();


        assertNotNull(zones, "Les zones ne doivent pas être nulles");
        assertEquals(5, zones.size(), "Il doit y avoir 5 zones");

        assertEquals(0, zones.get(0), "0 point en Z1");
        assertEquals(1, zones.get(1), "1 point en Z2 (140 bpm)");
        assertEquals(2, zones.get(2), "2 points en Z3 (150 et 160 bpm)");
        assertEquals(0, zones.get(3), "0 point en Z4");
        assertEquals(0, zones.get(4), "0 point en Z5");
    }
}