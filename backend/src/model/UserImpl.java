package model;

public class UserImpl implements UserModel{

    private int age;
    private boolean genre;
    private double height;
    private double weight;

    public UserImpl(int age, boolean genre, double height, double weight) {
        this.age = age;
        this.genre = genre;
        this.height = height;
        this.weight = weight;
    }
    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public void setAge(int age) {

    }

    @Override
    public boolean getGenre() {
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

    @Override
    public String toString() {
        return "UserImpl{" +
                "age=" + age +
                ", genre=" + genre +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
