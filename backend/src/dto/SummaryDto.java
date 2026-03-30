package dto;

/**
 * DTO containing aggregated summary values for an activity.
 */
public class SummaryDto {

	private final ActivityTypeDto sportType;
	private final double distanceKm;
	private final int durationSeconds;
	private final double avgHeartRate;
	private final int maxHeartRate;
	private final double avgSpeedKmh;
	private final double avgPaceMinPerKm;
	private final double avgPower;

	/**
	 * Creates an activity summary payload.
	 *
	 * @param sportType activity sport type
	 * @param distanceKm activity distance in kilometers
	 * @param durationSeconds activity duration in seconds
	 * @param avgHeartRate average heart rate
	 * @param maxHeartRate maximum heart rate
	 * @param avgSpeedKmh average speed in km/h
	 * @param avgPaceMinPerKm average pace in min/km
	 * @param avgPower average power
	 */
	public SummaryDto(ActivityTypeDto sportType, double distanceKm, int durationSeconds, double avgHeartRate,
                      int maxHeartRate, double avgSpeedKmh, double avgPaceMinPerKm, double avgPower) {
		this.sportType = sportType;
		this.distanceKm = distanceKm;
		this.durationSeconds = durationSeconds;
		this.avgHeartRate = avgHeartRate;
		this.maxHeartRate = maxHeartRate;
		this.avgSpeedKmh = avgSpeedKmh;
		this.avgPaceMinPerKm = avgPaceMinPerKm;
		this.avgPower = avgPower;
	}

	/**
	 * @return activity sport type
	 */
	public ActivityTypeDto getSportType() {
		return sportType;
	}

	/**
	 * @return activity distance in kilometers
	 */
	public double getDistanceKm() {
		return distanceKm;
	}

	/**
	 * @return activity duration in seconds
	 */
	public int getDurationSeconds() {
		return durationSeconds;
	}

	/**
	 * @return average heart rate
	 */
	public double getAvgHeartRate() {
		return avgHeartRate;
	}

	/**
	 * @return maximum heart rate
	 */
	public int getMaxHeartRate() {
		return maxHeartRate;
	}

	/**
	 * @return average speed in km/h
	 */
	public double getAvgSpeedKmh() {
		return avgSpeedKmh;
	}

	/**
	 * @return average pace in min/km
	 */
	public double getAvgPaceMinPerKm() {
		return avgPaceMinPerKm;
	}

	/**
	 * @return average power
	 */
	public double getAvgPower() {
		return avgPower;
	}
}
