package data_structures.graph;

import java.util.*;

public class Graph {
    public Map<Vertex, List<Vertex>> adjVertices;

    public Graph() {
        adjVertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        adjVertices.putIfAbsent(v, new ArrayList<>());
    }

    public void removeVertex(Vertex v) {
        adjVertices.values().forEach(l -> l.remove(v));
        adjVertices.remove(v);
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
