package data_structures.graph;

import java.util.*;

/**
 * Undirected graph.
 */
public class UndirectedGraph extends Graph {

    public void addEdge(Vertex v1, Vertex v2) {
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }

    public void removeEdge(Vertex v1, Vertex v2) {
        List<Vertex> v1Adj = adjVertices.get(v1);
        List<Vertex> v2Adj = adjVertices.get(v2);
        if (v1Adj != null) v1Adj.remove(v2);
        if (v2Adj != null) v2Adj.remove(v1);
    }
}
