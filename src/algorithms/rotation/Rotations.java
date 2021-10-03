package algorithms.rotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rotations {

    public List<List<String>> getGroups(String[] words) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String word : words) {
            String groupKey = null;
            for (String key : groups.keySet()) {
                if (key.contains(word)) {
                    groupKey = key;
                    break;
                }
            }
            if (groupKey == null) { // Group does not exist
                List<String> memberList = new ArrayList<>();
                memberList.add(word);
                groupKey = word + word;
                groups.put(groupKey, memberList);
            } else groups.get(groupKey).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    public boolean areSameRotations(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        String temp = s1 + s1;
        return temp.contains(s2);
    }

    public static void main(String[] args) {
        Rotations rotations = new Rotations();
        System.out.println(rotations.getGroups(new String[]{"xyz", "ab", "ba", "c", "yzx"}));
        System.out.println(rotations.areSameRotations("abcde", "cdeab"));
        System.out.println(rotations.areSameRotations("abcde", "abced"));
    }

}
