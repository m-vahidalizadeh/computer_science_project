package data_structures;

import java.util.Comparator;

/**
 * Created by Mohammad on 6/18/2016.
 */
public class Person implements Comparable<Person>{
    String name;
    int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int compareTo(Person o) {
        int compareAge = o.getAge();

        //ascending order
        return this.age - compareAge;

        //descending order
//        return compareAge - this.age;
    }

    public static Comparator<Person> PersonNameComparator
            = new Comparator<Person>() {

        public int compare(Person persons1, Person persons2) {

            String personName1 = persons1.getName().toUpperCase();
            String personName2 = persons2.getName().toUpperCase();

            //ascending order
            return personName1.compareTo(personName2);

            //descending order
            //return fruitName2.compareTo(fruitName1);
        }

    };
}
