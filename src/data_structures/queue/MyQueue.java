package data_structures.queue;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Created by Mohammad on 6/18/2016.
 */
public class MyQueue {
    public static void main(String[] args) {

        Queue myQueue = new LinkedList();

        // add elements in the queue using offer() - return true/false
        myQueue.offer("Mohammad");
        myQueue.offer("Ali");
        boolean flag = myQueue.offer("Parinaz");

        System.out.println("Parinaz inserted successfully? "+flag);

        // add more elements using add() - throws IllegalStateException
        try {
            myQueue.add("Ahmad");
            myQueue.add("Mahnaz");
            myQueue.add("Vahid");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        System.out.println("Pick the head of the queue: " + myQueue.peek());

        System.out.println("The queue: " + myQueue.toString());

        String head = null;
        try {
            // remove head - remove()
            head = myQueue.remove().toString();
            System.out.print("1) Push out " + head + " from the queue ");
            System.out.println("and the new head is now: "+myQueue.element());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        // remove the head - poll()
        head = myQueue.poll().toString();
        System.out.print("2) Push out " + head + " from the queue");
        System.out.println("and the new head is now: "+myQueue.peek());

        // find out if the queue contains an object
        System.out.println("The queue: " + myQueue.toString());
        System.out.println("Does the queue contain 'Parinaz'? " + myQueue.contains("Parinaz"));
        System.out.println("Does the queue contain 'Mohammad'? " + myQueue.contains("Mohammad"));
    }
}


