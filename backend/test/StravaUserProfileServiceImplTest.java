import dto.GenderDto;
import dto.UserProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.StravaUserProfileServiceImpl;
import services.UserProfileService;

import static org.junit.jupiter.api.Assertions.*;

public class StravaUserProfileServiceImplTest {

    private UserProfileService service;
    private static final UserProfileDto DEFAULT = new UserProfileDto(23, GenderDto.FEMALE, 77.0, 177.0);

    @BeforeEach
    void setUp() {
        service = new StravaUserProfileServiceImpl();
        service.setUserProfile(DEFAULT);
    }

    @Test
    void getUserProfile_shouldNotBeNull() {
        assertNotNull(service.getUserProfile());
    }

    @Test
    void getUserProfile_shouldReturnDefaultAge() {
        assertEquals(23, service.getUserProfile().getAge());
    }

    @Test
    void getUserProfile_shouldReturnDefaultGender() {
        assertEquals(GenderDto.FEMALE, service.getUserProfile().getGender());
    }

    @Test
    void setUserProfile_thenGetShouldReflectNewAge() {
        service.setUserProfile(new UserProfileDto(30, GenderDto.MALE, 80.0, 180.0));
        assertEquals(30, service.getUserProfile().getAge());
    }

    @Test
    void setUserProfile_thenGetShouldReflectNewGender() {
        service.setUserProfile(new UserProfileDto(28, GenderDto.MALE, 80.0, 180.0));
        assertEquals(GenderDto.MALE, service.getUserProfile().getGender());
    }

    @Test
    void setUserProfile_calledTwice_shouldKeepLastValues() {
        service.setUserProfile(new UserProfileDto(25, GenderDto.MALE, 70.0, 175.0));
        service.setUserProfile(new UserProfileDto(35, GenderDto.FEMALE, 65.0, 168.0));

        assertEquals(35, service.getUserProfile().getAge());
        assertEquals(GenderDto.FEMALE, service.getUserProfile().getGender());
    }
}