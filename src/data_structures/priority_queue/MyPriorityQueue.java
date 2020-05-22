package data_structures.priority_queue;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by Mohammad on 6/18/2016.
 */
public class MyPriorityQueue {
    public static void main(String args[]) {
        // create priority queue
        PriorityQueue< Integer > prq = new PriorityQueue < Integer > ();

        // insert values in the queue
        prq.add(6);
        prq.add(9);
        prq.add(5);
        prq.add(64);
        prq.add(6);

        System.out.println ( "Priority queue values are: "+ prq);
        System.out.println ( "Priority queue peak is: "+ prq.peek());

        // create iterator from the queue
        Iterator it = prq.iterator();

        System.out.println ( "Priority queue values are: ");

        while (it.hasNext()){
            System.out.println ( "Value: "+ it.next());
        }

        while (!prq.isEmpty()){
            System.out.println(prq.poll());
        }


        // create comparator
        Comparator comp = prq.comparator();

        System.out.println ( "Comparator value is: "+ comp);

        PriorityQueue<Integer> queue = new PriorityQueue<>(10, Collections.reverseOrder());
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(12);
        queue.offer(75);
        queue.offer(116);

        Integer val = null;
        while( (val = queue.poll()) != null) {
            System.out.println(val);
        }

    }
}
