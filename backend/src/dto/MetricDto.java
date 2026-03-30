package dto;

import java.util.List;

/**
 * Time-series metric payload for a given activity type.
 */
public class MetricDto {

	private final ActivityTypeDto sportType;
	private final String metric;
	private final List<TimedValueDto> points;

	/**
	 * Creates a metric payload.
	 *
	 * @param sportType activity sport type
	 * @param metric metric identifier/name
	 * @param points metric sampled points
	 */
	public MetricDto(ActivityTypeDto sportType, String metric, List<TimedValueDto> points) {
		this.sportType = sportType;
		this.metric = metric;
		this.points = points;
	}

	/**
	 * @return sport type associated with the metric
	 */
	public ActivityTypeDto getSportType() {
		return sportType;
	}

	/**
	 * @return metric identifier/name
	 */
	public String getMetric() {
		return metric;
	}

	/**
	 * @return sampled metric points
	 */
	public List<TimedValueDto> getPoints() {
		return points;
	}
}
