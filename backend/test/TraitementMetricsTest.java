import model.StravaRecord;
import model.UserImpl;
import services.Traitement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TraitementMetricsTest {

    private UserImpl mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new UserImpl(22, true, 170, 60);
    }

    @Test
    public void testGetMetricList_AllBranches() {
        // Un record avec toutes les métriques
        StravaRecord record = new StravaRecord(0.0, 0.0,
                500.0,
                0.0,
                250.0,
                0.0,
                80.0,
                "Metric_Test", 0.0,
                150.0,
                12.5,
                145.0,
                0.0, 0.0, LocalDateTime.of(2023, 1, 1, 12, 0, 0));

        Traitement traitement = new Traitement(List.of(record), mockUser);

        // On vérifie que chaque condition if/else fonctionne
        assertTrue(traitement.getMetricList("Metric_Test", "Altitude").containsValue(150.0));
        assertTrue(traitement.getMetricList("Metric_Test", "Speed").containsValue(12.5));
        assertTrue(traitement.getMetricList("Metric_Test", "HeartRate").containsValue(145.0));
        assertTrue(traitement.getMetricList("Metric_Test", "Power").containsValue(250.0));
        assertTrue(traitement.getMetricList("Metric_Test", "Cadence").containsValue(80.0));
        assertTrue(traitement.getMetricList("Metric_Test", "GroundTime").containsValue(500.0));

        // Test de métrique inconnue
        Map<LocalDateTime, Number> unknownMap = traitement.getMetricList("Metric_Test", "Inconnu");

    }
}