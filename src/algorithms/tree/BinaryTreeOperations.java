package algorithms.tree;

import data_structures.tree.BTNode;

import java.util.*;

public class BinaryTreeOperations {

    public List<BTNode> getColumnOrder(BTNode root) {
        if (root == null) return Collections.emptyList();
        int minColumn = 0, maxColumn = 0;
        Map<BTNode, Integer> nodeColumns = new HashMap<>();
        nodeColumns.put(root, 0);
        Map<Integer, List<BTNode>> columnsMap = new HashMap<>();
        Queue<BTNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BTNode currNode = q.poll();
            if (currNode == null) continue;
            int currColumn = nodeColumns.get(currNode);
            if (currColumn > maxColumn) maxColumn = currColumn;
            else if (currColumn < minColumn) minColumn = currColumn;
            addCurrNodeToColumnMap(columnsMap, currNode, currColumn);
            addChildrenToQueue(nodeColumns, q, currNode, currColumn);
        }
        return getFlatResult(minColumn, maxColumn, columnsMap);
    }

    private List<BTNode> getFlatResult(int minColumn, int maxColumn, Map<Integer, List<BTNode>> columnsMap) {
        List<BTNode> result = new ArrayList<>();
        for (int i = minColumn; i <= maxColumn; i++) {
            if (columnsMap.containsKey(i)) result.addAll(columnsMap.get(i));
        }
        return result;
    }

    private void addChildrenToQueue(Map<BTNode, Integer> nodeColumns, Queue<BTNode> q, BTNode currNode, int currColumn) {
        BTNode l = currNode.getLeft();
        nodeColumns.put(l, currColumn - 1);
        q.add(l);
        BTNode r = currNode.getRight();
        nodeColumns.put(r, currColumn + 1);
        q.add(r);
    }

    private void addCurrNodeToColumnMap(Map<Integer, List<BTNode>> columnsMap, BTNode currNode, int currColumn) {
        if (columnsMap.containsKey(currColumn)) {
            List<BTNode> currList = columnsMap.get(currColumn);
            currList.add(currNode);
            columnsMap.put(currColumn, currList);
        } else {
            List<BTNode> newList = new ArrayList<>();
            newList.add(currNode);
            columnsMap.put(currColumn, newList);
        }
    }

    public static void main(String[] args) {
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
        for (BTNode e : result) System.out.print(e.getValue() + " ");
    }

}
