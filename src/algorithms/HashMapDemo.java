package algorithms; /**
 * Created by Mohammad on 5/15/2016.
 */

import java.util.*;

public class HashMapDemo {

    public static void main(String args[]) {

        // Create a hash map
        HashMap hm = new HashMap();

        // Put elements to the map
        hm.put("Mohammad", new Double(3434.34));
        hm.put("Mahnaz", new Double(123.22));
        hm.put("Ali", new Double(1378.00));

        // Get a set of the entries
        Set set = hm.entrySet();

        // Get an iterator
        Iterator i = set.iterator();

        // Display elements
        while(i.hasNext()) {
            Map.Entry e = (Map.Entry)i.next();
            System.out.print("Key: "+e.getKey() + ": ");
            System.out.println(" Value: "+e.getValue());
        }
        System.out.println();

        // Deposit 1000 into Zara's account
        double current = ((Double)hm.get("Mohammad")).doubleValue();
        hm.put("Mohammad", new Double(current + 1000));
        System.out.println("Mohammad's new balance: " +
                hm.get("Mohammad"));
    }

}
