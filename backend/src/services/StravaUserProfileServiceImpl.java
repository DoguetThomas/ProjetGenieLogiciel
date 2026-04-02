package services;

import dto.UserProfileDto;
import model.UserImpl;
import model.UserModel;


public class StravaUserProfileServiceImpl implements UserProfileService{

    private UserModel user;
    public StravaUserProfileServiceImpl(){
        this.user = new UserImpl(23,true,177,77);
    }

    @Override
    public UserProfileDto getUserProfile() {
       /* UserProfileDto userDto = new user();
        if (user != null){
            UserProfileDto userDto = new UserProfileDto(user.get)
        }*/
        return null;
    }

    @Override
    public void setUserProfile(UserProfileDto userProfileDto) {}

}
