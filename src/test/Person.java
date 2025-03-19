package test;

public class Person extends Contact {
    private int age;

    public Person(String name, String phoneNumber, int age) {
        super(name, phoneNumber);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                "} " + super.toString();
    }
}
