package base;

import data_structures.linked_list.LLNode;

public class Utils {

    private Utils() {
    }

    public static void printLinkedList(LLNode head) {
        while (head != null) {
            System.out.print(head.getData() + " ");
            head = head.getNext();
        }
        System.out.println();
    }

}
