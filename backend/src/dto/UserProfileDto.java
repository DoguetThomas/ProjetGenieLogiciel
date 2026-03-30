package dto;

/**
 * DTO representing user profile attributes used by profile endpoints.
 */
public class UserProfileDto {

	private int age;
	private GenderDto gender;
	private double weight;
	private double height;

	/**
	 * No-arg constructor required for Jackson deserialization.
	 */
	public UserProfileDto() {
	}

	/**
	 * Creates a user profile DTO.
	 *
	 * @param age user age in years
	 * @param gender user gender
	 * @param weight user weight in kilograms
	 * @param height user height in centimeters
	 */
	public UserProfileDto(int age, GenderDto gender, double weight, double height) {
		this.age = age;
		this.gender = gender;
		this.weight = weight;
		this.height = height;
	}

	/**
	 * @return user age in years
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age user age in years
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return user gender
	 */
	public GenderDto getGender() {
		return gender;
	}

	/**
	 * @param gender user gender
	 */
	public void setGender(GenderDto gender) {
		this.gender = gender;
	}

	/**
	 * @return user weight in kilograms
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight user weight in kilograms
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return user height in centimeters
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height user height in centimeters
	 */
	public void setHeight(double height) {
		this.height = height;
	}
}
