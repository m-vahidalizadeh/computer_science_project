package base;

import java.util.*;

/**
 * Undirected graph.
 */
public class UndirectedGraph {
    public Map<Vertex, List<Vertex>> adjVertices;

    public UndirectedGraph() {
        adjVertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        adjVertices.putIfAbsent(v, new ArrayList<>());
    }

    public void removeVertex(Vertex v) {
        adjVertices.values().forEach(l -> l.remove(v));
        adjVertices.remove(v);
    }

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

    public List<Vertex> dfs(Vertex s) {
        List<Vertex> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> st = new Stack<>();
        st.push(s);
        while (!st.isEmpty()) {
            Vertex stTop = st.pop();
            if (visited.contains(stTop)) continue;
            result.add(stTop);
            visited.add(stTop);
            List<Vertex> children = adjVertices.get(stTop);
            for (Vertex c : children) st.push(c);
        }
        return result;
    }

    public List<Vertex> bfs(Vertex s) {
        List<Vertex> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> q = new LinkedList<>();
        q.add(s);
        while (!q.isEmpty()) {
            Vertex qFirst = q.poll();
            if (visited.contains(qFirst)) continue;
            result.add(qFirst);
            visited.add(qFirst);
            List<Vertex> children = adjVertices.get(qFirst);
            for (Vertex c : children) q.add(c);
        }
        return result;
    }
}
