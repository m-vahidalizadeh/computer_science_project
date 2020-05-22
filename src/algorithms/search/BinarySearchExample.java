package algorithms.search;

import java.util.Arrays;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class BinarySearchExample {

    //Find one occurrence
    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    //Find all occurrence
    public static void PrintIndicesForValue(int[] numbers, int target) {
        if (numbers == null)
            return;

        int low = 0, high = numbers.length - 1;
        // get the start index of target number
        int startIndex = -1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (numbers[mid] > target) {
                high = mid - 1;
            } else if (numbers[mid] == target) {
                startIndex = mid;
                high = mid - 1;
            } else
                low = mid + 1;
        }

        // get the end index of target number
        int endIndex = -1;
        low = 0;
        high = numbers.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (numbers[mid] > target) {
                high = mid - 1;
            } else if (numbers[mid] == target) {
                endIndex = mid;
                low = mid + 1;
            } else
                low = mid + 1;
        }

        if (startIndex != -1 && endIndex != -1){
            System.out.print("All: ");
            for(int i=0; i+startIndex<=endIndex;i++){
                if(i>0)
                    System.out.print(',');
                System.out.print(i+startIndex);
            }
        }
    }

    public static void main(String[] args) {

        // read the integers from a file
        int[] arr = {23,34,12,24,266,1,3,66,78,93,22,24,25,27};
        Boolean[] arrFlag = new Boolean[arr.length];
        Arrays.fill(arrFlag,false);

        // sort the array
        Arrays.sort(arr);

        //Search
        System.out.print("Array: ");
        for(int i=0; i<arr.length; i++)
            if(i != arr.length-1){
                System.out.print(arr[i]+",");
            }else{
                System.out.print(arr[i]);
            }

        System.out.println("\nOnly one: "+indexOf(arr,24));
        PrintIndicesForValue(arr,24);

    }

}
