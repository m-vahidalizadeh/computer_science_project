package algorithms.object_oriented;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Mohammad on 5/19/2016.
 */

public class ComparingObjects {

    public static void main(String[] args) {

        ArrayList<PersonAsc> personsAsc = new ArrayList<PersonAsc>();
        PersonAsc p1= new PersonAsc(12,"Ali");
        personsAsc.add(p1);
        PersonAsc p2= new PersonAsc(16,"Alireza");
        personsAsc.add(p2);
        PersonAsc p3= new PersonAsc(18,"Mohammad");
        personsAsc.add(p3);
        PersonAsc p4= new PersonAsc(27,"Mahnaz");
        personsAsc.add(p4);
        PersonAsc p5= new PersonAsc(32,"Hamid");
        personsAsc.add(p5);

        Collections.sort(personsAsc);

        for (int i = 0; i < personsAsc.size(); i++) {
            System.out.println("Name: " + personsAsc.get(i).getName()+" Age: "+personsAsc.get(i).getAge());
        }

        System.out.println("------------------------------------------------------------");

        ArrayList<PersonDes> personsDes = new ArrayList<PersonDes>();
        PersonDes p6= new PersonDes(12,"Ali");
        personsDes.add(p6);
        PersonDes p7= new PersonDes(16,"Alireza");
        personsDes.add(p7);
        PersonDes p8= new PersonDes(18,"Mohammad");
        personsDes.add(p8);
        PersonDes p9= new PersonDes(27,"Mahnaz");
        personsDes.add(p9);
        PersonDes p10= new PersonDes(32,"Hamid");
        personsDes.add(p10);

        Collections.sort(personsDes);

        for (int i = 0; i < personsDes.size(); i++) {
            System.out.println("Name: " + personsDes.get(i).getName()+" Age: "+personsDes.get(i).getAge());
        }


    }

    public static class PersonAsc implements Comparable<PersonAsc>{
        int age;
        String name;

        PersonAsc(int age, String name){
            this.age= age;
            this.name= name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(PersonAsc o) {
            return this.getAge() - o.getAge();
        }
    }

    public static class PersonDes implements Comparable<PersonDes>{
        int age;
        String name;

        PersonDes(int age, String name){
            this.age= age;
            this.name= name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(PersonDes o) {
            return o.getAge() - this.getAge();
        }
    }

}
