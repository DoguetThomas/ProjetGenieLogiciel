package controllers;

import services.ImportService;

import java.io.IOException;

/**
 * Controller exposing Strava synchronization endpoints.
 */
public class ImportController extends AbstractController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @Override
    protected void defineRoutes() {
        post("/api/import", (exchange, params) -> {
            byte[] fitBytes = exchange.getRequestBody().readAllBytes();
            if (fitBytes.length == 0) {
                throw new IOException("FIT file payload is required");
            }

            String providedName = exchange.getRequestHeaders().getFirst("X-Activity-Name");

            // Do not catch error, it will be handled at AbstractController level
            importService.importFitActivity(FitImportUtil.toSyncRequest(fitBytes, providedName));

            return ok("Fit file imported");
        });
    }
}
