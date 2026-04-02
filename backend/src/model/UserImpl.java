package model;

public class UserImpl implements UserModel{

    private int age;
    private boolean genre;
    private double height;
    private double weight;


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
