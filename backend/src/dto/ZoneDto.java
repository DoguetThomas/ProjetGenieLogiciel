package dto;

/**
 * DTO containing percentage values for heart-rate zones.
 */
public class ZoneDto {

	private final int[] zones;

	/**
	 * Creates a zone distribution payload.
	 *
	 * @param zones zone values, typically Z1 to Z5
	 */
	public ZoneDto(int[] zones) {
		this.zones = zones;
	}

	/**
	 * @return zone values array
	 */
	public int[] getZones() {
		return zones;
	}
}
