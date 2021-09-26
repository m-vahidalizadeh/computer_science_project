package algorithms.combinatorics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindPermutationsOfString {

    public List<String> findPermutations(String str) {
        if (str.isBlank()) return Collections.emptyList();
        List<String> res = new ArrayList<>();
        res.add("" + str.charAt(0));
        for (int i = 1; i < str.length(); i++) {
            int size = res.size();
            for (int j = size - 1; j >= 0; j--) {
                String perm = res.remove(j);
                for (int k = 0; k <= perm.length(); k++) {
                    res.add(perm.substring(0, k) + str.charAt(i) + perm.substring(k));
                }
            }
        }
        return res;
    }

}
