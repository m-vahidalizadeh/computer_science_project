package data_structures;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class TreeTraverse {

    public static class TreeNode {
        public Integer data;
        public TreeNode left;
        public TreeNode right;
        public Boolean visited;

        public TreeNode(Integer data) {
            this.right = null;
            this.data = data;
            this.left = null;
            visited= false;
        }
    }

    public static class BinaryTree {

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

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root = new TreeNode(1);
        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);
        tree.root.left.left.left = new TreeNode(8);
        System.out.println();
        System.out.print("DFS in: ");
        dfsInorder(tree.root);
        System.out.println();
        System.out.print("DFS pre: ");
        dfsPreorder(tree.root);
        System.out.println();
        System.out.print("DFS post: ");
        dfsPostorder(tree.root);
        System.out.println();
        System.out.print("BFS: ");
        bfs(tree.root);
    }

    private static void dfsInorder(TreeNode root){
        if(root == null)    return;
        dfsInorder(root.left);
        System.out.print(root.data + " ");
        dfsInorder(root.right);
    }

    private static void dfsPostorder(TreeNode root){
        if(root == null)    return;
        dfsPostorder(root.left);
        dfsPostorder(root.right);
        System.out.print(root.data + " ");
    }

    private static void dfsPreorder(TreeNode root){
        if(root == null)    return;
        System.out.print(root.data + " ");
        dfsPreorder(root.left);
        dfsPreorder(root.right);
    }

    private static void bfs(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while(queue.size() != 0){
            TreeNode treeNode = queue.poll();
            System.out.print(treeNode.data+" ");
            if(treeNode.left != null){
                queue.add(treeNode.left);
            }
            if(treeNode.right != null){
                queue.add(treeNode.right);
            }
        }


    }
}
