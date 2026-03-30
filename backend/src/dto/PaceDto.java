package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO containing split-by-kilometer pace data.
 */
public class PaceDto {

	private final List<SplitDto> splits;

	/**
	 * Creates a pace DTO.
	 *
	 * @param splits list of kilometer splits
	 */
	public PaceDto(List<SplitDto> splits) {
		this.splits = splits;
	}

	/**
	 * Adds a new split entry.
	 *
	 * @param km split kilometer index
	 * @param splitSeconds split duration in seconds
	 * @param avgHeartRate average heart rate for the split
	 * @return current DTO for chaining
	 */
	public PaceDto addSplit(int km, int splitSeconds, int avgHeartRate) {
		this.splits.add(new SplitDto(km, splitSeconds, avgHeartRate));
		return this;
	}

	/**
	 * @return list of split entries
	 */
	public List<SplitDto> getSplits() {
		return splits;
	}
}
