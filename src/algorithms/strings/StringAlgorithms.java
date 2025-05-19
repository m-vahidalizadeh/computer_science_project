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
            return lps; // Empty pattern has an empty LPS array or an LPS array of [0] if m=0
        }

        int length = 0; // Length of the previous longest prefix suffix
        lps[0] = 0;     // lps[0] is always 0
        int i = 1;

        // Loop to compute lps[i] for i from 1 to m-1
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else { // (pattern[i] != pattern[length])
                if (length != 0) {
                    // This is tricky. Consider the example.
                    // AAACAAAA and i = 7. The idea is similar
                    // to search step in KMP
                    length = lps[length - 1];
                    // Also, note that we do not increment i here
                } else { // if (length == 0)
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
     * @param text    The text string to search within.
     * @param pattern The pattern string to search for.
     * @return A list of starting indices where the pattern is found in the text.
     *         Returns an empty list if the pattern is not found, or if text/pattern is null or empty.
     */
    public static List<Integer> kmpSearch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        if (text == null || pattern == null || text.isEmpty() || pattern.isEmpty() || pattern.length() > text.length()) {
            return occurrences;
        }

        int n = text.length();
        int m = pattern.length();
        int[] lps = computeLPSArray(pattern);

        int i = 0; // Index for text[]
        int j = 0; // Index for pattern[]
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == m) {
                occurrences.add(i - j); // Pattern found at index (i - j)
                j = lps[j - 1];       // Continue searching for other occurrences
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                // Mismatch after j matches
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
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

    private static final int ASCII_SIZE = 256; // Assuming ASCII character set

    /**
     * Precomputes the bad character heuristic table for the Boyer-Moore-Horspool algorithm.
     * The table stores the last occurrence of each character in the pattern.
     * If a character is not in the pattern, its entry can be -1.
     *
     * @param pattern The pattern string.
     * @param badCharTable The array to store the bad character shifts. Its size should be ASCII_SIZE.
     */
    private static void precomputeBadCharTable(String pattern, int[] badCharTable) {
        Arrays.fill(badCharTable, -1); // Initialize all occurrences as -1 (not found)
        for (int i = 0; i < pattern.length(); i++) {
            badCharTable[pattern.charAt(i)] = i; // Store the last index of occurrence
        }
    }

    /**
     * Searches for all occurrences of a pattern within a text using the Boyer-Moore-Horspool algorithm.
     * This variant primarily uses the bad character heuristic.
     *
     * @param text    The text string to search within.
     * @param pattern The pattern string to search for.
     * @return A list of starting indices where the pattern is found in the text.
     *         Returns an empty list if the pattern is not found, or if text/pattern is null or empty.
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

        int shift = 0; // Current alignment of pattern with text
        while (shift <= (n - m)) {
            int j = m - 1; // Index for pattern, starting from the end

            // Keep reducing index j of pattern while characters of
            // pattern and text are matching at this shift s
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            // If the pattern is present at the current shift,
            // then index j will become -1 after the above loop
            if (j < 0) {
                occurrences.add(shift);

                // Shift the pattern so that the next character in text
                // aligns with the last occurrence of it in pattern.
                // The condition s+m < n is necessary for the case when
                // pattern occurs at the end of text
                // If pattern_len is 1, this will cause infinite loop if not handled.
                if (shift + m < n) {
                    // Get the character in the text immediately following the current match
                    char charAfterMatch = text.charAt(shift + m);
                    // Shift by m - (last occurrence of charAfterMatch in pattern)
                    // If charAfterMatch is not in pattern, badCharTable[charAfterMatch] is -1,
                    // so shift will be m - (-1) = m + 1.
                    // If charAfterMatch is the last char of pattern, shift is m - (m-1) = 1.
                    shift += m - badCharTable[charAfterMatch];
                } else {
                    shift += 1; // Reached end of text, or only one possible alignment left
                }
            } else {
                // Mismatch occurred at pattern[j] and text[shift+j]
                // Shift the pattern so that the bad character in text
                // aligns with the last occurrence of it in pattern.
                // The max function is used to make sure that we get a positive shift.
                // We may get a negative shift if the last occurrence
                // of bad character in pattern is on the right side of the current character.
                char badCharInText = text.charAt(shift + j);
                int badCharShift = j - badCharTable[badCharInText];
                shift += Math.max(1, badCharShift);
            }
        }
        return occurrences;
    }

    // --- Rabin-Karp Algorithm ---

    // A prime number for hashing
    private static final int PRIME_BASE = 101;
    // Modulus for hashing to prevent overflow and distribute hash values
    private static final int MODULUS = 1_000_000_007; // A large prime

    /**
     * Searches for all occurrences of a pattern within a text using the Rabin-Karp algorithm.
     *
     * @param text    The text string to search within.
     * @param pattern The pattern string to search for.
     * @return A list of starting indices where the pattern is found in the text.
     *         Returns an empty list if the pattern is not found, or if text/pattern is null or empty.
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
        long h = 1; // Represents PRIME_BASE^(m-1) % MODULUS

        // Calculate h = PRIME_BASE^(m-1) % MODULUS
        // This is used to remove the leading digit's contribution during rolling hash
        for (int i = 0; i < m - 1; i++) {
            h = (h * PRIME_BASE) % MODULUS;
        }

        // Calculate the hash value of the pattern and the first window of the text
        for (int i = 0; i < m; i++) {
            patternHash = (PRIME_BASE * patternHash + pattern.charAt(i)) % MODULUS;
            textWindowHash = (PRIME_BASE * textWindowHash + text.charAt(i)) % MODULUS;
        }

        // Slide the pattern over the text one by one
        for (int i = 0; i <= n - m; i++) {
            // Check if the hash values of the current window of text and pattern match
            if (patternHash == textWindowHash) {
                // If hashes match, verify characters one by one (to handle collisions)
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

            // Calculate hash value for the next window of text:
            // Remove leading digit, add trailing digit
            if (i < n - m) {
                // Remove leading character's contribution
                textWindowHash = (PRIME_BASE * (textWindowHash - text.charAt(i) * h) + text.charAt(i + m)) % MODULUS;

                // We might get a negative value of textWindowHash, convert it to positive
                if (textWindowHash < 0) {
                    textWindowHash = (textWindowHash + MODULUS);
                }
            }
        }
        return occurrences;
    }

    // --- String Permutations ---

    /**
     * Finds all unique permutations of a given string.
     * If the string contains duplicate characters, the resulting list will contain unique permutations.
     *
     * @param str The input string.
     * @return A list of all unique permutations of the string.
     *         Returns an empty list if the input string is null.
     *         Returns a list with an empty string if the input is an empty string.
     */
    public static List<String> getStringPermutations(String str) {
        if (str == null) {
            return new ArrayList<>(); // Or throw IllegalArgumentException
        }
        List<String> result = new ArrayList<>();
        if (str.isEmpty()) {
            result.add("");
            return result;
        }
        // Using a char array for efficient swapping
        permuteRecursive(str.toCharArray(), 0, result);
        // If we need to ensure uniqueness explicitly due to the recursive strategy,
        // we can pass a Set<String> to permuteRecursive and then convert to List,
        // or the permuteRecursive can handle duplicates.
        // The current permuteRecursive with the Set<Character> usedInThisLevel handles this.
        return result;
    }

    /**
     * Recursive helper function to generate permutations.
     * This version handles duplicate characters in the input string to produce unique permutations.
     *
     * @param arr    The character array representing the string to permute.
     * @param k      The starting index for the current permutation step.
     * @param result The list to store the generated unique permutations.
     */
    private static void permuteRecursive(char[] arr, int k, List<String> result) {
        if (k == arr.length -1) { // When k reaches the second to last element, the last permutation is fixed.
            // Or k == arr.length, then add new String(arr)
            result.add(new String(arr));
            return;
        }

        // This set ensures that for a given position 'k', each distinct character
        // from arr[k...end] is tried only once as arr[k].
        Set<Character> usedInThisLevel = new HashSet<>();
        for (int i = k; i < arr.length; i++) {
            if (usedInThisLevel.add(arr[i])) { // If arr[i] hasn't been placed at index k yet in this recursive call
                swap(arr, k, i);                // Place arr[i] at current index k
                permuteRecursive(arr, k + 1, result); // Permute the rest of the array
                swap(arr, k, i);                // Backtrack: restore original order for next iteration
            }
        }
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

    // --- Palindrome Check ---

    /**
     * Checks if a given string is a palindrome.
     * A palindrome is a string that reads the same forwards and backward.
     * Considers case sensitivity. Ignores spaces or special characters unless they are part of the comparison.
     *
     * @param str The string to check.
     * @return true if the string is a palindrome, false otherwise.
     *         Returns true for null or empty strings as per common convention (or can be defined differently).
     */
    public static boolean isPalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return true; // Or false, depending on definition for null/empty
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


        System.out.println("\n--- String Permutations ---");
        String permStr1 = "ABC";
        System.out.println("Permutations of \"" + permStr1 + "\": " + getStringPermutations(permStr1));
        // Expected: [ABC, ACB, BAC, BCA, CBA, CAB] (order may vary)
        String permStr2 = "AAB";
        System.out.println("Permutations of \"" + permStr2 + "\": " + getStringPermutations(permStr2));
        // Expected: [AAB, ABA, BAA] (unique, order may vary)
        String permStr3 = "A";
        System.out.println("Permutations of \"" + permStr3 + "\": " + getStringPermutations(permStr3));
        // Expected: [A]
        String permStr4 = "";
        System.out.println("Permutations of \"" + permStr4 + "\": " + getStringPermutations(permStr4));
        // Expected: [""]


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
