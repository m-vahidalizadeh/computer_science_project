package algorithms;

/**
 * Created by Mohammad on 9/18/2016.
 */
public interface TreeTraverseInterface {

    class Node{
        Node(int data){
            this.data = data;
            this.left = null;
            this.right = null;
            this.visited = false;
        }
        int data;
        Node left;
        Node right;
        boolean visited;
    }

    public void depthFirstSearch(Node node);

    public void breadthFirstSearch(Node node);
}
