package controllers;

import dto.UserProfileDto;
import services.UserProfileService;

/**
 * Controller for handling user profile-related endpoints, including fetching and updating user profile information.
 * 
 * Do not modify this class.
 */
public class UserProfileController extends AbstractController {

	private final UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@Override
	protected void defineRoutes() {
		get("/api/user-profile", (exchange, params) -> json(userProfileService.getUserProfile()));

		post("/api/user-profile", (exchange, params) -> {

			UserProfileDto dto = readBody(exchange, UserProfileDto.class);

			userProfileService.setUserProfile(dto);

			return ok("User profile updated");
		});
	}
}