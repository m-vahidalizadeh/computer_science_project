package algorithms;

import base.LLNode;

public class LinkedListOperations {

    public LLNode reverse(LLNode head) {
        /*
Input: Head of following linked list
1->2->3->4->NULL
Output: Linked list should be changed to,
4->3->2->1->NULL

Input: NULL
Output: NULL

Input: 1->NULL
Output: 1->NULL
         */
        LLNode prev = null;
        LLNode curr = head;
        LLNode next = null;
        while (curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        LLNode node4 = new LLNode(4);
        LLNode node3 = new LLNode(3, node4);
        LLNode node2 = new LLNode(2, node3);
        LLNode node1 = new LLNode(1, node2);
        LinkedListOperations l = new LinkedListOperations();
        base.Utils.printLinkedList(node1);
        base.Utils.printLinkedList(l.reverse(node1));
        base.Utils.printLinkedList(null);
        base.Utils.printLinkedList(l.reverse(null));
        LLNode node1_1 = new LLNode(1);
        base.Utils.printLinkedList(node1_1);
        base.Utils.printLinkedList(l.reverse(node1_1));
    }

}
