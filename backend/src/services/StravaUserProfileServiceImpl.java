package services;

import dto.UserProfileDto;

public class StravaUserProfileService implements UserProfileService{

    private User user;
    public StravaUserProfileServiceImpl(){
        this.user = new user();
        User user1 = new User(23,0,177,77);
        this.user.add(user1);
    }

    @Override
    public UserProfileDto getUserProfile() {
        UserProfileDto userDto = new user();
        if (user != null){
            UserProfileDto userDto = new UserProfileDto(user.get)
        }
    };

    @Override
    public void setUserProfile(UserProfileDto userProfileDto) {return null};

}
