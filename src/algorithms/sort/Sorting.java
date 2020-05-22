package algorithms.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class Sorting {

    public static void main(String[] args) {
        Integer[] input1 = new Integer[]{4,3,2,0,1};
        String[] input2 = new String[]{"E","D","C","A","B"};
        Arrays.sort(input1);
        Arrays.sort(input2);
        System.out.println("Sort numbers ascending: ");
        print(input1);
        System.out.println("\nSort characters ascending: ");
        print(input2);
        Arrays.sort(input1, Comparator.reverseOrder());
        System.out.println("\nSort numbers descending: ");
        print(input1);
    }

    private static void print(Integer[] input){
        //System.out.println();
        for(int i=0;i<input.length; i++){
            System.out.print(input[i]+" ");
        }
    }

    private static void print(String[] input){
        //System.out.println();
        for(int i=0;i<input.length; i++){
            System.out.print(input[i]+" ");
        }
    }

}
