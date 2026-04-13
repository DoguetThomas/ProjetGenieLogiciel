package launch;

import model.UserImpl;
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
        // UserSession reste null jusqu'à ce que l'utilisateur renseigne son profil
        UserSession.setInstance(new UserImpl()); // UserImpl vide, tous les champs à 0/false

        this.userProfileService = new StravaUserProfileServiceImpl();

        StravaAnalyticsServiceImpl analyticsImpl = new StravaAnalyticsServiceImpl();
        this.analyticsService = analyticsImpl;

        this.enhancedAnalyticsService = new EnhancedAnalyticsServiceImpl();

        this.importService = new ImportServiceImpl(analyticsImpl.getActivities(), analyticsImpl.getTraitement());

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
