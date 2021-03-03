package data_structures.tree;

import java.util.ArrayList;
import java.util.List;

public class SerializeDeserializeNaryTree {

    class Node {
        public int val;
        public List<Node> children;

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public String serialize(Node root) {
        if (root == null) return ""; // Serialization base case
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(root.val).append(",").append(root.children.size()); // Add root val+,+num of children
        for (Node child : root.children)
            stringBuilder.append(",").append(serialize(child)); // Add children recursively, one by one
        return stringBuilder.toString(); // Return result
    }

    int i;

    public Node deserialize(String data) {
        if (data.isBlank()) return null; // Base case root is null
        i = 0; // The index on the tokens
        return recursiveDeserialize(data.split(",")); // Recursively build the tree from the tokenized string
    }

    private Node recursiveDeserialize(String[] nums) {
        if (i == nums.length) return null; // Base case- no more child
        Node root = new Node(Integer.parseInt(nums[i++]), new ArrayList<>()); // Build the root
        int childrenCount = Integer.parseInt(nums[i++]); // Get number of children
        for (int j = 0; j < childrenCount; j++) { // Add children recursively one by one
            Node child = recursiveDeserialize(nums);
            if (child != null) root.children.add(child); // If child is not null, add it to the children of root
        }
        return root; // Return the root of the tree
    }

}
