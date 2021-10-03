package algorithms.palindrome;

import java.util.HashMap;
import java.util.Map;

/**
 * 409. Longest Palindrome
 * Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.
 *
 * Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
 *
 * Example 1:
 *
 * Input: s = "abccccdd"
 * Output: 7
 * Explanation:
 * One longest palindrome that can be built is "dccaccd", whose length is 7.
 * Example 2:
 *
 * Input: s = "a"
 * Output: 1
 * Example 3:
 *
 * Input: s = "bb"
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= s.length <= 2000
 * s consists of lowercase and/or uppercase English letters only.
 */
public class LongestPalindromeLengthStr {

    public int longestPalindrome(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        int count = 0;
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            int duplicates = entry.getValue() / 2;
            count += duplicates * 2;
            int newFreq = entry.getValue() - duplicates * 2;
            entry.setValue(newFreq);
        }
        boolean anyLeft = false;
        for (int freq : freqMap.values()) {
            if (freq > 0) {
                anyLeft = true;
                break;
            }
        }
        return count + (anyLeft ? 1 : 0);
    }

}
