package model;

/**
 * Représente un utilisateur avec ses caractéristiques physiques.
 */
public class User {
    private int age;
    private boolean String; // Note : 'String' est utilisé ici comme nom de variable, ce qui est inhabituel mais valide.
    private double height;
    private double weight;

    /**
     * Récupère l'âge de l'utilisateur.
     * @return L'âge de l'utilisateur en années.
     */
    public int getAge() {
        return age;
    }

    /**
     * Définit l'âge de l'utilisateur.
     * @param age Le nouvel âge de l'utilisateur.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Récupère l'état de l'attribut booléen String.
     * @return La valeur booléenne de l'attribut String.
     */
    public boolean isString() {
        return String;
    }

    /**
     * Définit l'état de l'attribut booléen String.
     * @param string La nouvelle valeur booléenne.
     */
    public void setString(boolean string) {
        String = string;
    }

    /**
     * Récupère la taille de l'utilisateur.
     * @return La taille de l'utilisateur.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Définit la taille de l'utilisateur.
     * @param height La nouvelle taille de l'utilisateur.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Récupère le poids de l'utilisateur.
     * @return Le poids de l'utilisateur.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Définit le poids de l'utilisateur.
     * @param weight Le nouveau poids de l'utilisateur.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet User,
     * incluant l'âge, la valeur du booléen String, la taille et le poids.
     * @return Une chaîne de caractères représentant l'utilisateur.
     */
    @Override
    public java.lang.String toString() {
        return "User{" +
                "age=" + age +
                ", String=" + String +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}