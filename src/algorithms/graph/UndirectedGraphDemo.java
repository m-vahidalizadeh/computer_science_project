package algorithms.graph;

import base.UndirectedGraph;
import base.Vertex;

import java.util.List;

public class UndirectedGraphDemo {

    public static void main(String[] args) {
        // Construct the undirected graph
        UndirectedGraph ug = new UndirectedGraph();
        Vertex a = new Vertex("a");
        Vertex b = new Vertex("b");
        Vertex c = new Vertex("c");
        Vertex d = new Vertex("d");
        Vertex e = new Vertex("e");
        ug.addVertex(a);
        ug.addVertex(b);
        ug.addVertex(c);
        ug.addVertex(d);
        ug.addVertex(e);
        ug.addEdge(a, b);
        ug.addEdge(a, d);
        ug.addEdge(b, c);
        ug.addEdge(d, c);
        ug.addEdge(c, e);
        // DFS
        System.out.print("DFS: ");
        printList(ug.dfs(a));
        // BFS
        System.out.print("BFS: ");
        printList(ug.bfs(a));
    }

    private static void printList(List<Vertex> l) {
        for (Vertex v : l) {
            System.out.print(v.label + " ");
        }
        System.out.println();
    }

}
