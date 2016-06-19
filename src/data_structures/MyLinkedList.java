package data_structures;

import java.util.LinkedList;

/**
 * Created by Mohammad on 6/18/2016.
 */
public class MyLinkedList {

    public static void main(String args[]) {
        // create a linked list
        LinkedList ll = new LinkedList();
        // add elements to the linked list
        ll.add("Ali");
        ll.add("Reza");
        ll.add("Ahmad");
        ll.addLast("Zahra");
        ll.add("Hosein");
        ll.addLast("Zahra");
        ll.addLast("Mohammad");
        ll.addFirst("Mahnaz");
        ll.addLast("Zahra");
        ll.add(1, "Parinaz");
        System.out.println("Original contents of ll: " + ll);

        // remove elements from the linked list
//        ll.remove("Ahmad");
        ll.remove(2);
        System.out.println("Contents of ll after deletion: "
                + ll);

        // remove first and last elements
        ll.removeFirst();
        ll.removeLast();
        System.out.println("ll after deleting first and last: "
                + ll);

        // get and set a value
        Object val = ll.get(2);
        ll.set(2, (String) val + " Changed");
        System.out.println("ll after change: " + ll);

        System.out.println("First index of 2 is:"+
                ll.indexOf("Zahra"));
        System.out.println("Last index of 2 is:"+
                ll.lastIndexOf("Zahra"));
    }

}
