package test;

import java.util.ArrayList;

public class TestContact {
    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "18393991", 1));
        persons.add(new Person("Ann", "29892723", 45));
        persons.add(new Person("Doe", "234737839", 12));

        Person young = persons.get(0);
        for(int i = 1; i < persons.size(); i++){
            if (persons.get(i).getAge() < young.getAge()){
                young = persons.get(i);
            }
        }

        for(Contact contact : persons) {
            System.out.println(contact);
        }
        System.out.println("Young: " + young);
    }
}
