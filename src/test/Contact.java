package test;

public class Contact {
    private String phoneNumber;
    private String name;

    public Contact(String name, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String toString() {
        return name + " " + phoneNumber;
    }
}
