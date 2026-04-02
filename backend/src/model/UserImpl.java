package model;

public class UserImpl implements UserModel{

    private int age;
    private boolean genre;
    private double height;
    private double weight;
    private double maxHRUser;

    public UserImpl(int age, boolean genre, double height, double weight) {
        this.age = age;
        this.genre = genre;
        this.height = height;
        this.weight = weight;

    }

    @Override
    public int getAge(int age) {
        return 0;
    }

    @Override
    public void setAge(int age) {

    }

    @Override
    public boolean getGenre(boolean genre) {
        return false;
    }

    @Override
    public void setGenre(boolean genre) {

    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double weight) {

    }

    /**
     * Permet d'estimer le HRMax de l'User
     * @param age de l'utilisateur
     * @param genre de l'utilisateur true = Female et false = Homme
     * @return maxHRUser
     */
    @Override
    public double setMaxHRUser(int age, boolean genre) {
        this.age = age;
        this.genre = genre;

        // lever une exception pour l'age

        if (genre==true){
            maxHRUser = 226 - age;
        } else {
            maxHRUser = 220 - age;
        }
        //Pour les hommes : FCmax = 220 – âge
        //Pour les femmes : FCmax = 226 – âge
        return maxHRUser;
    }


    @Override
    public String toString() {
        return "UserImpl{" +
                "age=" + age +
                ", genre=" + genre +
                ", height=" + height +
                ", weight=" + weight +
                ", maxHRUser=" + maxHRUser +
                '}';
    }
}
