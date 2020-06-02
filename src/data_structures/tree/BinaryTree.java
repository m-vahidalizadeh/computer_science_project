package data_structures.tree;

import java.util.LinkedList;
import java.util.List;

public class BinaryTree {

    public static void main(String[] args) {
        /*
        Example tree:
            1
           / \
          2   3
         / \   \
        4   5   7
         */
        BTNode node1 = new BTNode(1);
        BTNode node2 = new BTNode(2);
        BTNode node3 = new BTNode(3);
        BTNode node4 = new BTNode(4);
        BTNode node5 = new BTNode(5);
        BTNode node7 = new BTNode(7);
        node1.setLeft(node2);
        node1.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setRight(node7);
        System.out.format("Height of the tree is %d", getHeight(node1));
        BTNode[] arrayRepresentation = getArrayRepresentation(node1);
        System.out.println();
        System.out.print("Array representation: ");
        for (int i = 0; i < arrayRepresentation.length; i++) {
            if (arrayRepresentation[i] == null)
                System.out.print("null ");
            else
                System.out.print(arrayRepresentation[i].getValue() + " ");
        }
        System.out.println();
        System.out.format("Parent of %d is %d", arrayRepresentation[7].getValue(), getParentOf(arrayRepresentation, 7).getValue());
        System.out.println();
        System.out.format("Left child of %d is %d", arrayRepresentation[2].getValue(), getLeftChild(arrayRepresentation, 2).getValue());
        System.out.println();
        System.out.format("Right child of %d is %d", arrayRepresentation[3].getValue(), getRightChild(arrayRepresentation, 3).getValue());
        System.out.println();
        System.out.format("The path to %d is:", 7);
        List<BTNode> path = new LinkedList<>();
        findPath(node1, 7, path);
        for (BTNode n : path) {
            System.out.format("%d ", n.getValue());
        }
        System.out.println();
        System.out.format("The path to %d is:", 4);
        path = new LinkedList<>();
        findPath(node1, 4, path);
        for (BTNode n : path) {
            System.out.format("%d ", n.getValue());
        }
        System.out.println();
        System.out.format("Least common ancestor of 5 and 7 is %d", findLeastCommonAncestor(5, 7, node1).getValue());
        System.out.println();
        System.out.format("Least common ancestor of 4 and 5 is %d", findLeastCommonAncestor(4, 5, node1).getValue());
    }

    /**
     * Gets the height of a binary tree.
     *
     * @param root The root of the binary tree
     * @return The height of a binary tree
     */
    public static int getHeight(BTNode root) {
        if (root == null)
            return 0;
        return 1 + Math.max(getHeight(root.getLeft()), getHeight(root.getRight()));
    }

    /***
     * Gets the array representation of the binary tree;
     *
     * @param root The root of the binary tree
     * @return The array representation of the binary tree
     */
    public static BTNode[] getArrayRepresentation(BTNode root) {
        int h = getHeight(root);
        BTNode[] arrayRepresentation = new BTNode[Double.valueOf(Math.pow(2, h)).intValue()];
        getLocationsInArray(root, 1, arrayRepresentation);
        return arrayRepresentation;
    }

    private static void getLocationsInArray(BTNode node, int location, BTNode[] arrayRepresentation) {
        if (node == null)
            return;
        arrayRepresentation[location] = node;
        int leftChildLocation = 2 * location;
        getLocationsInArray(node.getLeft(), leftChildLocation, arrayRepresentation);
        getLocationsInArray(node.getRight(), leftChildLocation + 1, arrayRepresentation);
    }

    /**
     * Gets the parent of a binary tree node in an array representation
     *
     * @param arrayRepresentation The array representation
     * @param currentNodeIndex    The index of the current node
     * @return The parent of a binary tree noe in an array representation
     */
    public static BTNode getParentOf(BTNode[] arrayRepresentation, int currentNodeIndex) {
        return arrayRepresentation[currentNodeIndex / 2];
    }

    /**
     * Gets the left child of a binary tree node.
     *
     * @param arrayRepresentation The array representation
     * @param parentIndex         The index of the parent node
     * @return The left child of the parent
     */
    public static BTNode getLeftChild(BTNode[] arrayRepresentation, int parentIndex) {
        return arrayRepresentation[2 * parentIndex];
    }

    /**
     * Gets the right child of the parent in a binary tree.
     *
     * @param arrayRepresentation The array representation of a binary tree
     * @param parentIndex         The index of the parent node
     * @return The right child of the parent in a binary tree
     */
    public static BTNode getRightChild(BTNode[] arrayRepresentation, int parentIndex) {
        return arrayRepresentation[2 * parentIndex + 1];
    }

    /**
     * Finds the least common ancestor of two nodes in a binary tree.
     *
     * @param n1   The value of node 1
     * @param n2   The value of node 2
     * @param root The root of the binary tree
     * @return The least common ancestor of nodes n1 and n2
     */
    public static BTNode findLeastCommonAncestor(int n1, int n2, BTNode root) {
        List<BTNode> path1 = new LinkedList<>();
        List<BTNode> path2 = new LinkedList<>();
        if (!findPath(root, n1, path1) || !findPath(root, n2, path2)) {
            // One of the nodes doesn't exist in the tree
            return new BTNode(-1);
        }
        int i;
        for (i = 0; i < path1.size() && i < path2.size(); i++) {
            if (!path1.get(i).equals(path2.get(i)))
                break;
        }
        return path1.get(i - 1);
    }

    /**
     * Finds the path from the root to the node with value target. Returns false if the nodes doesn't exist in the tree.
     *
     * @param root   The root of the binary tree
     * @param target The value of the target node
     * @param path   The path to the target node
     * @return If the target nodes exist in the tree or not
     */
    public static boolean findPath(BTNode root, int target, List<BTNode> path) {
        return findPathRecursive(root, target, path);
    }

    private static boolean findPathRecursive(BTNode node, int target, List<BTNode> path) {
        if (node == null)
            return false;
        path.add(node);
        if (node.getValue() == target) return true;
        if (node.getLeft() != null && findPathRecursive(node.getLeft(), target, path)) return true;
        if (node.getRight() != null && findPathRecursive(node.getRight(), target, path)) return true;
        path.remove(path.size() - 1);
        return false;
    }

}
