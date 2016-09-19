package data_structures;

import java.util.PriorityQueue;

/**
 * Created by Mohammad on 9/19/2016.
 */
public class MaxHeapWithPriorityQueue {

    public static void main(String args[]) {
        // create priority queue
        PriorityQueue<Integer> prq = new PriorityQueue<>((x,y) -> y-x);

        // insert values in the queue
        prq.add(6);
        prq.add(9);
        prq.add(5);
        prq.add(64);
        prq.add(6);

        //print values
        while (!prq.isEmpty()) {
            System.out.print(prq.poll()+" ");
        }
    }

}
