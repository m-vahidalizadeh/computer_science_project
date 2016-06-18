package algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {

    public static void main1(String[] args) {

        Integer[] input= new Integer[] {12, 3, 5, 7, 19};
        Integer k= 4;

        printArray("Array", input);

        findKthLargestBySort("Kth largest number by sorting",input,k);
        findKthLargestByHeap("Kth largest number by heap",input,k);

//        Arrays.sort(input);
//
//        printArray("Sort",input);
//
//        Arrays.sort(input, Collections.reverseOrder());
//
//        printArray("Sort (reverse)",input);

    }

    private static Integer findKthLargestBySort(String title, Integer[] inputArray, Integer k){
        System.out.print(title + ": ");
        //Sort is ascending by default
        Arrays.sort(inputArray, Collections.reverseOrder());
        System.out.print(inputArray[k - 1]+"\n");
        return inputArray[k-1];
    }

    public static Integer findKthLargestByHeap(String title, Integer[] inputArray, Integer k){
        System.out.print(title+": ");
        //Building a max-heap
        //* Priority queue is min-heap by default
        PriorityQueue<Integer> q= new PriorityQueue<Integer>(inputArray.length, Collections.reverseOrder());
        for(int i=0; i<inputArray.length; i++){
            q.offer(inputArray[i]);
        }
        Integer kthLargest= -1;
        for(int i=0; i< k; i++){
            kthLargest= q.poll();
        }
        System.out.print(kthLargest+"\n");
        return kthLargest;
    }

    private static void printArray(String title, Integer[] inputArray){

        System.out.print(title + ": ");
        for(int i=0; i<inputArray.length; i++){
            System.out.print(inputArray[i]);
            if(i != inputArray.length-1){
                System.out.print(",");
            }else{
                System.out.println();
            }
        }
    }


}
