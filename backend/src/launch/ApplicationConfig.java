package launch;

import services.*;

/**
 * This class is intended to hold the application configuration, such as debug mode.
 * 
 * It uses the singleton pattern to provide a single instance of the
 * configuration throughout the application.
 */
public class ApplicationConfig {

    private static ApplicationConfig instance;

    public final boolean DEBUG = true;
    public final int PORT = 8090;
    private AnalyticsService analyticsService;
    private EnhancedAnalyticsService enhancedAnalyticsService;
    private UserProfileService userProfileService;
    private ImportService importService;

    private ApplicationConfig() {
        this.analyticsService = new StravaAnalyticsServiceImpl();
        this.userProfileService = new StravaUserProfileServiceImpl();
        this.enhancedAnalyticsService = new EnhancedAnalyticsServiceImpl();
    }

    public static ApplicationConfig getConfiguration() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }

        return instance;
    }

    public AnalyticsService getAnalyticsService() {
        return analyticsService;
    }

    public EnhancedAnalyticsService getEnhancedAnalyticsService() {
        return enhancedAnalyticsService;
    }

    public UserProfileService getUserProfileService() {
        return userProfileService;
    }

    public ImportService getStravaSyncService() {
        return importService;
    }
}
