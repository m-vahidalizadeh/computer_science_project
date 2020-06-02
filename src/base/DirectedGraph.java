package base;

import java.util.List;

public class DirectedGraph extends Graph {
    public void addEdge(Vertex v1, Vertex v2) {
        adjVertices.get(v1).add(v2);
    }

    public void removeEdge(Vertex v1, Vertex v2) {
        List<Vertex> v1Adj = adjVertices.get(v1);
        if (v1Adj != null) v1Adj.remove(v2);
    }
}
