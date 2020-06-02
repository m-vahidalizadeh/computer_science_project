package algorithms.graph;

import data_structures.graph.DirectedGraph;
import data_structures.graph.Vertex;

import java.util.List;

public class DirectedGraphDemo {
    public static void main(String[] args) {
        DirectedGraph dg = new DirectedGraph();
        Vertex a = new Vertex("a");
        Vertex b = new Vertex("b");
        Vertex c = new Vertex("c");
        Vertex d = new Vertex("d");
        Vertex e = new Vertex("e");
        dg.addVertex(a);
        dg.addVertex(b);
        dg.addVertex(c);
        dg.addVertex(d);
        dg.addVertex(e);
        dg.addEdge(a, b);
        dg.addEdge(b, e);
        dg.addEdge(a, c);
        dg.addEdge(e, c);
        dg.addEdge(c, d);
        System.out.print("DFS: ");
        printList(dg.dfs(a));
        System.out.print("BFS: ");
        printList(dg.bfs(a));
    }

    private static void printList(List<Vertex> l) {
        for (Vertex v : l) {
            System.out.print(v.label + " ");
        }
        System.out.println();
    }
}
