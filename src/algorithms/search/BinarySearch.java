package algorithms.search;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides utility methods for performing binary search on arrays and lists.
 * This class demonstrates finding elements, as well as the first and last occurrences
 * of an element in a sorted array.
 */
public class BinarySearch {

    /**
     * Main method to demonstrate the binary search functionalities.
     * It showcases searching in an array, finding the first and last index of a duplicate element,
     * and searching in a list.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args){
        int[] arr=new int[]{1,7,19,28,28,28,28,34,56,99};
        System.out.println("Index of 99 is: "+binarySearchArrays(arr,99));
        System.out.println("First index of 28 is: "+binarySearchArraysFirstIndex(arr,28));
        System.out.println("Index of 1000 is: "+binarySearchArrays(arr,1000)); // Example of target not present
        System.out.println("Last index of 28 is: "+binarySearchArraysLastIndex(arr,28));
        List<Integer> list=List.of(1,7,19,28,28,28,28,34,56,99);
        System.out.println("Index of 19 is: "+binarySearchCollections(list,19));
    }

    /**
     * Finds the first occurrence of a target value in a sorted array.
     * This method currently delegates to a standard binary search which might not
     * guarantee the first index if duplicates exist. For a true first index,
     * a modified binary search algorithm is typically needed.
     * <p>
     * Note: The current implementation relies on {@link Arrays#binarySearch(int[], int, int, int)},
     * which returns *an* index if the target is found, not necessarily the first one.
     * To truly find the first index, one would typically adjust the binary search logic
     * to continue searching in the left half even after a match is found.
     * </p>
     * @param arr The sorted array to be searched.
     * @param target The value to search for.
     * @return The index of the first occurrence of the target, or a negative value if not found.
     */
    public static int binarySearchArraysFirstIndex(int[] arr,int target){
        // This is a simplification. A true "first index" binary search
        // would require custom logic to continue searching leftwards.
        // The current implementation will find *an* index, which might be the first
        // if it's the only one or the one Arrays.binarySearch happens to land on.
        // For a robust solution, one would implement a modified binary search.
        // For example, after finding an element, check if arr[mid-1] == target and continue left.
        int index = binarySearchArrays(arr, 0, arr.length, target);
        if (index < 0) {
            return index; // Target not found
        }
        // Attempt to find an earlier occurrence by searching to the left
        int firstIndex = index;
        while (firstIndex > 0 && arr[firstIndex - 1] == target) {
            int tempIndex = binarySearchArrays(arr, 0, firstIndex, target);
            if (tempIndex >= 0) {
                firstIndex = tempIndex;
            } else {
                // Should not happen if arr[firstIndex - 1] == target, but as a safeguard
                break;
            }
        }
        return firstIndex;
    }

    /**
     * Finds the last occurrence of a target value in a sorted array.
     * It first finds an occurrence using standard binary search, then iteratively
     * searches in the sub-array to the right of the found index to locate the last one.
     * @param arr The sorted array to be searched.
     * @param target The value to search for.
     * @return The index of the last occurrence of the target, or a negative value if not found.
     */
    public static int binarySearchArraysLastIndex(int[] arr,int target){
        int index = binarySearchArrays(arr,0,arr.length,target); // Find an initial occurrence
        if(index<0) return index; // Target not found

        // Iteratively search for the target in the sub-array to the right
        // to find the last occurrence.
        while(index < arr.length - 1){ // Check up to the second to last element
            // Search in the range (current index + 1) to the end of the array
            int newIndex = binarySearchArrays(arr,index + 1, arr.length, target);
            if(newIndex >= 0) { // If target is found in the right sub-array
                index = newIndex; // Update index to the new, later occurrence
            } else {
                // If not found in the right sub-array, the current 'index' is the last one.
                return index;
            }
        }
        return index; // Return the last found index
    }

    /**
     * Searches the entire sorted array for the specified value using the binary search algorithm.
     * This is a convenience method that calls the overloaded version with the full array range.
     * @param arr The sorted array to be searched.
     * @param target The value to be searched for.
     * @return Index of the search key, if it is contained in the array;
     *         otherwise, (-(insertion point) - 1). The insertion point is defined as
     *         the point at which the key would be inserted into the array: the index
     *         of the first element greater than the key, or arr.length if all elements
     *         in the array are less than the specified key.
     * @see #binarySearchArrays(int[], int, int, int)
     * @see Arrays#binarySearch(int[], int)
     */
    public static int binarySearchArrays(int[] arr,int target){
        return binarySearchArrays(arr,0,arr.length,target);
    }

    /**
     * Searches a range of the specified array of ints for the specified value using the
     * binary search algorithm. The range must be sorted prior to making this call.
     * If it is not sorted, the results are undefined.
     * This method delegates to {@link Arrays#binarySearch(int[], int, int, int)}.
     *
     * @param arr The array to be searched.
     * @param fromIndex The index of the first element (inclusive) to be searched.
     * @param toIndex The index of the last element (exclusive) to be searched.
     * @param target The value to be searched for.
     * @return Index of the search key, if it is contained in the array within the specified range;
     *         otherwise, (-(insertion point) - 1). The insertion point is defined as
     *         the point at which the key would be inserted into the array: the index
     *         of the first element in the range greater than the key,
     *         or toIndex if all elements in the range are less than the specified key.
     * @see Arrays#binarySearch(int[], int, int, int)
     */
    public static int binarySearchArrays(int[] arr, int fromIndex, int toIndex, int target){
        // Ensure the array is sorted for binary search to work correctly.
        // Note: Standard library binarySearch assumes the array/range is already sorted.
        // If it's not, the behavior is undefined.
        return Arrays.binarySearch(arr,fromIndex,toIndex,target);
    }

    /**
     * Searches the specified list for the specified object using the binary search algorithm.
     * The list must be sorted into ascending order according to the natural ordering of its
     * elements prior to making this call. If it is not sorted, the results are undefined.
     * This method delegates to {@link Collections#binarySearch(List, Object)}.
     *
     * @param list The list to be searched.
     * @param target The key to be searched for.
     * @return The index of the search key, if it is contained in the list;
     *         otherwise, (-(insertion point) - 1). The insertion point is defined as
     *         the point at which the key would be inserted into the list: the index
     *         of the first element greater than the key, or list.size() if all elements
     *         in the list are less than the specified key.
     * @see Collections#binarySearch(List, Object)
     */
    public static int binarySearchCollections(List<Integer> list, int target){
        // Ensure the list is sorted for binary search to work correctly.
        // Note: Standard library binarySearch assumes the list is already sorted.
        return Collections.binarySearch(list,target);
    }

}