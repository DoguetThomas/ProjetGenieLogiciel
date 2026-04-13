package dto;

/**
 * DTO contenant les statistiques de dénivelé pour une activité vélo.
 * Retourné par l'endpoint GET /api/activities/{id}/elevation
 */
public class ElevationDto {

    /** Dénivelé positif total en mètres */
    private final double totalAscent;

    /**
     * @param totalAscent dénivelé positif total en mètres
     */
    public ElevationDto(double totalAscent) {
        this.totalAscent = totalAscent;
    }

    public double getTotalAscent() {
        return totalAscent;
    }
}
