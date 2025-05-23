package algorithms.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StringAlgorithmsTest {

    // --- Tests for kmpSearch ---
    @Test
    @DisplayName("KMP: Pattern found multiple times")
    void kmpSearch_patternFoundMultipleTimes() {
        List<Integer> expected = Arrays.asList(0, 9, 12);
        List<Integer> actual = StringAlgorithms.kmpSearch("AABAACAADAABAABA", "AABA");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Pattern found once")
    void kmpSearch_patternFoundOnce() {
        List<Integer> expected = Collections.singletonList(10);
        List<Integer> actual = StringAlgorithms.kmpSearch("ABABDABACDABABCABAB", "ABABCABAB");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Pattern not found")
    void kmpSearch_patternNotFound() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearch("ABCDEFG", "XYZ");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Empty pattern")
    void kmpSearch_emptyPattern() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearch("ABCDEFG", "");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Empty text")
    void kmpSearch_emptyText() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearch("", "ABC");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Pattern longer than text")
    void kmpSearch_patternLongerThanText() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearch("ABC", "ABCDE");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Null text")
    void kmpSearch_nullText() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearch(null, "ABC");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Null pattern")
    void kmpSearch_nullPattern() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearch("ABCDEFG", null);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP: Text and pattern are the same")
    void kmpSearch_textAndPatternSame() {
        List<Integer> expected = Collections.singletonList(0);
        List<Integer> actual = StringAlgorithms.kmpSearch("ABCDE", "ABCDE");
        assertEquals(expected, actual);
    }

    // --- Tests for kmpSearchWithConcatenatedLPS ---
    @Test
    @DisplayName("KMP Concat: Pattern found multiple times")
    void kmpSearchWithConcatenatedLPS_patternFoundMultipleTimes() {
        List<Integer> expected = Arrays.asList(0, 9, 12);
        List<Integer> actual = StringAlgorithms.kmpSearchWithConcatenatedLPS("AABAACAADAABAABA", "AABA");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP Concat: Pattern found once")
    void kmpSearchWithConcatenatedLPS_patternFoundOnce() {
        List<Integer> expected = Collections.singletonList(10);
        List<Integer> actual = StringAlgorithms.kmpSearchWithConcatenatedLPS("ABABDABACDABABCABAB", "ABABCABAB");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP Concat: Overlapping patterns")
    void kmpSearchWithConcatenatedLPS_overlappingPatterns() {
        List<Integer> expected = Arrays.asList(0, 1, 2, 3);
        List<Integer> actual = StringAlgorithms.kmpSearchWithConcatenatedLPS("AAAAA", "AA");
        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("KMP Concat: Pattern not found")
    void kmpSearchWithConcatenatedLPS_patternNotFound() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearchWithConcatenatedLPS("ABCDEFG", "XYZ");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP Concat: Empty pattern")
    void kmpSearchWithConcatenatedLPS_emptyPattern() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearchWithConcatenatedLPS("ABCDEFG", "");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP Concat: Empty text")
    void kmpSearchWithConcatenatedLPS_emptyText() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearchWithConcatenatedLPS("", "ABC");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("KMP Concat: Pattern longer than text")
    void kmpSearchWithConcatenatedLPS_patternLongerThanText() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.kmpSearchWithConcatenatedLPS("ABC", "ABCDE");
        assertEquals(expected, actual);
    }


    // --- Tests for boyerMooreHorspoolSearch ---
    @Test
    @DisplayName("BMH: Pattern found")
    void boyerMooreHorspoolSearch_patternFound() {
        List<Integer> expected = Collections.singletonList(9);
        List<Integer> actual = StringAlgorithms.boyerMooreHorspoolSearch("TRUSTHARDTOOTHBRUSHES", "TOOTH");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("BMH: Pattern found at beginning")
    void boyerMooreHorspoolSearch_patternFoundAtBeginning() {
        List<Integer> expected = Collections.singletonList(0);
        List<Integer> actual = StringAlgorithms.boyerMooreHorspoolSearch("ABAAABCD", "ABAA");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("BMH: Pattern found at end")
    void boyerMooreHorspoolSearch_patternFoundAtEnd() {
        List<Integer> expected = Collections.singletonList(4);
        List<Integer> actual = StringAlgorithms.boyerMooreHorspoolSearch("ABAAABCD", "ABCD");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("BMH: Pattern not found")
    void boyerMooreHorspoolSearch_patternNotFound() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.boyerMooreHorspoolSearch("ABCDEFG", "HIJ");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("BMH: Empty pattern")
    void boyerMooreHorspoolSearch_emptyPattern() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.boyerMooreHorspoolSearch("ABCDEFG", "");
        assertEquals(expected, actual);
    }


    // --- Tests for rabinKarpSearch ---
    @Test
    @DisplayName("Rabin-Karp: Pattern found multiple times")
    void rabinKarpSearch_patternFoundMultipleTimes() {
        List<Integer> expected = Arrays.asList(0, 9, 12);
        List<Integer> actual = StringAlgorithms.rabinKarpSearch("AABAACAADAABAABA", "AABA");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Rabin-Karp: Pattern not found")
    void rabinKarpSearch_patternNotFound() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.rabinKarpSearch("ABCDEFG", "XYZ");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Rabin-Karp: Empty pattern")
    void rabinKarpSearch_emptyPattern() {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = StringAlgorithms.rabinKarpSearch("ABCDEFG", "");
        assertEquals(expected, actual);
    }

    // --- Tests for getStringPermutations (Recursive) ---
    @Test
    @DisplayName("Permutations Recursive: Unique characters")
    void getStringPermutations_uniqueChars() {
        List<String> expected = Arrays.asList("ABC", "ACB", "BAC", "BCA", "CAB", "CBA");
        List<String> actual = StringAlgorithms.getStringPermutations("ABC");
        // Order might vary, so compare content using sets
        assertEquals(new HashSet<>(expected), new HashSet<>(actual));
        assertEquals(expected.size(), actual.size()); // Ensure no duplicates if not expected
    }

    @Test
    @DisplayName("Permutations Recursive: Duplicate characters")
    void getStringPermutations_duplicateChars() {
        List<String> expected = Arrays.asList("AAB", "ABA", "BAA");
        List<String> actual = StringAlgorithms.getStringPermutations("AAB");
        assertEquals(new HashSet<>(expected), new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @DisplayName("Permutations Recursive: Single character")
    void getStringPermutations_singleChar() {
        List<String> expected = Collections.singletonList("A");
        List<String> actual = StringAlgorithms.getStringPermutations("A");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Permutations Recursive: Empty string")
    void getStringPermutations_emptyString() {
        List<String> expected = Collections.singletonList("");
        List<String> actual = StringAlgorithms.getStringPermutations("");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Permutations Recursive: Null string")
    void getStringPermutations_nullString() {
        List<String> expected = new ArrayList<>(); // Or Collections.emptyList()
        List<String> actual = StringAlgorithms.getStringPermutations(null);
        assertEquals(expected, actual);
    }

    // --- Tests for getStringPermutationsIterative ---
    @Test
    @DisplayName("Permutations Iterative: Unique characters, lexicographical order")
    void getStringPermutationsIterative_uniqueChars() {
        List<String> expected = Arrays.asList("ABC", "ACB", "BAC", "BCA", "CAB", "CBA");
        List<String> actual = StringAlgorithms.getStringPermutationsIterative("ABC");
        assertEquals(expected, actual); // Order must match for iterative
    }

    @Test
    @DisplayName("Permutations Iterative: Unique characters, initially unsorted")
    void getStringPermutationsIterative_uniqueCharsUnsortedInput() {
        List<String> expected = Arrays.asList("ABC", "ACB", "BAC", "BCA", "CAB", "CBA");
        List<String> actual = StringAlgorithms.getStringPermutationsIterative("CBA");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Permutations Iterative: Duplicate characters, lexicographical order")
    void getStringPermutationsIterative_duplicateChars() {
        List<String> expected = Arrays.asList("AAB", "ABA", "BAA");
        List<String> actual = StringAlgorithms.getStringPermutationsIterative("AAB");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Permutations Iterative: Single character")
    void getStringPermutationsIterative_singleChar() {
        List<String> expected = Collections.singletonList("A");
        List<String> actual = StringAlgorithms.getStringPermutationsIterative("A");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Permutations Iterative: Empty string")
    void getStringPermutationsIterative_emptyString() {
        List<String> expected = Collections.singletonList("");
        List<String> actual = StringAlgorithms.getStringPermutationsIterative("");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Permutations Iterative: Null string")
    void getStringPermutationsIterative_nullString() {
        List<String> expected = new ArrayList<>();
        List<String> actual = StringAlgorithms.getStringPermutationsIterative(null);
        assertEquals(expected, actual);
    }


    // --- Tests for isPalindrome ---
    @Test
    @DisplayName("Palindrome: True for odd length")
    void isPalindrome_trueOddLength() {
        assertTrue(StringAlgorithms.isPalindrome("madam"));
    }

    @Test
    @DisplayName("Palindrome: True for even length")
    void isPalindrome_trueEvenLength() {
        assertTrue(StringAlgorithms.isPalindrome("racecar")); // racecar is odd, let's use "anna"
        assertTrue(StringAlgorithms.isPalindrome("anna"));
    }

    @Test
    @DisplayName("Palindrome: False")
    void isPalindrome_false() {
        assertFalse(StringAlgorithms.isPalindrome("hello"));
    }

    @Test
    @DisplayName("Palindrome: Single character")
    void isPalindrome_singleChar() {
        assertTrue(StringAlgorithms.isPalindrome("A"));
    }

    @Test
    @DisplayName("Palindrome: Empty string")
    void isPalindrome_emptyString() {
        assertTrue(StringAlgorithms.isPalindrome(""));
    }

    @Test
    @DisplayName("Palindrome: Null string")
    void isPalindrome_nullString() {
        assertTrue(StringAlgorithms.isPalindrome(null));
    }

    @Test
    @DisplayName("Palindrome: Case sensitive false")
    void isPalindrome_caseSensitiveFalse() {
        assertFalse(StringAlgorithms.isPalindrome("Madam"));
    }
}
