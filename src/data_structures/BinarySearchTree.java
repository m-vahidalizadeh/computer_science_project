package data_structures;

import base.BTNode;

public class BinarySearchTree {

    public static void main(String[] args) {
        /*
        Example tree:
            10
           / \
          2   30
         / \   \
        1   5   70
         */
        BTNode node10 = new BTNode(10);
        BTNode node2 = new BTNode(2);
        BTNode node30 = new BTNode(30);
        BTNode node4 = new BTNode(4);
        BTNode node5 = new BTNode(5);
        BTNode node70 = new BTNode(70);
        node10.setLeft(node2);
        node10.setRight(node30);
        node2.setLeft(node4);
        node2.setRight(node5);
        node30.setRight(node70);
        System.out.format("The least common ancestor of 5 and 70 is %d", findLeastCommonAncestor(node10, 5, 70).getValue());
    }

    public static BTNode findLeastCommonAncestor(BTNode node, int n1, int n2) {
        if (node == null)
            return node;
        if (node.getValue() > n1 && node.getValue() > n2)
            return findLeastCommonAncestor(node.getLeft(), n1, n2);
        if (node.getValue() < n1 && node.getValue() < n2)
            return findLeastCommonAncestor(node.getRight(), n1, n2);
        return node;
    }

}
