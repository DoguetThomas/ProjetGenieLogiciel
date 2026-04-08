package services;

import dto.FitActivityDto;

/**
 * Service contract for syncing activities from Strava.
 */
public interface ImportService {

    void importFitActivity(FitActivityDto request);

}
