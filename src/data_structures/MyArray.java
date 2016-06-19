package data_structures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Created by Mohammad on 6/18/2016.
 */
public class MyArray {
    public static void main(String[] args) {
        //1D
        int[] array1D = new int[20];
        Arrays.fill(array1D,1);
        array1D[19] = 10;
        array1D[2] = 5;
        System.out.println("Original 1D:");
        printArray1D(array1D);
        Arrays.sort(array1D);
        System.out.println("\nAfter sorting:");
        printArray1D(array1D);
        int key = 20;
        long startTime = System.currentTimeMillis();
        System.out.println("\nDoes array contains "+key+": "+ keyFinder(20,array1D));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        int key2 = 10;
        startTime = System.currentTimeMillis();
        System.out.println("\nDoes array contains "+key2+": "+ keyFinder(10,array1D));
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(totalTime);
        int key3 = 20;
        startTime = System.currentTimeMillis();
        System.out.println("\nDoes array contains "+key3+": "+ Arrays.binarySearch(array1D,key3));
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(totalTime);



//        long startTime = System.currentTimeMillis();
//        long endTime   = System.currentTimeMillis();
//        long totalTime = endTime - startTime;
//        System.out.println(totalTime);

//        //2D
//        Integer[][] array2D = new Integer[20][20];
//        for(int i=0; i<array2D.length; i++){
//            Arrays.fill(array2D[i],1);
//        }
//        array2D[19][9] = 10;
//        array2D[2][10] = 5;
//        System.out.println("\nOriginal 2D:");
//        printArray2D(array2D);
//        final Comparator<Integer[]> arrayComparator = new Comparator<Integer[]>() {
//            @Override
//            public int compare(Integer[] o1, Integer[] o2) {
//                return o1[0].compareTo(o2[0]);
//            }
//        };
//        Arrays.sort(array2D, arrayComparator);
//        System.out.println("\nAfter sorting:");
//        printArray2D(array2D);
    }
    private static boolean keyFinder(int key, int[] input){
        return IntStream.of(input).anyMatch(x -> x == key);
    }
    private static void printArray1D(int[] input){
        for(int i=0; i<input.length; i++){
                System.out.print(input[i]+" ");
        }
    }
//    private static void printArray2D(Integer[][] input){
//        for(int i=0; i<input.length; i++){
//            for(int j=0; j<input[i].length; j++){
//                System.out.print(input[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }
}
