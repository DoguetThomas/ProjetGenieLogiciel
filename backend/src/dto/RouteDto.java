package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO containing route points.
 */
public class RouteDto {

	private final List<GeoDto> points;

	/**
	 * Creates a route DTO.
	 *
	 * @param points ordered geographic points composing the route
	 */
	public RouteDto(List<GeoDto> points) {
		this.points = points;
	}

	/**
	 * @return ordered geographic points composing the route
	 */
	public List<GeoDto> getPoints() {
		return points;
	}
}
