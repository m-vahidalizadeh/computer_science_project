package algorithms;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class BinaryTree {

    static TreeNode root;

    public static void main2(String[] args) {

        BinaryTree tree = new BinaryTree();
        tree.root = new TreeNode(1);
        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);
//        tree.root.left.left.left = new TreeNode(8);

        if(tree.isBalanced(root))
            System.out.println("Tree is balanced");
        else
            System.out.println("Tree is not balanced");

    }

    boolean isBalanced(TreeNode node) {
        if ((node == null) || (Math.abs(height(node.left) - height(node.right)) <= 1
                && isBalanced(node.left)
                && isBalanced(node.right))) {
            return true;
        }else{
            return false;
        }
    }

    int height(TreeNode node) {
        /* base case tree is empty */
        if (node == null) {
            return 0;
        }
        /* If tree is not empty then height = 1 + max of left
         height and right heights */
        return 1 + Math.max(height(node.left), height(node.right));
    }

}
