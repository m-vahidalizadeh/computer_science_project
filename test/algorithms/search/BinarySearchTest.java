package algorithms.search;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Assuming the BinarySearch class is in the 'algorithms.search' package
// If your test class is in a different package, ensure BinarySearch is accessible
// e.g., by importing algorithms.search.BinarySearch;
// For this example, we'll assume the test class can access BinarySearch methods directly.

class BinarySearchTest {

    //--- Test cases for binarySearchArraysFirstIndex ---

    @Test
    void testBinarySearchArraysFirstIndex_emptyArray() {
        assertEquals(-1, BinarySearch.binarySearchArraysFirstIndex(new int[]{}, 5)); // -(0)-1
    }

    @Test
    void testBinarySearchArraysFirstIndex_singleElement_targetPresent() {
        assertEquals(0, BinarySearch.binarySearchArraysFirstIndex(new int[]{5}, 5));
    }

    @Test
    void testBinarySearchArraysFirstIndex_singleElement_targetNotPresent_smaller() {
        assertEquals(-1, BinarySearch.binarySearchArraysFirstIndex(new int[]{5}, 3)); // -(0)-1
    }

    @Test
    void testBinarySearchArraysFirstIndex_singleElement_targetNotPresent_larger() {
        assertEquals(-2, BinarySearch.binarySearchArraysFirstIndex(new int[]{5}, 7)); // -(1)-1
    }

    @Test
    void testBinarySearchArraysFirstIndex_targetNotPresent_general() {
        int[] arr = {10, 20, 30, 40, 50};
        assertEquals(-1, BinarySearch.binarySearchArraysFirstIndex(arr, 5));  // Before all
        assertEquals(-3, BinarySearch.binarySearchArraysFirstIndex(arr, 25)); // In between, -(2)-1
        assertEquals(-6, BinarySearch.binarySearchArraysFirstIndex(arr, 55)); // After all, -(5)-1
    }

    @Test
    void testBinarySearchArraysFirstIndex_targetPresent_singleOccurrence() {
        int[] arr = {10, 20, 30};
        assertEquals(1, BinarySearch.binarySearchArraysFirstIndex(arr, 20));
    }

    @Test
    void testBinarySearchArraysFirstIndex_targetPresent_multipleOccurrences_middle() {
        int[] arr = {10, 20, 20, 20, 30};
        assertEquals(1, BinarySearch.binarySearchArraysFirstIndex(arr, 20));
    }

    @Test
    void testBinarySearchArraysFirstIndex_targetPresent_multipleOccurrences_atStart() {
        int[] arr = {20, 20, 20, 30, 40};
        assertEquals(0, BinarySearch.binarySearchArraysFirstIndex(arr, 20));
    }

    @Test
    void testBinarySearchArraysFirstIndex_targetPresent_multipleOccurrences_atEndSection() {
        int[] arr = {10, 20, 30, 30, 30};
        assertEquals(2, BinarySearch.binarySearchArraysFirstIndex(arr, 30));
    }

    @Test
    void testBinarySearchArraysFirstIndex_allSameElements() {
        int[] arr = {28, 28, 28, 28, 28};
        assertEquals(0, BinarySearch.binarySearchArraysFirstIndex(arr, 28));
    }

    //--- Test cases for binarySearchArraysLastIndex ---

    @Test
    void testBinarySearchArraysLastIndex_emptyArray() {
        assertEquals(-1, BinarySearch.binarySearchArraysLastIndex(new int[]{}, 5));
    }

    @Test
    void testBinarySearchArraysLastIndex_singleElement_targetPresent() {
        assertEquals(0, BinarySearch.binarySearchArraysLastIndex(new int[]{5}, 5));
    }

    @Test
    void testBinarySearchArraysLastIndex_singleElement_targetNotPresent_smaller() {
        assertEquals(-1, BinarySearch.binarySearchArraysLastIndex(new int[]{5}, 3));
    }

    @Test
    void testBinarySearchArraysLastIndex_singleElement_targetNotPresent_larger() {
        assertEquals(-2, BinarySearch.binarySearchArraysLastIndex(new int[]{5}, 7));
    }

    @Test
    void testBinarySearchArraysLastIndex_targetNotPresent_general() {
        int[] arr = {10, 20, 30, 40, 50};
        assertEquals(-1, BinarySearch.binarySearchArraysLastIndex(arr, 5));
        assertEquals(-3, BinarySearch.binarySearchArraysLastIndex(arr, 25));
        assertEquals(-6, BinarySearch.binarySearchArraysLastIndex(arr, 55));
    }

    @Test
    void testBinarySearchArraysLastIndex_targetPresent_singleOccurrence() {
        int[] arr = {10, 20, 30};
        assertEquals(1, BinarySearch.binarySearchArraysLastIndex(arr, 20));
    }

    @Test
    void testBinarySearchArraysLastIndex_targetPresent_multipleOccurrences_middle() {
        int[] arr = {10, 20, 20, 20, 30};
        assertEquals(3, BinarySearch.binarySearchArraysLastIndex(arr, 20));
    }

    @Test
    void testBinarySearchArraysLastIndex_targetPresent_multipleOccurrences_atStartSection() {
        int[] arr = {20, 20, 20, 30, 40};
        assertEquals(2, BinarySearch.binarySearchArraysLastIndex(arr, 20));
    }

    @Test
    void testBinarySearchArraysLastIndex_targetPresent_multipleOccurrences_atEnd() {
        int[] arr = {10, 20, 30, 30, 30};
        assertEquals(4, BinarySearch.binarySearchArraysLastIndex(arr, 30));
    }

    @Test
    void testBinarySearchArraysLastIndex_allSameElements() {
        int[] arr = {28, 28, 28, 28, 28};
        assertEquals(4, BinarySearch.binarySearchArraysLastIndex(arr, 28));
    }

    //--- Test cases for binarySearchArrays(int[] arr, int target) ---

    @Test
    void testBinarySearchArrays_full_emptyArray() {
        int[] arr = {};
        assertEquals(Arrays.binarySearch(arr, 5), BinarySearch.binarySearchArrays(arr, 5));
    }

    @Test
    void testBinarySearchArrays_full_targetPresent() {
        int[] arr = {10, 20, 30};
        assertEquals(1, BinarySearch.binarySearchArrays(arr, 20));
        assertEquals(Arrays.binarySearch(arr, 20), BinarySearch.binarySearchArrays(arr, 20));
    }

    @Test
    void testBinarySearchArrays_full_targetNotPresent() {
        int[] arr = {10, 20, 30};
        assertEquals(-2, BinarySearch.binarySearchArrays(arr, 15)); // -(1)-1
        assertEquals(Arrays.binarySearch(arr, 15), BinarySearch.binarySearchArrays(arr, 15));
    }

    @Test
    void testBinarySearchArrays_full_targetPresent_duplicates() {
        int[] arr = {10, 20, 20, 20, 30};
        int target = 20;
        int result = BinarySearch.binarySearchArrays(arr, target);
        // Arrays.binarySearch on duplicates returns *an* index.
        // We verify our method returns the same as the standard library.
        assertEquals(Arrays.binarySearch(arr, target), result);
        assertTrue(result >= 1 && result <= 3); // Check it's one of the valid indices
        assertEquals(target, arr[result]);
    }

    //--- Test cases for binarySearchArrays(int[] arr, int fromIndex, int toIndex, int target) ---

    @Test
    void testBinarySearchArrays_range_emptyArrayRange() {
        int[] arr = {};
        assertEquals(Arrays.binarySearch(arr, 0, 0, 5), BinarySearch.binarySearchArrays(arr, 0, 0, 5));
    }

    @Test
    void testBinarySearchArrays_range_validRange_targetPresent() {
        int[] arr = {10, 20, 30, 40, 50}; // Search [20, 30, 40]
        assertEquals(2, BinarySearch.binarySearchArrays(arr, 1, 4, 30));
        assertEquals(Arrays.binarySearch(arr, 1, 4, 30), BinarySearch.binarySearchArrays(arr, 1, 4, 30));
    }

    @Test
    void testBinarySearchArrays_range_validRange_targetNotPresent() {
        int[] arr = {10, 20, 30, 40, 50}; // Search [20, 30, 40]
        assertEquals(-4, BinarySearch.binarySearchArrays(arr, 1, 4, 35)); // -(3)-1
        assertEquals(Arrays.binarySearch(arr, 1, 4, 35), BinarySearch.binarySearchArrays(arr, 1, 4, 35));
    }

    @Test
    void testBinarySearchArrays_range_targetOutsideSpecifiedRange_butInArray() {
        int[] arr = {10, 20, 30, 40, 50}; // Search [10, 20]
        assertEquals(-3, BinarySearch.binarySearchArrays(arr, 0, 2, 30)); // -(2)-1
        assertEquals(Arrays.binarySearch(arr, 0, 2, 30), BinarySearch.binarySearchArrays(arr, 0, 2, 30));
    }

    @Test
    void testBinarySearchArrays_range_emptySubRange_fromEqualsTo() {
        int[] arr = {10, 20, 30};
        assertEquals(-2, BinarySearch.binarySearchArrays(arr, 1, 1, 20)); // -(1)-1
        assertEquals(Arrays.binarySearch(arr, 1, 1, 20), BinarySearch.binarySearchArrays(arr, 1, 1, 20));
    }

    @Test
    void testBinarySearchArrays_range_fullArrayAsRange() {
        int[] arr = {10, 20, 30};
        assertEquals(1, BinarySearch.binarySearchArrays(arr, 0, arr.length, 20));
        assertEquals(Arrays.binarySearch(arr, 0, arr.length, 20), BinarySearch.binarySearchArrays(arr, 0, arr.length, 20));
    }

    //--- Test cases for binarySearchCollections(List<Integer> list, int target) ---

    @Test
    void testBinarySearchCollections_emptyList() {
        List<Integer> list = Collections.emptyList();
        assertEquals(Collections.binarySearch(list, 5), BinarySearch.binarySearchCollections(list, 5));
    }

    @Test
    void testBinarySearchCollections_singleElement_targetPresent() {
        List<Integer> list = List.of(5);
        assertEquals(0, BinarySearch.binarySearchCollections(list, 5));
        assertEquals(Collections.binarySearch(list, 5), BinarySearch.binarySearchCollections(list, 5));
    }

    @Test
    void testBinarySearchCollections_targetNotPresent() {
        List<Integer> list = List.of(10, 20, 30);
        assertEquals(-2, BinarySearch.binarySearchCollections(list, 15)); // -(1)-1
        assertEquals(Collections.binarySearch(list, 15), BinarySearch.binarySearchCollections(list, 15));
    }

    @Test
    void testBinarySearchCollections_targetPresent() {
        List<Integer> list = List.of(10, 20, 30);
        assertEquals(1, BinarySearch.binarySearchCollections(list, 20));
        assertEquals(Collections.binarySearch(list, 20), BinarySearch.binarySearchCollections(list, 20));
    }

    @Test
    void testBinarySearchCollections_targetPresent_duplicates() {
        List<Integer> list = List.of(10, 20, 20, 20, 30);
        int target = 20;
        int result = BinarySearch.binarySearchCollections(list, target);
        // Collections.binarySearch on duplicates returns *an* index.
        assertEquals(Collections.binarySearch(list, target), result);
        assertTrue(result >= 1 && result <= 3); // Check it's one of the valid indices
        assertEquals(target, list.get(result));
    }
}
