package dto;

/**
 * DTO contenant le rapport vertical moyen pour une activité course.
 * Retourné par l'endpoint GET /api/activities/{id}/vertical-ratio
 */
public class VerticalRatioDto {

    /** Rapport vertical moyen en pourcentage */
    private final double verticalRatio;

    /**
     * @param verticalRatio rapport vertical moyen en pourcentage
     */
    public VerticalRatioDto(double verticalRatio) {
        this.verticalRatio = verticalRatio;
    }

    public double getVerticalRatio() {
        return verticalRatio;
    }
}