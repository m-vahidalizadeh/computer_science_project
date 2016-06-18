package algorithms;

import java.util.Arrays;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class Sorting {

    public static void main(String[] args) {
        Integer[] input1 = new Integer[]{4,3,2,0,1};
        String[] input2 = new String[]{"E","D","C","A","B"};
        Arrays.sort(input1);
        Arrays.sort(input2);
        print(input1);
        print(input2);
    }

    private static void print(Integer[] input){
        System.out.println();
        for(int i=0;i<input.length; i++){
            System.out.print(input[i]+" ");
        }
    }

    private static void print(String[] input){
        System.out.println();
        for(int i=0;i<input.length; i++){
            System.out.print(input[i]+" ");
        }
    }

}
