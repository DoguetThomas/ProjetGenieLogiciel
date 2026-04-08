package services;

import dto.GenderDto;
import dto.UserProfileDto;
import model.UserImpl;
import model.UserModel;

/**
 * Implémentation du service de gestion du profil utilisateur.
 *
 * <p>Utilise {@link UserSession} pour travailler sur l'instance partagée
 * de l'utilisateur, garantissant que toute modification du profil via
 * le frontend se répercute immédiatement sur les calculs analytiques
 * (notamment les zones de fréquence cardiaque).</p>
 */
public class StravaUserProfileServiceImpl implements UserProfileService {

    private UserModel user;

    /**
     * Initialise le service en récupérant l'instance partagée de l'utilisateur.
     * Aucune création d'objet ici : on pointe vers le singleton {@link UserSession}.
     */
    public StravaUserProfileServiceImpl() {
        this.user = UserSession.getInstance();
    }

    @Override
    public UserProfileDto getUserProfile() {
        if (user == null || !((UserImpl) user).isProfileConfigured()) {
            return null;
        }



        if (user != null) {
            GenderDto genre;
            if (!user.getGenre()) {
                genre = GenderDto.MALE;
            } else {
                genre = GenderDto.FEMALE;
            }
            return new UserProfileDto(user.getAge(), genre,user.getWeight() , user.getHeight());
        }
        return null;
    }



    /**
     * Met à jour le profil utilisateur et recalcule immédiatement les seuils
     * de zones cardiaques en fonction du nouvel âge et genre.
     *
     *
     * @param userProfileDto les nouvelles données du profil venant du frontend
     */

    @Override
    public void setUserProfile(UserProfileDto userProfileDto) {
        if (userProfileDto == null) return;

        // Ne pas faire new UserImpl() — modifier le singleton en place
        this.user.setAge(userProfileDto.getAge());
        this.user.setWeight(userProfileDto.getWeight());
        this.user.setHeight(userProfileDto.getHeight());
        boolean isFemale = (userProfileDto.getGender() == GenderDto.FEMALE);
        this.user.setGenre(isFemale);
        this.user.setMaxHRUser(this.user.getAge(), this.user.getGenre());
        this.user.setSeuilZoneHR(this.user.getMaxHRUser());
    }

}
