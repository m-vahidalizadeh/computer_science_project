package algorithms.object_oriented;

/**
 * Created by Mohammad on 6/18/2016.
 */
import java.util.Arrays;
import java.util.Comparator;

public class ComparePersons{

    public static void main(String args[]){

        Person[] persons = new Person[3];

        Person mohammad = new Person("Mohammad",30);
        Person ali = new Person("Ali", 25);
        Person mahnaz = new Person("Mahnaz",24);

        persons[0] = mohammad;
        persons[1] = ali;
        persons[2] = mahnaz;

        System.out.println("Sort based on name ascending: ");
        Arrays.sort(persons,Person.PersonNameComparator);

        int i=0;
        for(Person temp: persons){
            System.out.println("Name " + ++i + " : " + temp.getName() +
                    ", Age : " + temp.getAge());
        }

        System.out.println("Sort based on age ascending: ");
        Arrays.sort(persons,Person.PersonAgeComparator);

        i=0;
        for(Person temp: persons){
            System.out.println("Name " + ++i + " : " + temp.getName() +
                    ", Age : " + temp.getAge());
        }

        System.out.println("Sort based on age descending: ");
        Arrays.sort(persons,Person.PersonAgeComparator.reversed());

        i=0;
        for(Person temp: persons){
            System.out.println("Name " + ++i + " : " + temp.getName() +
                    ", Age : " + temp.getAge());
        }

        System.out.println("Sort: ");
        Arrays.sort(persons);

        i=0;
        for(Person temp: persons){
            System.out.println("Name " + ++i + " : " + temp.getName() +
                    ", Age : " + temp.getAge());
        }

    }

    public static class Person implements Comparable<Person>{
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

        public static Comparator<Person> PersonAgeComparator
                = new Comparator<Person>() {

            public int compare(Person persons1, Person persons2) {

                int personAge1 = persons1.age;
                int personAge2 = persons2.age;

                //ascending order
                return personAge1 - personAge2;

                //descending order
                //return fruitName2.compareTo(fruitName1);
            }

        };
    }


}
