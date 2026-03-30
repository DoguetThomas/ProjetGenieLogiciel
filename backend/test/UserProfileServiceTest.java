import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserProfileService;
import launch.ApplicationConfig;

import static org.junit.jupiter.api.Assertions.*;

public class UserProfileServiceTest {

    private UserProfileService service;

    @BeforeEach
    void beforeEach() {
        service = ApplicationConfig.getConfiguration().getUserProfileService();
    }

    @Test
    void serviceNotIntitialized() {
        service = null;
        assertNull(service);
    }
}