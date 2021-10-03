package algorithms.combinatorics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AllPermutationsOfIntArray {

    public List<List<Integer>> getAllPermutationsOfIntArr(int[] arr) {
        if (arr == null || arr.length == 0) return Collections.emptyList();
        LinkedList<List<Integer>> permutations = new LinkedList<>();
        List<Integer> first = new ArrayList<>();
        first.add(arr[0]);
        permutations.addFirst(first);
        for (int arrI = 1; arrI < arr.length; arrI++) {
            int elementToInsert = arr[arrI];
            int size = permutations.size();
            for (int pI = size - 1; pI >= 0; pI--) {
                List<Integer> perm = permutations.removeLast();
                for (int insertionI = 0; insertionI <= perm.size(); insertionI++) {
                    List<Integer> newPerm = new ArrayList<>();
                    newPerm.addAll(perm.subList(0, insertionI));
                    newPerm.add(elementToInsert);
                    newPerm.addAll(perm.subList(insertionI, perm.size()));
                    permutations.addFirst(newPerm);
                }
            }
        }
        return permutations;
    }

    public static void main(String[] args) {
        AllPermutationsOfIntArray a = new AllPermutationsOfIntArray();
        System.out.println(a.getAllPermutationsOfIntArr(new int[]{1, 2, 3}));
    }

}
