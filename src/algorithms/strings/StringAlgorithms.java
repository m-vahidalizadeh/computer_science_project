package algorithms.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A utility class providing implementations of various string algorithms,
 * including pattern searching (KMP, Boyer-Moore, Rabin-Karp),
 * string permutation, and palindrome checking.
 * All methods in this class are static.
 */
public class StringAlgorithms {

    // Private constructor to prevent instantiation of this utility class.
    private StringAlgorithms() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    // --- Knuth-Morris-Pratt (KMP) Algorithm ---

    /**
     * Computes the Longest Proper Prefix Suffix (LPS) array for the KMP algorithm.
     * A proper prefix is a prefix that is not the entire string.
     * A proper suffix is a suffix that is not the entire string.
     * lps[i] will store the length of the longest proper prefix of pattern[0...i]
     * which is also a suffix of pattern[0...i].
     *
     * @param pattern The pattern string.
     * @return The LPS array.
     * @throws IllegalArgumentException if the pattern is null.
     */
    private static int[] computeLPSArray(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null for LPS computation.");
        }
        int m = pattern.length();
        int[] lps = new int[m];
        if (m == 0) {
            return lps;
        }

        int length = 0;
        lps[0] = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * Searches for all occurrences of a pattern within a text using the KMP algorithm.
     *
     * @param text    The text string to search within. Let n be its length.
     * @param pattern The pattern string to search for. Let m be its length.
     * @return A list of starting indices where the pattern is found in the text.
     *         Returns an empty list if the pattern is not found, or if text/pattern is null or empty.
     * - Time complexity: $$O(n + m)$$
     * - Space complexity: $$O(m)$$ (auxiliary space for the LPS array)
     */
    public static List<Integer> kmpSearch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        if (text == null || pattern == null || text.isEmpty() || pattern.isEmpty() || pattern.length() > text.length()) {
            return occurrences;
        }

        int n = text.length();
        int m = pattern.length();
        int[] lps = computeLPSArray(pattern);

        int i = 0;
        int j = 0;
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == m) {
                occurrences.add(i - j);
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return occurrences;
    }

    // --- Boyer-Moore-Horspool Algorithm ---

    private static final int ASCII_SIZE = 256; // Represents the alphabet size

    /**
     * Precomputes the bad character heuristic table for the Boyer-Moore-Horspool algorithm.
     * The table stores the last occurrence of each character in the pattern.
     * If a character is not in the pattern, its entry can be -1.
     *
     * @param pattern The pattern string.
     * @param badCharTable The array to store the bad character shifts. Its size should be ASCII_SIZE.
     */
    private static void precomputeBadCharTable(String pattern, int[] badCharTable) {
        Arrays.fill(badCharTable, -1);
        for (int i = 0; i < pattern.length(); i++) {
            badCharTable[pattern.charAt(i)] = i;
        }
    }

    /**
     * Searches for all occurrences of a pattern within a text using the Boyer-Moore-Horspool algorithm.
     * This variant primarily uses the bad character heuristic.
     *
     * @param text    The text string to search within. Let n be its length.
     * @param pattern The pattern string to search for. Let m be its length.
     * @return A list of starting indices where the pattern is found in the text.
     *         Returns an empty list if the pattern is not found, or if text/pattern is null or empty.
     * - Time complexity: $$O(n*m)$$ in the worst case, though often performs better, potentially $$O(n/m)$$ to $$O(n)$$ on average.
     * - Space complexity: $$O(\text{alphabet\_size})$$ (for the bad character table, which is $$O(1)$$ if alphabet size is considered constant like ASCII_SIZE).
     */
    public static List<Integer> boyerMooreHorspoolSearch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        if (text == null || pattern == null || text.isEmpty() || pattern.isEmpty() || pattern.length() > text.length()) {
            return occurrences;
        }

        int n = text.length();
        int m = pattern.length();

        int[] badCharTable = new int[ASCII_SIZE];
        precomputeBadCharTable(pattern, badCharTable);

        int shift = 0;
        while (shift <= (n - m)) {
            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                occurrences.add(shift);
                if (shift + m < n) {
                    char charAfterMatch = text.charAt(shift + m);
                    shift += m - badCharTable[charAfterMatch];
                } else {
                    shift += 1;
                }
            } else {
                char badCharInText = text.charAt(shift + j);
                int badCharShift = j - badCharTable[badCharInText];
                shift += Math.max(1, badCharShift);
            }
        }
        return occurrences;
    }

    // --- Rabin-Karp Algorithm ---

    private static final int PRIME_BASE = 101;
    private static final int MODULUS = 1_000_000_007;

    /**
     * Searches for all occurrences of a pattern within a text using the Rabin-Karp algorithm.
     *
     * @param text    The text string to search within. Let n be its length.
     * @param pattern The pattern string to search for. Let m be its length.
     * @return A list of starting indices where the pattern is found in the text.
     *         Returns an empty list if the pattern is not found, or if text/pattern is null or empty.
     * - Time complexity: $$O(n*m)$$ in the worst case (due to hash collisions requiring character-by-character comparison), $$O(n+m)$$ on average.
     * - Space complexity: $$O(1)$$ (auxiliary space).
     */
    public static List<Integer> rabinKarpSearch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        if (text == null || pattern == null || text.isEmpty() || pattern.isEmpty() || pattern.length() > text.length()) {
            return occurrences;
        }

        int n = text.length();
        int m = pattern.length();
        long patternHash = 0;
        long textWindowHash = 0;
        long h = 1;

        for (int i = 0; i < m - 1; i++) {
            h = (h * PRIME_BASE) % MODULUS;
        }

        for (int i = 0; i < m; i++) {
            patternHash = (PRIME_BASE * patternHash + pattern.charAt(i)) % MODULUS;
            textWindowHash = (PRIME_BASE * textWindowHash + text.charAt(i)) % MODULUS;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textWindowHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    occurrences.add(i);
                }
            }

            if (i < n - m) {
                textWindowHash = (PRIME_BASE * (textWindowHash - text.charAt(i) * h) + text.charAt(i + m)) % MODULUS;
                if (textWindowHash < 0) {
                    textWindowHash = (textWindowHash + MODULUS);
                }
            }
        }
        return occurrences;
    }

    // --- String Permutations ---

    /**
     * Finds all unique permutations of a given string using a recursive approach.
     * If the string contains duplicate characters, the resulting list will contain unique permutations.
     *
     * @param str The input string. Let n be its length.
     * @return A list of all unique permutations of the string.
     *         Returns an empty list if the input string is null.
     *         Returns a list with an empty string if the input is an empty string.
     * - Time complexity: $$O(n * P(n))$$, where P(n) is the number of unique permutations. If all characters are unique, P(n) = n!, so it's $$O(n * n!)$$.
     * - Space complexity: $$O(n)$$ (auxiliary space for the recursion stack, character array, and set used at each level).
     */
    public static List<String> getStringPermutations(String str) {
        if (str == null) {
            return new ArrayList<>();
        }
        if (str.isEmpty()) {
            List<String> result = new ArrayList<>();
            result.add("");
            return result;
        }
        // The permuteRecursive method now returns the list directly.
        return permuteRecursive(str.toCharArray(), 0);
    }

    /**
     * Recursive helper function to generate permutations.
     * This version handles duplicate characters in the input string to produce unique permutations.
     * This method now returns the list of permutations instead of modifying a passed-in list.
     *
     * @param arr    The character array representing the string to permute.
     * @param k      The starting index for the current permutation step.
     * @return A list containing all unique permutations generated from this state.
     */
    private static List<String> permuteRecursive(char[] arr, int k) {
        List<String> permutations = new ArrayList<>();
        if (k == arr.length - 1) {
            permutations.add(new String(arr));
            return permutations;
        }

        Set<Character> usedInThisLevel = new HashSet<>();
        for (int i = k; i < arr.length; i++) {
            if (usedInThisLevel.add(arr[i])) { // Check if character was already used at this position k
                swap(arr, k, i);
                // Add all permutations generated from the recursive call
                permutations.addAll(permuteRecursive(arr, k + 1));
                swap(arr, k, i); // Backtrack: revert the swap
            }
        }
        return permutations;
    }

    /**
     * Finds all unique permutations of a given string iteratively using the
     * "next lexicographical permutation" algorithm.
     * If the string contains duplicate characters, the resulting list will contain unique permutations
     * generated in lexicographical order.
     *
     * @param str The input string. Let n be its length.
     * @return A list of all unique permutations of the string.
     *         Returns an empty list if the input string is null.
     *         Returns a list with an empty string if the input is an empty string.
     * - Time complexity: $$O(n \log n + n * P(n))$$, where P(n) is the number of unique permutations. Sorting takes $$O(n \log n)$$. Generating P(n) permutations, each takes $$O(n)$$ work. If all characters are unique, P(n) = n!, so it's effectively $$O(n * n!)$$.
     * - Space complexity: $$O(n)$$ (auxiliary space for the character array).
     */
    public static List<String> getStringPermutationsIterative(String str) {
        if (str == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        if (str.isEmpty()) {
            result.add("");
            return result;
        }

        char[] arr = str.toCharArray();
        // Sort the array to start with the lexicographically smallest permutation.
        // This is essential for the "next permutation" algorithm to generate
        // all unique permutations correctly and in order.
        Arrays.sort(arr);

        // Add the first (sorted) permutation
        result.add(new String(arr));

        // Iteratively generate the next lexicographical permutation
        while (true) {
            // 1. Find the largest index k such that arr[k] < arr[k + 1].
            // If no such index exists, the permutation is the last permutation.
            int k = -1;
            for (int i = arr.length - 2; i >= 0; i--) {
                if (arr[i] < arr[i + 1]) {
                    k = i;
                    break;
                }
            }

            // If we are at the last permutation (lexicographically largest), exit loop.
            if (k == -1) {
                break;
            }

            // 2. Find the largest index l greater than k such that arr[k] < arr[l].
            // (arr[l] is the smallest character to the right of arr[k] that is greater than arr[k])
            int l = -1;
            for (int i = arr.length - 1; i > k; i--) {
                if (arr[k] < arr[i]) {
                    l = i;
                    break;
                }
            }

            // 3. Swap arr[k] with arr[l].
            swap(arr, k, l);

            // 4. Reverse the sub-array starting from arr[k + 1] to the end of the array.
            reverse(arr, k + 1, arr.length - 1);

            // Add the newly generated permutation to the result list
            result.add(new String(arr));
        }

        return result;
    }


    /**
     * Swaps two characters in a character array.
     *
     * @param arr The character array.
     * @param i   The index of the first character.
     * @param j   The index of the second character.
     */
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Reverses a portion of a character array in place.
     * This is a helper for the iterative permutation algorithm.
     *
     * @param arr   The character array.
     * @param start The starting index of the sub-array to reverse (inclusive).
     * @param end   The ending index of the sub-array to reverse (inclusive).
     */
    private static void reverse(char[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }

    // --- Palindrome Check ---

    /**
     * Checks if a given string is a palindrome.
     * A palindrome is a string that reads the same forwards and backward.
     * Considers case sensitivity. Ignores spaces or special characters unless they are part of the comparison.
     *
     * @param str The string to check. Let n be its length.
     * @return true if the string is a palindrome, false otherwise.
     *         Returns true for null or empty strings as per common convention (or can be defined differently).
     * - Time complexity: $$O(n)$$
     * - Space complexity: $$O(1)$$ (auxiliary space).
     */
    public static boolean isPalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        int left = 0;
        int right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * Main method for example usage and testing of the string algorithms.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("--- KMP Search ---");
        String textKMP = "ABABDABACDABABCABAB";
        String patternKMP = "ABABCABAB";
        System.out.println("Text: " + textKMP);
        System.out.println("Pattern: " + patternKMP);
        System.out.println("KMP Occurrences: " + kmpSearch(textKMP, patternKMP)); // Expected: [10]
        System.out.println("KMP Occurrences (AABAACAADAABAABA): " + kmpSearch("AABAACAADAABAABA", "AABA")); // Expected: [0, 9, 12]

        System.out.println("\n--- Boyer-Moore-Horspool Search ---");
        String textBMH = "TRUSTHARDTOOTHBRUSHES";
        String patternBMH = "TOOTH";
        System.out.println("Text: " + textBMH);
        System.out.println("Pattern: " + patternBMH);
        System.out.println("Boyer-Moore-Horspool Occurrences: " + boyerMooreHorspoolSearch(textBMH, patternBMH)); // Expected: [9]
        System.out.println("Boyer-Moore-Horspool Occurrences (ABAAABCD): " + boyerMooreHorspoolSearch("ABAAABCD", "ABC")); // Expected: [4]

        System.out.println("\n--- Rabin-Karp Search ---");
        String textRK = "AABAACAADAABAABA";
        String patternRK = "AABA";
        System.out.println("Text: " + textRK);
        System.out.println("Pattern: " + patternRK);
        System.out.println("Rabin-Karp Occurrences: " + rabinKarpSearch(textRK, patternRK)); // Expected: [0, 9, 12]
        String textRK2 = "GEEKS FOR GEEKS";
        String patternRK2 = "GEEK";
        System.out.println("Text: " + textRK2);
        System.out.println("Pattern: " + patternRK2);
        System.out.println("Rabin-Karp Occurrences: " + rabinKarpSearch(textRK2, patternRK2)); // Expected: [0, 10]


        System.out.println("\n--- String Permutations (Recursive) ---");
        String permStr1 = "ABC";
        System.out.println("Permutations of \"" + permStr1 + "\": " + getStringPermutations(permStr1));
        // Expected: [ABC, ACB, BAC, BCA, CBA, CAB] (order may vary for recursive, but with this change it will be more consistent)
        String permStr2 = "AAB";
        System.out.println("Permutations of \"" + permStr2 + "\": " + getStringPermutations(permStr2));
        // Expected: [AAB, ABA, BAA] (unique)

        System.out.println("\n--- String Permutations (Iterative - Next Lexicographical) ---");
        System.out.println("Permutations of \"" + permStr1 + "\" (Iterative): " + getStringPermutationsIterative(permStr1));
        // Expected: [ABC, ACB, BAC, BCA, CAB, CBA] (order will be lexicographical)
        System.out.println("Permutations of \"" + permStr2 + "\" (Iterative): " + getStringPermutationsIterative(permStr2));
        // Expected: [AAB, ABA, BAA] (unique, lexicographical order)
        String permStr3 = "A";
        System.out.println("Permutations of \"" + permStr3 + "\" (Iterative): " + getStringPermutationsIterative(permStr3));
        // Expected: [A]
        String permStr4 = "";
        System.out.println("Permutations of \"" + permStr4 + "\" (Iterative): " + getStringPermutationsIterative(permStr4));
        // Expected: [""]
        String permStr5 = "CBA"; // Test with initially unsorted string
        System.out.println("Permutations of \"" + permStr5 + "\" (Iterative): " + getStringPermutationsIterative(permStr5));
        // Expected: [ABC, ACB, BAC, BCA, CAB, CBA] (order will be lexicographical)


        System.out.println("\n--- Palindrome Check ---");
        String palin1 = "madam";
        System.out.println("\"" + palin1 + "\" is palindrome: " + isPalindrome(palin1)); // Expected: true
        String palin2 = "racecar";
        System.out.println("\"" + palin2 + "\" is palindrome: " + isPalindrome(palin2)); // Expected: true
        String palin3 = "hello";
        System.out.println("\"" + palin3 + "\" is palindrome: " + isPalindrome(palin3)); // Expected: false
        String palin4 = "A";
        System.out.println("\"" + palin4 + "\" is palindrome: " + isPalindrome(palin4)); // Expected: true
        String palin5 = "";
        System.out.println("\"" + palin5 + "\" is palindrome: " + isPalindrome(palin5)); // Expected: true
        String palin6 = null;
        System.out.println("null is palindrome: " + isPalindrome(palin6)); // Expected: true
    }
}