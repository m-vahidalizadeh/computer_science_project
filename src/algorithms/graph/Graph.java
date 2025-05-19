package algorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents a graph (can be a tree or a general graph) and provides algorithms
 * for finding shortest paths, traversals, LCA (for tree structures), and MSTs.
 * The graph is stored internally using an adjacency list.
 */
public class Graph { // Renamed from Tree to Graph

    private final int numVertices;
    private final List<List<Edge>> adj;

    // --- LCA specific members ---
    // These members are relevant when the graph instance is a tree and LCA is used.
    /**
     * Depth of each node from the root (typically node 0 for LCA).
     * Populated during LCA preprocessing. Only meaningful for tree structures.
     */
    private int[] depthLCA;
    /**
     * Sparse table for LCA: lcaParent[u][k] is the 2^k-th ancestor of node u.
     * Populated during LCA preprocessing. Only meaningful for tree structures.
     */
    private int[][] lcaParent;
    /**
     * Maximum k such that 2^k is less than or equal to numVertices.
     * Used for sizing the lcaParent table. Only meaningful for tree structures.
     */
    private int maxLogN_LCA;
    /**
     * Flag to indicate if LCA preprocessing has been completed.
     */
    private boolean lcaPreprocessed = false;
    /**
     * The root used for LCA calculations, typically 0.
     * Assumes the graph component containing this root is a tree for LCA.
     */
    private static final int LCA_ROOT = 0;


    /**
     * Represents an edge in the graph, connecting a source vertex to a destination vertex
     * with a specific weight.
     */
    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.from = -1;
            this.to = to;
            this.weight = weight;
        }

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            if (from != -1) {
                return from + "->" + to + "(w:" + weight + ")";
            }
            return "->(" + to + ", w:" + weight + ")";
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    /**
     * Represents a node and its distance from a source, used in Dijkstra's algorithm's priority queue.
     */
    static class NodeDistance implements Comparable<NodeDistance> {
        int node;
        int distance;

        public NodeDistance(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this.distance, other.distance);
        }

        @Override
        public String toString() {
            return "NodeDistance{" +
                    "node=" + node +
                    ", distance=" + distance +
                    '}';
        }
    }

    /**
     * Constructs a Graph with a given number of vertices and its adjacency list.
     *
     * @param numVertices The total number of vertices in the graph (0 to numVertices-1).
     * @param adjList The adjacency list representing the graph.
     * @throws IllegalArgumentException if numVertices is negative or adjList size doesn't match.
     */
    public Graph(int numVertices, List<List<Edge>> adjList) {
        if (numVertices < 0) {
            throw new IllegalArgumentException("Number of vertices cannot be negative.");
        }
        if (adjList == null || adjList.size() != numVertices) {
            throw new IllegalArgumentException("Adjacency list size must match the number of vertices.");
        }
        this.numVertices = numVertices;
        this.adj = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.adj.add(new ArrayList<>(adjList.get(i) == null ? Collections.emptyList() : adjList.get(i)));
        }
    }

    public int getNumVertices() {
        return numVertices;
    }

    public List<List<Edge>> getAdj() {
        List<List<Edge>> defensiveCopy = new ArrayList<>(numVertices);
        for(List<Edge> edgesFromNode : this.adj) {
            defensiveCopy.add(new ArrayList<>(edgesFromNode));
        }
        return defensiveCopy;
    }

    public static Graph createFromEdgeList(List<int[]> edgeList, boolean isDirected) {
        if (edgeList == null) {
            throw new IllegalArgumentException("Edge list cannot be null.");
        }

        int maxVertexId = -1;
        for (int[] edge : edgeList) {
            if (edge == null || edge.length < 2) {
                throw new IllegalArgumentException("Malformed edge in edge list: " + Arrays.toString(edge));
            }
            maxVertexId = Math.max(maxVertexId, edge[0]);
            maxVertexId = Math.max(maxVertexId, edge[1]);
        }

        int numVertices = (maxVertexId == -1) ? 0 : maxVertexId + 1;
        List<List<Edge>> adjList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edgeData : edgeList) {
            int u = edgeData[0];
            int v = edgeData[1];
            int weight = (edgeData.length >= 3) ? edgeData[2] : 1;

            if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
                throw new IllegalArgumentException("Edge " + Arrays.toString(edgeData) + " contains out-of-bounds vertex for inferred numVertices=" + numVertices);
            }

            adjList.get(u).add(new Edge(u, v, weight));
            if (!isDirected && u != v) {
                adjList.get(v).add(new Edge(v, u, weight));
            }
        }
        return new Graph(numVertices, adjList); // Changed to return Graph
    }

    public static Graph createFromAdjacencyMatrix(int[][] matrix, int noEdgeSentinel) { // Changed to return Graph
        if (matrix == null || matrix.length == 0) {
            return new Graph(0, new ArrayList<>());
        }
        int numVertices = matrix.length;
        for (int[] row : matrix) {
            if (row == null || row.length != numVertices) {
                throw new IllegalArgumentException("Adjacency matrix must be square.");
            }
        }

        List<List<Edge>> adjList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrix[i][j] != noEdgeSentinel) {
                    adjList.get(i).add(new Edge(i, j, matrix[i][j]));
                }
            }
        }
        return new Graph(numVertices, adjList); // Changed to return Graph
    }

    public List<Integer> dfs(int startNode) {
        if (startNode < 0 || startNode >= numVertices) {
            throw new IllegalArgumentException("Start node for DFS is out of bounds.");
        }
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        dfsRecursive(startNode, visited, result);
        return result;
    }

    private void dfsRecursive(int u, boolean[] visited, List<Integer> result) {
        visited[u] = true;
        result.add(u);
        for (Edge edge : adj.get(u)) {
            if (!visited[edge.to]) {
                dfsRecursive(edge.to, visited, result);
            }
        }
    }

    public List<Integer> bfs(int startNode) {
        if (startNode < 0 || startNode >= numVertices) {
            throw new IllegalArgumentException("Start node for BFS is out of bounds.");
        }
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();

        visited[startNode] = true;
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(u);
            for (Edge edge : adj.get(u)) {
                if (!visited[edge.to]) {
                    visited[edge.to] = true;
                    queue.offer(edge.to);
                }
            }
        }
        return result;
    }

    /**
     * Preprocesses the graph for efficient LCA queries, assuming the relevant
     * part of the graph (component containing LCA_ROOT) is a tree.
     * This method should be called once before any {@link #findLCA(int, int)} calls.
     *
     * @throws IllegalStateException if the graph has no vertices or LCA_ROOT is invalid.
     */
    public void preprocessLCA() {
        if (numVertices == 0) {
            this.lcaPreprocessed = false;
            // Consider logging a warning or throwing if LCA is expected on an empty graph
            return;
        }
        if (LCA_ROOT < 0 || LCA_ROOT >= numVertices) {
            throw new IllegalStateException("LCA_ROOT is out of bounds. Cannot preprocess LCA.");
        }

        // Ensure numVertices is at least 1 for Math.log
        this.maxLogN_LCA = (numVertices == 1) ? 0 : (int) (Math.log(numVertices) / Math.log(2));
        this.depthLCA = new int[numVertices];
        this.lcaParent = new int[numVertices][maxLogN_LCA + 1];
        for (int[] row : lcaParent) {
            Arrays.fill(row, -1);
        }

        lcaDfs(LCA_ROOT, -1, 0);

        for (int k = 1; k <= maxLogN_LCA; k++) {
            for (int u = 0; u < numVertices; u++) {
                if (lcaParent[u][k - 1] != -1) {
                    lcaParent[u][k] = lcaParent[lcaParent[u][k - 1]][k - 1];
                }
            }
        }
        this.lcaPreprocessed = true;
    }

    private void lcaDfs(int u, int p, int d) {
        depthLCA[u] = d;
        lcaParent[u][0] = p;
        for (Edge edge : adj.get(u)) {
            int v = edge.to;
            if (v != p) {
                lcaDfs(v, u, d + 1);
            }
        }
    }

    /**
     * Finds the Lowest Common Ancestor (LCA) of two nodes u and v,
     * assuming the graph has been preprocessed and represents a tree structure
     * for the relevant nodes.
     * {@link #preprocessLCA()} must be called before using this method.
     *
     * @param u The first node.
     * @param v The second node.
     * @return The LCA of u and v, or -1 if issues occur.
     * @throws IllegalStateException if LCA preprocessing has not been performed.
     * @throws IllegalArgumentException if u or v are out of bounds.
     */
    public int findLCA(int u, int v) {
        if (!lcaPreprocessed) {
            throw new IllegalStateException("LCA not preprocessed. Call preprocessLCA() first.");
        }
        if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
            throw new IllegalArgumentException("Nodes for LCA are out of bounds.");
        }

        if (depthLCA[u] < depthLCA[v]) {
            int temp = u; u = v; v = temp;
        }

        for (int k = maxLogN_LCA; k >= 0; k--) {
            if (lcaParent[u][k] != -1 && (depthLCA[u] - (1 << k) >= depthLCA[v])) {
                u = lcaParent[u][k];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = maxLogN_LCA; k >= 0; k--) {
            if (lcaParent[u][k] != -1 && lcaParent[v][k] != -1 && lcaParent[u][k] != lcaParent[v][k]) {
                u = lcaParent[u][k];
                v = lcaParent[v][k];
            }
        }
        return lcaParent[u][0];
    }

    public Map<Integer, Integer> dijkstra(int src) {
        if (src < 0 || src >= numVertices) {
            throw new IllegalArgumentException("Source vertex " + src + " is out of bounds for numVertices=" + numVertices);
        }
        if (numVertices == 0) {
            return new HashMap<>();
        }

        int[] dist = new int[numVertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
        pq.add(new NodeDistance(src, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            int u = current.node;
            int d = current.distance;

            if (d > dist[u]) {
                continue;
            }

            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new NodeDistance(v, dist[v]));
                }
            }
        }

        Map<Integer, Integer> shortestPaths = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            shortestPaths.put(i, dist[i]);
        }
        return shortestPaths;
    }

    public Map<Integer, Integer> bellmanFord(int src) {
        if (src < 0 || src >= numVertices) {
            throw new IllegalArgumentException("Source vertex " + src + " is out of bounds for numVertices=" + numVertices);
        }
        if (numVertices == 0) {
            return new HashMap<>();
        }

        int[] dist = new int[numVertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 1; i < numVertices; ++i) {
            for (int u = 0; u < numVertices; ++u) {
                if (dist[u] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Edge edge : adj.get(u)) {
                    int v = edge.to;
                    int weight = edge.weight;
                    if (dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                    }
                }
            }
        }

        for (int i = 0; i < numVertices; ++i) {
            boolean changedInThisPass = false;
            for (int u = 0; u < numVertices; ++u) {
                if (dist[u] == Integer.MAX_VALUE) continue;

                for (Edge edge : adj.get(u)) {
                    int v = edge.to;
                    int weight = edge.weight;
                    if (dist[u] == Integer.MIN_VALUE) {
                        if (dist[v] != Integer.MIN_VALUE) {
                            dist[v] = Integer.MIN_VALUE;
                            changedInThisPass = true;
                        }
                    } else if (dist[u] + weight < dist[v]) {
                        dist[v] = Integer.MIN_VALUE;
                        changedInThisPass = true;
                    }
                }
            }
            if (!changedInThisPass && i > 0) break;
        }

        Map<Integer, Integer> shortestPaths = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            shortestPaths.put(i, dist[i]);
        }
        return shortestPaths;
    }

    public List<Edge> primMST() {
        if (numVertices == 0) {
            return new ArrayList<>();
        }

        boolean[] inMST = new boolean[numVertices];
        int[] key = new int[numVertices];
        Arrays.fill(key, Integer.MAX_VALUE);
        Edge[] mstParentEdge = new Edge[numVertices];

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();

        int startNode = 0;
        key[startNode] = 0;
        pq.add(new NodeDistance(startNode, 0));

        List<Edge> mstEdges = new ArrayList<>();
        int verticesInMST = 0; // Changed from edgesInMST to verticesInMST for clarity

        while (!pq.isEmpty() && verticesInMST < numVertices) {
            NodeDistance current = pq.poll();
            int u = current.node;

            if (inMST[u]) {
                continue;
            }

            inMST[u] = true;
            // Add the edge that connected u to the MST (if u is not the startNode)
            if (mstParentEdge[u] != null) { // This check is correct
                mstEdges.add(mstParentEdge[u]);
            }
            verticesInMST++; // Increment when vertex is finalized in MST

            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                if (!inMST[v] && weight < key[v]) {
                    key[v] = weight;
                    pq.add(new NodeDistance(v, key[v]));
                    mstParentEdge[v] = new Edge(u, v, weight);
                }
            }
        }
        // An MST for a connected graph of V vertices has V-1 edges.
        // If the graph is disconnected, this will return an MST for the component of startNode.
        // The loop condition `verticesInMST < numVertices` ensures we don't over-process if disconnected.
        return mstEdges;
    }

    private static class DSU {
        int[] parent;
        int[] rank;

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (rank[rootI] < rank[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (rank[rootI] > rank[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    rank[rootI]++;
                }
                return true;
            }
            return false;
        }
    }

    public List<Edge> kruskalMST() {
        if (numVertices == 0) {
            return new ArrayList<>();
        }

        List<Edge> allEdges = new ArrayList<>();
        Set<String> addedEdges = new HashSet<>();

        for (int u = 0; u < numVertices; u++) {
            for (Edge edge : adj.get(u)) {
                // For Kruskal's, we need the 'from' field to be correctly set for the DSU.
                // The current Edge constructor in adj list might only have 'to' and 'weight'.
                // It's better if edges in `adj` store their source, or we reconstruct them here.
                // Assuming `edge.from` is correctly populated by `createFromEdgeList` or `createFromAdjacencyMatrix`
                // If not, `edge.from` would be `u` here.
                int fromNode = (edge.from != -1) ? edge.from : u; // Ensure 'from' is set
                int toNode = edge.to;

                String edgeKey = Math.min(fromNode, toNode) + "-" + Math.max(fromNode, toNode);
                if (!addedEdges.contains(edgeKey)) {
                    allEdges.add(new Edge(fromNode, toNode, edge.weight)); // Use the reconstructed/verified edge
                    addedEdges.add(edgeKey);
                }
            }
        }

        Collections.sort(allEdges);

        List<Edge> mstEdges = new ArrayList<>();
        DSU dsu = new DSU(numVertices);
        int edgeCount = 0;

        for (Edge edge : allEdges) {
            if (edgeCount == numVertices - 1 && numVertices > 0) { // Optimization for connected graph
                break;
            }
            if (dsu.union(edge.from, edge.to)) {
                mstEdges.add(edge);
                edgeCount++;
            }
        }
        return mstEdges;
    }

    public void printGraph() {
        System.out.println("Graph (Adjacency List, " + numVertices + " vertices):");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vertex " + i + ": ");
            if (adj.get(i) == null || adj.get(i).isEmpty()) {
                System.out.print("-> null");
            } else {
                for (Edge edge : adj.get(i)) {
                    System.out.print(edge + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Example 1: Basic Graph (Undirected) ---");
        List<int[]> edgeList1 = new ArrayList<>();
        edgeList1.add(new int[]{0, 1, 4}); edgeList1.add(new int[]{0, 2, 1});
        edgeList1.add(new int[]{1, 2, 2}); edgeList1.add(new int[]{1, 3, 5});
        edgeList1.add(new int[]{2, 3, 8}); edgeList1.add(new int[]{2, 4, 3});
        edgeList1.add(new int[]{3, 4, 2});

        Graph graph1 = Graph.createFromEdgeList(edgeList1, false); // Use Graph
        graph1.printGraph();

        System.out.println("\nDFS from 0: " + graph1.dfs(0));
        System.out.println("BFS from 0: " + graph1.bfs(0));

        System.out.println("\nDijkstra from source 0:");
        graph1.dijkstra(0).forEach((n, d) -> System.out.println("Node " + n + ": " + (d == Integer.MAX_VALUE ? "INF" : d)));

        System.out.println("\nBellman-Ford from source 0:");
        graph1.bellmanFord(0).forEach((n, d) -> System.out.println("Node " + n + ": " + (d == Integer.MAX_VALUE ? "INF" : (d == Integer.MIN_VALUE ? "-INF" : d))));

        System.out.println("\nPrim's MST:");
        List<Edge> primResult = graph1.primMST();
        primResult.forEach(System.out::println);
        System.out.println("Prim's MST Total Weight: " + primResult.stream().mapToInt(e -> e.weight).sum());

        System.out.println("\nKruskal's MST:");
        List<Edge> kruskalResult = graph1.kruskalMST();
        kruskalResult.forEach(System.out::println);
        System.out.println("Kruskal's MST Total Weight: " + kruskalResult.stream().mapToInt(e -> e.weight).sum());

        System.out.println("\n--- Example 2: Clear Tree for LCA ---");
        List<int[]> treeEdges = new ArrayList<>();
        treeEdges.add(new int[]{0, 1, 1});
        treeEdges.add(new int[]{0, 2, 1});
        treeEdges.add(new int[]{1, 3, 1});
        treeEdges.add(new int[]{1, 4, 1});
        treeEdges.add(new int[]{2, 5, 1});
        treeEdges.add(new int[]{2, 6, 1});
        treeEdges.add(new int[]{4, 7, 1});

        Graph lcaGraph = Graph.createFromEdgeList(treeEdges, false); // Use Graph
        lcaGraph.printGraph();
        System.out.println("\nLCA Preprocessing for lcaGraph (rooted at 0)...");
        lcaGraph.preprocessLCA();
        System.out.println("LCA(3, 7): " + lcaGraph.findLCA(3, 7) + " (Expected: 1)");
        System.out.println("LCA(4, 5): " + lcaGraph.findLCA(4, 5) + " (Expected: 0)");
        System.out.println("LCA(5, 6): " + lcaGraph.findLCA(5, 6) + " (Expected: 2)");
        System.out.println("LCA(0, 7): " + lcaGraph.findLCA(0, 7) + " (Expected: 0)");

        System.out.println("\n--- Example 3: Bellman-Ford with Negative Cycle ---");
        List<int[]> edgeListNegCycle = new ArrayList<>();
        edgeListNegCycle.add(new int[]{0, 1, 1});
        edgeListNegCycle.add(new int[]{1, 2, -2});
        edgeListNegCycle.add(new int[]{2, 0, -3});
        edgeListNegCycle.add(new int[]{0, 3, 5});

        Graph graphNegCycle = Graph.createFromEdgeList(edgeListNegCycle, true); // Use Graph
        graphNegCycle.printGraph();
        System.out.println("\nBellman-Ford from source 0 (Negative Cycle):");
        graphNegCycle.bellmanFord(0).forEach((n, d) -> System.out.println("Node " + n + ": " + (d == Integer.MAX_VALUE ? "INF" : (d == Integer.MIN_VALUE ? "-INF (Neg Cycle)" : d))));
    }
}
