import org.junit.jupiter.api.Test;
import services.AnalyticsService;
import launch.ApplicationConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ActivityAnalyticsService.
 */
public class ActivityAnalyticsServiceTest {

    AnalyticsService service;

    void beforeEach() {
        service = ApplicationConfig.getConfiguration().getAnalyticsService();
    }

    @Test
    void serviceNotIntitialized() {
        service = null;
        assertNull(service);
    }
}
