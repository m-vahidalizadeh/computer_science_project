package base;

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
