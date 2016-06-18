package algorithms;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Mohammad on 5/15/2016.
 */
public class PriorityQ {

    public static void main(String[] args)
    {
        Comparator<Node> comparator = new MyComparator();
        PriorityQueue<Node> queue =
                new PriorityQueue<Node>(10, comparator);
        queue.add(new Node(2,"ABC"));
        queue.add(new Node(1,"PRW"));
        queue.add(new Node(2,"DEF"));
        queue.add(new Node(3,"PRE"));
        queue.add(new Node(4,"DKF"));
        while (queue.size() != 0)
        {
            System.out.print(queue.remove().value);
            if(queue.size() != 0){
                System.out.print("|");
            }
        }
    }

    public static class Node{
        Integer priority;
        String value;

        Node(Integer priority, String value){
            this.value= value;
            this.priority= priority;
        }
    }

    public static class MyComparator implements Comparator<Node>
    {
        @Override
        public int compare(Node x, Node y)
        {
            // Assume neither string is null. Real code should
            // probably be more robust
            // You could also just return x.length() - y.length(),
            // which would be more efficient.
            if (x.priority < y.priority)
            {
                return -1;
            }
            if (x.priority > y.priority)
            {
                return 1;
            }
            return 0;
        }
    }

}
