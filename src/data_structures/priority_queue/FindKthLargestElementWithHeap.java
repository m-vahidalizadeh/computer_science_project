package data_structures.priority_queue;

import java.util.PriorityQueue;

/**
 * Created by Mohammad on 9/19/2016.
 */
public class FindKthLargestElementWithHeap {

    /**
     * We can use a min heap to solve this problem. The heap stores the top k elements.
     * Whenever the size is greater than k, delete the min.
     * Time complexity is O(nlog(k)).
     * Space complexity is O(k) for storing the top k numbers.
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<>(k);
        for(int i: nums){
            q.offer(i);

            if(q.size()>k){
                q.poll();
            }
        }

        return q.peek();
    }

    public static void main(String args[]) {
        int[] nums = {5,8,6,97,12,3,5,6,4,2,3,};
        //Return the second largest number
        System.out.println(findKthLargest(nums,2));
    }

}
