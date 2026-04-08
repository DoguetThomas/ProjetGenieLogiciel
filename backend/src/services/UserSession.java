package services;

import model.UserImpl;
import model.UserModel;

/**
 * Singleton partagé qui maintient une instance unique de l'utilisateur
 * {@link StravaUserProfileServiceImpl} modifie le profil utilisateur (âge, genre, etc.)
 * et {@link StravaAnalyticsServiceImpl} a besoin de ces mêmes données pour calculer
 * les zones de fréquence cardiaque.
 * Sans ce singleton, chaque service créerait son propre {@link UserImpl} indépendant
 */
public class UserSession {

    private static UserModel instance;

    /**
     * Seul {@link #getInstance()} permet d'accéder à l'utilisateur.
     */
    private UserSession() {}

    /**
     * Retourne l'instance unique de l'utilisateur avec nouvelle zoneHR
     * @return l'instance unique et partagée de {@link UserModel}
     */
    public static UserModel getInstance() {
        return instance;
    }

}