package services;

import dto.GenderDto;
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
        if (user != null){
            GenderDto genre = null;
            if (!user.getGenre()){
                genre = GenderDto.MALE;
            }
            else{
                genre = GenderDto.FEMALE;
            }

            return new UserProfileDto(user.getAge(),genre,user.getHeight(),user.getWeight());
        }
        return null;
    }




    @Override
    public void setUserProfile(UserProfileDto userProfileDto) {

        this.user.setAge(userProfileDto.getAge());

        this.user.setWeight(userProfileDto.getWeight());
        this.user.setHeight(userProfileDto.getHeight());

        boolean isFemale = (userProfileDto.getGender() == GenderDto.FEMALE);
        this.user.setGenre(isFemale);

    }

}
