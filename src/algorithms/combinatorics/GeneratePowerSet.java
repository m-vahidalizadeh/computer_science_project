package algorithms.combinatorics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class GeneratePowerSet {

    public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    public static void main(String args[]) {
        Set<Integer> mySet = new HashSet<Integer>();
        mySet.add(1);
        mySet.add(2);
        mySet.add(3);
        mySet.add(4);
        for (Set<Integer> s : powerSet(mySet)) {
            System.out.println(s);
        }
    }

}
