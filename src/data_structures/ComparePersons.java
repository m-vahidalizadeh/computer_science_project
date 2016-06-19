package data_structures;

/**
 * Created by Mohammad on 6/18/2016.
 */
import java.util.Arrays;

public class ComparePersons{

    public static void main(String args[]){

        Person[] persons = new Person[3];

        Person mohammad = new Person("Mohammad",30);
        Person ali = new Person("Ali", 25);
        Person mahnaz = new Person("Mahnaz",24);

        persons[0] = mohammad;
        persons[1] = ali;
        persons[2] = mahnaz;

        Arrays.sort(persons,Person.PersonNameComparator);

        int i=0;
        for(Person temp: persons){
            System.out.println("Name " + ++i + " : " + temp.getName() +
                    ", Age : " + temp.getAge());
        }

    }
}
