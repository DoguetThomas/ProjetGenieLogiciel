import services.Traitement;

import model.ActivityModel;
import model.StravaRecord;
import model.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TraitementCardioTest {
    private Traitement traitement;

    @BeforeEach
    public void setUp() {
        // L'utilisateur est CRUCIAL ici pour la génération des zones
        UserImpl user1 = new UserImpl(22, true, 170, 60);
        user1.setMaxHRUser(22, true); // FC Max = 204
        user1.setSeuilZoneHR(user1.getMaxHRUser());

        // Points centrés sur la FC (140, 150, 160)
        StravaRecord record1 = new StravaRecord(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                "Activity1", 0.0, 0.0, 0.0, 140.0, 0.0, 0.0,
                LocalDateTime.of(2023, 12, 12, 12, 12, 10));

        StravaRecord record2 = new StravaRecord(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                "Activity1", 0.0, 0.0, 0.0, 150.0, 0.0, 0.0,
                LocalDateTime.of(2023, 12, 12, 12, 12, 11));

        StravaRecord record3 = new StravaRecord(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                "Activity1", 0.0, 0.0, 0.0, 160.0, 0.0, 0.0,
                LocalDateTime.of(2023, 12, 12, 12, 12, 12));

        traitement = new Traitement(List.of(record1, record2, record3), user1);
    }

    @Test
    public void testHeartRateAverages() {
        ActivityModel activity = traitement.getActivities().get(0);

        assertEquals(150.0, activity.getAvgHR(), "Moyenne de 140, 150 et 160 = 150");
        assertEquals(160.0, activity.getMaxHR(), "Le max est 160");
    }

    @Test
    public void testTimeInZonesCalculation() {
        ActivityModel activity = traitement.getActivities().get(0);
        List<Integer> zones = activity.getZoneHR();

        assertNotNull(zones, "Les zones ne doivent pas être nulles");
        assertEquals(5, zones.size(), "Il doit y avoir 5 zones");

        // FC Max = 204. Points: 140, 150, 160.
        assertEquals(0, zones.get(0), "0 point en Z1");
        assertEquals(1, zones.get(1), "1 point en Z2 (140 bpm)");
        assertEquals(2, zones.get(2), "2 points en Z3 (150 et 160 bpm)");
        assertEquals(0, zones.get(3), "0 point en Z4");
        assertEquals(0, zones.get(4), "0 point en Z5");
    }
}