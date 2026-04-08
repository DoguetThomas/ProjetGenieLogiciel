package services;

import model.UserImpl;
import model.UserModel;

/**
 * Singleton partagé qui maintient une instance unique de l'utilisateur.
 * {@link StravaUserProfileServiceImpl} modifie le profil utilisateur (âge, genre, etc.)
 * et {@link StravaAnalyticsServiceImpl} a besoin de ces mêmes données pour calculer
 * les zones de fréquence cardiaque.
 * Sans ce singleton, chaque service créerait son propre {@link UserImpl} indépendant.
 */
public class UserSession {

    private static UserModel instance;

    /** Seul {@link #getInstance()} permet d'accéder à l'utilisateur. */
    private UserSession() {}

    /**
     * Initialise le singleton avec un {@link UserModel} fourni de l'extérieur.
     * Permet aux tests d'injecter un utilisateur mocké sans toucher
     * à la logique de création.
     * N'écrase pas une instance déjà existante.
     *
     * @param user l'instance à utiliser comme utilisateur partagé
     */
    public static void setInstance(UserModel user) {
        if (instance == null) {
            instance = user;
        }
    }

    /**
     * Retourne l'instance partagée de l'utilisateur.
     * Retourne {@code null} si {@link #setInstance(UserModel)} n'a pas encore
     * été appelé — les services doivent vérifier ce cas.
     *
     * @return l'instance unique de {@link UserModel}, ou {@code null}
     */
    public static UserModel getInstance() {
        return instance;
    }

    /**
     * Réinitialise le singleton à null.
     * Réservé aux tests unitaires pour repartir d'un état propre
     * entre chaque cas de test.
     */
    public static void reset() {
        instance = null;
    }
}