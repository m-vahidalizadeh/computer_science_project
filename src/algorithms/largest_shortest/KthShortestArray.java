package algorithms.largest_shortest;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Mohammad on 6/19/2016.
 */
public class KthShortestArray {
    public static void main(String[] args) {
        //Sort ascending
        Integer[] intArray = new Integer[]{1,4,44,9,12,3,5,77,12,19,17,34,56,7,32};
        Arrays.sort(intArray);
        System.out.print("Sorted array: ");
        print1DArray(intArray);
        System.out.println("3th shortest number: "+intArray[2]);
    }
    private static void print1DArray(Integer[] input){
        for(int i=0;i<input.length;i++){
            System.out.print(input[i]+" ");
        }
        System.out.println();
    }
}
