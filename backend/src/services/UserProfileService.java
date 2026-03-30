package services;

import dto.UserProfileDto;

public interface UserProfileService {

	public UserProfileDto getUserProfile();

	public void setUserProfile(UserProfileDto userProfileDto);

}
