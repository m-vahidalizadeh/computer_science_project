package algorithms.combinatorics;

import java.util.*;

public class AllPermutationsString {

    public List<String> getAllPermutationsOfStr(String s) {
        if (s.isBlank()) return Collections.emptyList();
        LinkedList<String> permutations = new LinkedList<>();
        permutations.addLast(String.valueOf(s.charAt(0)));
        for (int sI = 1; sI < s.length(); sI++) {
            char charToInsert = s.charAt(sI);
            int size = permutations.size();
            for (int pI = size - 1; pI >= 0; pI--) {
                String perm = permutations.removeLast();
                for (int insertionI = 0; insertionI <= perm.length(); insertionI++) {
                    permutations.addFirst(perm.substring(0, insertionI) + charToInsert + perm.substring(insertionI));
                }
            }
        }
        return permutations;
    }

    public static void main(String[] args) {
        AllPermutationsString a = new AllPermutationsString();
        System.out.println(a.getAllPermutationsOfStr("abb"));
    }

}
