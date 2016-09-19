package algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammad on 9/18/2016.
 */
public class TreeTraverseByInterface implements TreeTraverseInterface {

    public static void main(String[] args) {
        //The tree:
        //   1
        //  / \
        // 7   9
        // \  / \
        //  8 2 3

        Node node1 = new Node(1);
        Node node7 = new Node(7);
        Node node9 = new Node(9);
        Node node8 = new Node(8);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.left = node7;
        node1.right = node9;
        node7.right = node8;
        node9.right = node3;
        node9.left = node2;
        TreeTraverseByInterface dFSByInterface = new TreeTraverseByInterface();
        System.out.println("DFS: ");
        dFSByInterface.depthFirstSearch(node1);
        System.out.println("\nBFS: ");
        dFSByInterface.breadthFirstSearch(node1);

    }

    @Override
    public void depthFirstSearch(Node node) {
        if(node.left == null && node.right == null){
            System.out.print(node.data+" ");
            node.visited = true;
        }else if(node.left == null || node.left.visited){
            depthFirstSearch(node.right);
            System.out.print(node.data+" ");
            node.visited = true;
        }else{
            depthFirstSearch(node.left);
            node.visited = true;
            System.out.print(node.data+" ");
            depthFirstSearch(node.right);

        }
    }

    @Override
    public void breadthFirstSearch(Node node) {
        List<Node> al = new ArrayList<>();
        al.add(node);
        while(!al.isEmpty()){
            node = al.get(0);
            if(node.left != null){
                int index = al.size();
                al.add(index,node.left);
            }
            if(node.right != null){
                int index = al.size();
                al.add(index,node.right);
            }
            System.out.print(al.get(0).data+" ");
            al.remove(0);


        }
    }


}
