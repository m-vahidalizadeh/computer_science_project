package algorithms.palindrome;

/**
 * 214. Shortest Palindrome
 * You are given a string s. You can convert s to a palindrome by adding characters in front of it.
 *
 * Return the shortest palindrome you can find by performing this transformation.
 *
 * Example 1:
 *
 * Input: s = "aacecaaa"
 * Output: "aaacecaaa"
 * Example 2:
 *
 * Input: s = "abcd"
 * Output: "dcbabcd"
 *
 * Constraints:
 *
 * 0 <= s.length <= 5 * 104
 * s consists of lowercase English letters only.
 */
public class ShortestPalindrome {

    public String shortestPalindrome(String s) {
        int[] lps = getLPS(s + "#" + (new StringBuilder(s)).reverse());
        return (new StringBuilder(s.substring(lps[lps.length - 1]))).reverse() + s;
    }

    private int[] getLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int index = 0;
        for (int i = 1; i < pattern.length(); ) {
            if (pattern.charAt(index) == pattern.charAt(i)) lps[i++] = ++index;
            else if (index > 0) index = lps[index - 1];
            else i++; // index==0
        }
        return lps;
    }

}
