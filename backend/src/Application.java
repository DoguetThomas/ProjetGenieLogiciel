
import com.sun.net.httpserver.HttpServer;
import controllers.ActivitiesController;
import controllers.ImportController;
import controllers.UserProfileController;
import launch.ApplicationConfig;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Main application class that initializes the HTTP server and sets up the
 * controllers for handling API requests.
 * 
 * Do not modify this class.
 */
public class Application {
    public static void main(String[] args) throws IOException {

        ApplicationConfig config = ApplicationConfig.getConfiguration();

        HttpServer server = HttpServer.create(new InetSocketAddress(config.PORT), 0);
        server.createContext("/api/activities", new ActivitiesController(config.getAnalyticsService(), config.getEnhancedAnalyticsService()));
        server.createContext("/api/user-profile", new UserProfileController(config.getUserProfileService()));
        server.createContext("/api/import", new ImportController(config.getStravaSyncService()));

        server.setExecutor(null);
        server.start();

        System.out.println("   _____                          \n"
                + "  / ____|                         \n"
                + " | (___   ___ _ ____   _____ _ __ \n"
                + "  \\___ \\ / _ \\ '__\\ \\ / / _ \\ '__|\n"
                + "  ____) |  __/ |   \\ V /  __/ |   \n"
                + " |_____/ \\___|_|    \\_/ \\___|_|   \n"
                + "                                  \n"
                + "----------------------------------");

        System.out.println("Server started on port " + config.PORT);
    }

}
