package base;

/**
 * Binary tree node.
 */
public class BTNode {

    BTNode left;
    BTNode right;
    int value;

    public BTNode(int value) {
        this.value = value;
    }

    public BTNode(int value, BTNode left, BTNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public BTNode getLeft() {
        return left;
    }

    public void setLeft(BTNode left) {
        this.left = left;
    }

    public BTNode getRight() {
        return right;
    }

    public void setRight(BTNode right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
