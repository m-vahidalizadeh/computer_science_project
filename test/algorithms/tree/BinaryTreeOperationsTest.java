package algorithms.tree;

import data_structures.tree.BTNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit test os the class binary tree operations.
 */
public class BinaryTreeOperationsTest {

    /**
     * This is testing getting the column order traversal of a binary tree.
     */
    @Test
    public void getColumnOrderTest() {
        /*
             1
            / \
           2   3
          /     \
         4       5
                /
               6
         Expected output: 4 2 1 3 6 5
         */
        BTNode node1 = new BTNode(1);
        BTNode node2 = new BTNode(2);
        BTNode node3 = new BTNode(3);
        BTNode node4 = new BTNode(4);
        BTNode node5 = new BTNode(5);
        BTNode node6 = new BTNode(6);
        node1.setLeft(node2);
        node2.setLeft(node4);
        node1.setRight(node3);
        node3.setRight(node5);
        node5.setLeft(node6);
        BinaryTreeOperations b = new BinaryTreeOperations();
        List<BTNode> result = b.getColumnOrder(node1);
        Assertions.assertEquals(List.of(node4, node2, node1, node3, node6, node5), result);
    }


}
