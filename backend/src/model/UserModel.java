package model;

public interface UserModel {

    /**
     * Récupère l'âge de l'utilisateur.
     * @return L'âge de l'utilisateur en années.
     */
    public int getAge(int age);

    /**
     * Définit l'âge de l'utilisateur.
     * @param age Le nouvel âge de l'utilisateur.
     */
    public void setAge(int age);

    /**
     * Récupère l'état de l'attribut booléen genre.
     * @return La valeur booléenne de l'attribut genre.
     */
    public boolean getGenre(boolean genre);

    /**
     * Définit l'état de l'attribut booléen genre.
     * @param genre La nouvelle valeur booléenne.
     */
    public void setGenre(boolean genre);

    /**
     * Récupère la taille de l'utilisateur.
     * @return La taille de l'utilisateur.
     */
    public double getHeight();

    /**
     * Définit la taille de l'utilisateur.
     * @param height La nouvelle taille de l'utilisateur.
     */
    public void setHeight(double height);

    /**
     * Récupère le poids de l'utilisateur.
     * @return Le poids de l'utilisateur.
     */
    public double getWeight();

    /**
     * Définit le poids de l'utilisateur.
     * @param weight Le nouveau poids de l'utilisateur.
     */
    public void setWeight(double weight);

    /**
     * Définit le HRmax de l'utilisateur
     * @param  age de l'utilisateur
     * @param genre de l'utilisateur
     */
    public void setMaxHRUser(int age, boolean genre);

    /**
     * Récupète le HRmax de l'utilisateur
     * @return le HRmax de l'utilisateur
     */
    public double getMaxHRUser();


}
