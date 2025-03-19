package test;

import java.util.ArrayList;
import java.util.List;

public class DadsAndKids {
    static class Person{
        String name;
        String secName;
        int age;

        Person(String name, String secName, int age) {
            this.name = name;
            this.secName = secName;
            this.age = age;
        }
    }
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Филипп", "Иван", 38));
        people.add(new Person("Катя", "Филипп", 18));
        people.add(new Person("Максим", "Иван", 14));
        people.add(new Person("Иван", "Алексей", 56));

        for(Person person : people) {
            printKids(person, people);
        }
    }
    static void printKids(Person dad, List<Person> people) {
        for(Person person : people) {
            if (dad.name.equals(person.secName)) {
                System.out.println("Kid name: " + person.name + " dad name: " + dad.name);
            }
        }
    }
}

//TODO:
// для каждого человека вывести имя его младшего ребенка
