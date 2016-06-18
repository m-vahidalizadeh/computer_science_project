package algorithms;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class TreeNode {
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
