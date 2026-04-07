package services;

import model.UserImpl;
import model.UserModel;

/**
 * Singleton partagé qui maintient une instance unique de l'utilisateur
 * pendant toute la durée de vie de l'application.
 *
 * <p>Cette classe résout le problème de cohérence entre les services :
 * {@link StravaUserProfileServiceImpl} modifie le profil utilisateur (âge, genre, etc.)
 * et {@link StravaAnalyticsServiceImpl} a besoin de ces mêmes données pour calculer
 * les zones de fréquence cardiaque. Sans ce singleton, chaque service créerait
 * son propre {@link UserImpl} indépendant, et les modifications du frontend
 * n'auraient aucun effet sur les calculs analytiques.</p>
 *
 * <p>Utilisation :</p>
 * <pre>
 *     UserModel user = UserSession.getInstance();
 * </pre>
 */
public class UserSession {

    /**
     * L'instance unique de l'utilisateur, partagée entre tous les services.
     * Initialisée une seule fois lors du premier appel à {@link #getInstance()}.
     */
    private static UserModel instance;

    /**
     * Constructeur privé pour empêcher toute instanciation directe.
     * Seul {@link #getInstance()} permet d'accéder à l'utilisateur.
     */
    private UserSession() {}

    /**
     * Retourne l'instance unique de l'utilisateur.
     *
     * <p>Si l'instance n'existe pas encore, elle est créée avec des valeurs
     * par défaut raisonnables, et les seuils de zones cardiaques sont
     * immédiatement calculés pour éviter tout {@code NullPointerException}
     * lors des premiers appels aux services analytiques.</p>
     *

     *
     * @return l'instance unique et partagée de {@link UserModel}
     */
    public static UserModel getInstance() {
        if (instance == null) {
            // Création de l'utilisateur avec les valeurs par défaut
            UserImpl user = new UserImpl(23, true, 177, 77);

            // Calcul de la FCmax théorique selon la formule age/genre
            // (226 - âge pour femme, 220 - âge pour homme)
            user.setMaxHRUser(23, true);

            // Calcul des 4 seuils qui délimitent les 5 zones cardiaques Garmin
            // Z1 < 60% FCmax < Z2 < 70% < Z3 < 80% < Z4 < 90% < Z5
            user.setSeuilZoneHR(user.getMaxHRUser());

            instance = user;
        }
        return instance;
    }
}