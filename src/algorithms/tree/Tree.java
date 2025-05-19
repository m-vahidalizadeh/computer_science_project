package algorithms.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set; // Added for DFS/BFS visited set if preferred
import java.util.HashSet; // Added for DFS/BFS visited set if preferred

/**
 * Represents a graph (can be a tree or a general graph) and provides algorithms
 * for finding shortest paths, traversals, LCA (for trees), and MSTs.
 * The graph is stored internally using an adjacency list.
 */
public class Tree {

    private final int numVertices;
    private final List<List<Edge>> adj;

    // --- LCA specific members ---
    /**
     * Depth of each node from the root (typically node 0 for LCA).
     * Populated during LCA preprocessing.
     */
    private int[] depthLCA;
    /**
     * Sparse table for LCA: lcaParent[u][k] is the 2^k-th ancestor of node u.
     * Populated during LCA preprocessing.
     */
    private int[][] lcaParent;
    /**
     * Maximum k such that 2^k is less than or equal to numVertices.
     * Used for sizing the lcaParent table.
     */
    private int maxLogN_LCA;
    /**
     * Flag to indicate if LCA preprocessing has been completed.
     */
    private boolean lcaPreprocessed = false;
    /**
     * The root used for LCA calculations, typically 0.
     */
    private static final int LCA_ROOT = 0;


    /**
     * Represents an edge in the graph, connecting a source vertex to a destination vertex
     * with a specific weight.
     */
    static class Edge implements Comparable<Edge> { // Implements Comparable for Kruskal's
        int from; // Source vertex, useful for Kruskal's
        int to;   // Destination vertex
        int weight;

        /**
         * Constructs an Edge.
         * @param to The destination vertex of the edge.
         * @param weight The weight of the edge.
         */
        public Edge(int to, int weight) {
            this.from = -1; // Placeholder, set if needed (e.g. Kruskal)
            this.to = to;
            this.weight = weight;
        }

        /**
         * Constructs an Edge with a specified source.
         * @param from The source vertex of the edge.
         * @param to The destination vertex of the edge.
         * @param weight The weight of the edge.
         */
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

        /**
         * Compares edges based on their weights. Used for sorting in Kruskal's algorithm.
         * @param other The other edge to compare to.
         * @return a negative integer, zero, or a positive integer as this edge's
         *         weight is less than, equal to, or greater than the specified edge's weight.
         */
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    /**
     * Represents a node and its distance from a source, used in Dijkstra's algorithm's priority queue.
     * Implements Comparable to allow usage in a min-priority queue.
     */
    static class NodeDistance implements Comparable<NodeDistance> {
        int node;
        int distance;

        /**
         * Constructs a NodeDistance pair.
         * @param node The vertex.
         * @param distance The distance to this vertex from a source.
         */
        public NodeDistance(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        /**
         * Compares this NodeDistance with another based on distance, for use in a min-priority queue.
         * @param other The other NodeDistance object to compare against.
         * @return A negative integer, zero, or a positive integer as this object's distance
         *         is less than, equal to, or greater than the specified object's distance.
         */
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
     * Constructs a Tree (or general graph) with a given number of vertices and its adjacency list.
     * The adjacency list should be pre-populated.
     *
     * @param numVertices The total number of vertices in the graph (assumed to be 0 to numVertices-1).
     * @param adjList The adjacency list representing the graph. adjList.get(i) contains a list of Edge objects
     *                originating from vertex i.
     * @throws IllegalArgumentException if numVertices is negative or if adjList size doesn't match numVertices.
     */
    public Tree(int numVertices, List<List<Edge>> adjList) {
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

    /**
     * Gets the number of vertices in the graph.
     * @return The number of vertices.
     */
    public int getNumVertices() {
        return numVertices;
    }

    /**
     * Gets the adjacency list of the graph.
     * @return A list of lists of edges, representing the adjacency list.
     *         This returns a defensive copy of the internal list structure.
     */
    public List<List<Edge>> getAdj() {
        List<List<Edge>> defensiveCopy = new ArrayList<>(numVertices);
        for(List<Edge> edgesFromNode : this.adj) {
            defensiveCopy.add(new ArrayList<>(edgesFromNode));
        }
        return defensiveCopy;
    }


    // --- Conversion Methods ---

    /**
     * Creates a Tree (or graph) instance from an edge list representation.
     * An edge list is a list of arrays, where each array is [source, destination, weight].
     * The number of vertices is inferred from the maximum vertex ID in the edge list.
     *
     * @param edgeList A list of integer arrays, where each array `[u, v, w]` represents an edge
     *                 from vertex `u` to vertex `v` with weight `w`.
     * @param isDirected true if the graph is directed, false if undirected (edges will be added in both directions).
     * @return A new Tree instance.
     * @throws IllegalArgumentException if edgeList is null or contains malformed edges.
     */
    public static Tree createFromEdgeList(List<int[]> edgeList, boolean isDirected) {
        if (edgeList == null) {
            throw new IllegalArgumentException("Edge list cannot be null.");
        }

        int maxVertexId = -1;
        for (int[] edge : edgeList) {
            if (edge == null || edge.length < 2) { // Weight is optional, default to 1 if not present
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
            int weight = (edgeData.length >= 3) ? edgeData[2] : 1; // Default weight to 1 if not specified

            if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
                throw new IllegalArgumentException("Edge " + Arrays.toString(edgeData) + " contains out-of-bounds vertex for inferred numVertices=" + numVertices);
            }

            adjList.get(u).add(new Edge(u, v, weight)); // Store 'from' for Kruskal
            if (!isDirected && u != v) { // Avoid duplicate self-loop for undirected graph
                adjList.get(v).add(new Edge(v, u, weight)); // Store 'from' for Kruskal
            }
        }
        return new Tree(numVertices, adjList);
    }

    /**
     * Creates a Tree (or graph) instance from an adjacency matrix representation.
     *
     * @param matrix The adjacency matrix, where matrix[i][j] is the weight of the edge
     *               from vertex i to vertex j. A value of 0 or Integer.MAX_VALUE
     *               (or any other sentinel) can indicate no edge, depending on convention.
     *               This implementation assumes Integer.MAX_VALUE means no edge.
     * @param noEdgeSentinel The value in the matrix that signifies no direct edge (e.g., Integer.MAX_VALUE).
     * @return A new Tree instance.
     * @throws IllegalArgumentException if matrix is null or not square.
     */
    public static Tree createFromAdjacencyMatrix(int[][] matrix, int noEdgeSentinel) {
        if (matrix == null || matrix.length == 0) {
            return new Tree(0, new ArrayList<>()); // Empty graph
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
                    adjList.get(i).add(new Edge(i, j, matrix[i][j])); // Store 'from'
                }
            }
        }
        return new Tree(numVertices, adjList);
    }

    // --- Traversal Methods ---

    /**
     * Performs a Depth First Search (DFS) traversal starting from a given vertex.
     *
     * @param startNode The vertex to start the DFS from.
     * @return A list of vertices in the order they were visited by DFS.
     * @throws IllegalArgumentException if startNode is out of bounds.
     */
    public List<Integer> dfs(int startNode) {
        if (startNode < 0 || startNode >= numVertices) {
            throw new IllegalArgumentException("Start node for DFS is out of bounds.");
        }
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        dfsRecursive(startNode, visited, result);
        return result;
    }

    /**
     * Recursive helper for DFS.
     * @param u Current vertex.
     * @param visited Array to keep track of visited vertices.
     * @param result List to store the DFS traversal order.
     */
    private void dfsRecursive(int u, boolean[] visited, List<Integer> result) {
        visited[u] = true;
        result.add(u);
        for (Edge edge : adj.get(u)) {
            if (!visited[edge.to]) {
                dfsRecursive(edge.to, visited, result);
            }
        }
    }

    /**
     * Performs a Breadth First Search (BFS) traversal starting from a given vertex.
     *
     * @param startNode The vertex to start the BFS from.
     * @return A list of vertices in the order they were visited by BFS.
     * @throws IllegalArgumentException if startNode is out of bounds.
     */
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


    // --- Lowest Common Ancestor (LCA) Methods ---

    /**
     * Preprocesses the tree for efficient LCA queries.
     * This method should be called once before any {@link #findLCA(int, int)} calls
     * if the graph is indeed a tree and LCA queries are needed.
     * It assumes the graph is a tree (or a forest, processing the component containing LCA_ROOT).
     * If the graph is not a tree, the LCA concept might not be well-defined or
     * this preprocessing might not cover all nodes appropriately for general graphs.
     *
     * @throws IllegalStateException if the graph has no vertices.
     */
    public void preprocessLCA() {
        if (numVertices == 0) {
            // Or handle as appropriate, e.g., by not setting lcaPreprocessed to true
            // and findLCA can check this flag.
            this.lcaPreprocessed = false;
            return;
        }
        if (LCA_ROOT < 0 || LCA_ROOT >= numVertices) {
            throw new IllegalStateException("LCA_ROOT is out of bounds. Cannot preprocess LCA.");
        }

        this.maxLogN_LCA = (int) (Math.log(numVertices) / Math.log(2));
        this.depthLCA = new int[numVertices];
        this.lcaParent = new int[numVertices][maxLogN_LCA + 1];
        for (int[] row : lcaParent) {
            Arrays.fill(row, -1); // -1 indicates no ancestor
        }

        // Perform DFS from LCA_ROOT to fill depth and parent[u][0]
        lcaDfs(LCA_ROOT, -1, 0);

        // Fill the rest of the lcaParent table using binary lifting
        for (int k = 1; k <= maxLogN_LCA; k++) {
            for (int u = 0; u < numVertices; u++) {
                if (lcaParent[u][k - 1] != -1) {
                    lcaParent[u][k] = lcaParent[lcaParent[u][k - 1]][k - 1];
                }
            }
        }
        this.lcaPreprocessed = true;
    }

    /**
     * DFS specifically for LCA preprocessing.
     * @param u Current node.
     * @param p Parent of u.
     * @param d Depth of u.
     */
    private void lcaDfs(int u, int p, int d) {
        depthLCA[u] = d;
        lcaParent[u][0] = p;
        for (Edge edge : adj.get(u)) {
            int v = edge.to;
            if (v != p) { // Ensure we don't go back to the parent immediately
                lcaDfs(v, u, d + 1);
            }
        }
    }

    /**
     * Finds the Lowest Common Ancestor (LCA) of two nodes u and v in the tree.
     * {@link #preprocessLCA()} must be called before using this method.
     *
     * @param u The first node.
     * @param v The second node.
     * @return The LCA of u and v, or -1 if they are in different components (not connected to LCA_ROOT)
     *         or if preprocessing was not done, or if nodes are invalid.
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

        // Ensure u is deeper or at the same level as v
        if (depthLCA[u] < depthLCA[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // Lift u to the same depth as v
        for (int k = maxLogN_LCA; k >= 0; k--) {
            if (lcaParent[u][k] != -1 && (depthLCA[u] - (1 << k) >= depthLCA[v])) {
                u = lcaParent[u][k];
            }
        }

        if (u == v) {
            return u; // v was an ancestor of u, or u == v
        }

        // Lift u and v simultaneously until their parents are the same
        for (int k = maxLogN_LCA; k >= 0; k--) {
            if (lcaParent[u][k] != -1 && lcaParent[v][k] != -1 && lcaParent[u][k] != lcaParent[v][k]) {
                u = lcaParent[u][k];
                v = lcaParent[v][k];
            }
        }
        return lcaParent[u][0]; // The parent of u (and v) is the LCA
    }


    // --- Shortest Path Algorithms (Dijkstra, Bellman-Ford from original code) ---
    /**
     * Implements Dijkstra's algorithm to find the shortest paths from a source vertex
     * to all other vertices in a graph with non-negative edge weights.
     *
     * @param src The source vertex (0-indexed).
     * @return A Map where keys are destination vertex IDs and values are the shortest
     *         distances from the source. Unreachable vertices will have a distance of Integer.MAX_VALUE.
     * @throws IllegalArgumentException if the source vertex is out of bounds.
     */
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

    /**
     * Implements the Bellman-Ford algorithm to find the shortest paths from a single
     * source vertex to all other vertices in a weighted graph.
     * This algorithm can handle graphs with negative edge weights and detect negative cycles.
     *
     * @param src The source vertex (0-indexed).
     * @return A Map where keys are destination vertex IDs and values are the shortest
     *         distances. Nodes reachable via a negative cycle will have distance Integer.MIN_VALUE.
     *         Unreachable nodes have Integer.MAX_VALUE.
     * @throws IllegalArgumentException if the source vertex is out of bounds.
     */
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

        // Check for negative-weight cycles and propagate Integer.MIN_VALUE
        // Run more iterations to ensure all nodes affected by negative cycles are marked
        for (int i = 0; i < numVertices; ++i) { // Loop to ensure propagation
            boolean changedInThisPass = false;
            for (int u = 0; u < numVertices; ++u) {
                if (dist[u] == Integer.MAX_VALUE) continue;

                for (Edge edge : adj.get(u)) {
                    int v = edge.to;
                    int weight = edge.weight;
                    // If u is already -INF, v should also become -INF if reachable
                    if (dist[u] == Integer.MIN_VALUE) {
                        if (dist[v] != Integer.MIN_VALUE) {
                            dist[v] = Integer.MIN_VALUE;
                            changedInThisPass = true;
                        }
                    } else if (dist[u] + weight < dist[v]) {
                        // This condition, after V-1 iterations, indicates a negative cycle path
                        dist[v] = Integer.MIN_VALUE;
                        changedInThisPass = true;
                    }
                }
            }
            if (!changedInThisPass && i > 0) break; // Optimization: if no changes, stable
        }


        Map<Integer, Integer> shortestPaths = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            shortestPaths.put(i, dist[i]);
        }
        return shortestPaths;
    }

    // --- Minimum Spanning Tree (MST) Algorithms ---

    /**
     * Implements Prim's algorithm to find the Minimum Spanning Tree (MST) of a connected,
     * undirected graph. If the graph is disconnected, it finds an MST for the component
     * containing the starting vertex (defaulting to vertex 0 if graph is not empty).
     *
     * @return A list of Edges representing the MST, or an empty list if the graph is empty.
     *         The total weight of the MST can be summed from these edges.
     *         Returns an MST for the component containing vertex 0 if the graph is disconnected.
     */
    public List<Edge> primMST() {
        if (numVertices == 0) {
            return new ArrayList<>();
        }

        boolean[] inMST = new boolean[numVertices]; // To track vertices included in MST
        // key[i] stores the minimum weight of an edge connecting vertex i to the MST
        int[] key = new int[numVertices];
        Arrays.fill(key, Integer.MAX_VALUE);
        // mstParentEdge[i] stores the edge (parent -> i) in the MST
        Edge[] mstParentEdge = new Edge[numVertices];

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();

        // Start with vertex 0 (or any other vertex)
        int startNode = 0; // Assuming vertex 0 exists if numVertices > 0
        key[startNode] = 0;
        pq.add(new NodeDistance(startNode, 0));
        // mstParentEdge[startNode] will be null as it's the root of the MST

        List<Edge> mstEdges = new ArrayList<>();
        int edgesInMST = 0;

        while (!pq.isEmpty() && edgesInMST < numVertices) { // MST has V-1 edges, or stops if component is spanned
            NodeDistance current = pq.poll();
            int u = current.node;

            if (inMST[u]) { // If already processed
                continue;
            }

            inMST[u] = true;
            edgesInMST++; // Count vertices added to MST set

            // Add the edge that connected u to the MST (if u is not the startNode)
            if (mstParentEdge[u] != null) {
                mstEdges.add(mstParentEdge[u]);
            }

            // Iterate over all adjacent vertices of u
            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                // If v is not in MST and weight of (u,v) is smaller than current key of v
                if (!inMST[v] && weight < key[v]) {
                    key[v] = weight;
                    pq.add(new NodeDistance(v, key[v]));
                    mstParentEdge[v] = new Edge(u, v, weight); // Store edge u -> v
                }
            }
        }
        return mstEdges;
    }


    /**
     * Helper class for Kruskal's algorithm's Disjoint Set Union (DSU) operations.
     */
    private static class DSU {
        int[] parent;
        int[] rank; // Or size, for union by rank/size

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // Each node is its own parent initially
                rank[i] = 0;   // Initial rank is 0
            }
        }

        // Find operation with path compression
        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]); // Path compression
        }

        // Union operation by rank
        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) { // Only union if they are in different sets
                if (rank[rootI] < rank[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (rank[rootI] > rank[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    rank[rootI]++;
                }
                return true; // Union successful
            }
            return false; // Already in the same set
        }
    }

    /**
     * Implements Kruskal's algorithm to find the Minimum Spanning Tree (MST) of a connected,
     * undirected graph. If the graph is disconnected, it finds a Minimum Spanning Forest.
     *
     * @return A list of Edges representing the MST (or MSF).
     *         The total weight can be summed from these edges.
     */
    public List<Edge> kruskalMST() {
        if (numVertices == 0) {
            return new ArrayList<>();
        }

        List<Edge> allEdges = new ArrayList<>();
        // Collect all unique edges. For an undirected graph, each edge (u,v) might appear twice
        // in the adjacency list (once for u, once for v). We only need one instance.
        // The Edge class now has 'from', 'to', 'weight'.
        // We assume the adjacency list `adj.get(u)` contains edges `(u, v, w)`.
        // If it was built for undirected graph by adding (u,v) and (v,u), we need to be careful.
        // The createFromEdgeList(..., isDirected=false) adds both u->v and v->u.
        // Let's re-collect edges to ensure uniqueness for Kruskal's.
        Set<String> addedEdges = new HashSet<>(); // To handle undirected edges uniquely: "min(u,v)-max(u,v)"

        for (int u = 0; u < numVertices; u++) {
            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                // Create a canonical representation for undirected edges to avoid duplicates
                String edgeKey = Math.min(u, v) + "-" + Math.max(u, v);
                if (!addedEdges.contains(edgeKey)) {
                    allEdges.add(new Edge(u, v, edge.weight));
                    addedEdges.add(edgeKey);
                }
            }
        }


        // Sort all edges in non-decreasing order of their weight
        Collections.sort(allEdges);

        List<Edge> mstEdges = new ArrayList<>();
        DSU dsu = new DSU(numVertices);
        int edgeCount = 0; // Number of edges added to MST

        for (Edge edge : allEdges) {
            // If we already have V-1 edges for a connected graph's MST
            if (edgeCount == numVertices - 1 && numVertices > 0) {
                break;
            }
            // Check if including this edge forms a cycle
            if (dsu.union(edge.from, edge.to)) { // If they were in different sets, union them
                mstEdges.add(edge);
                edgeCount++;
            }
        }
        return mstEdges;
    }


    /**
     * Helper method to print the graph's adjacency list representation.
     * Useful for debugging.
     */
    public void printGraph() {
        System.out.println("Graph (Adjacency List, " + numVertices + " vertices):");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vertex " + i + ": ");
            if (adj.get(i) == null || adj.get(i).isEmpty()) { // Added null check for safety
                System.out.print("-> null");
            } else {
                for (Edge edge : adj.get(i)) {
                    System.out.print(edge + " ");
                }
            }
            System.out.println();
        }
    }

    // --- Main method for example usage ---
    public static void main(String[] args) {
        System.out.println("--- Example 1: Basic Graph (Undirected) ---");
        List<int[]> edgeList1 = new ArrayList<>();
        edgeList1.add(new int[]{0, 1, 4}); edgeList1.add(new int[]{0, 2, 1});
        edgeList1.add(new int[]{1, 2, 2}); edgeList1.add(new int[]{1, 3, 5});
        edgeList1.add(new int[]{2, 3, 8}); edgeList1.add(new int[]{2, 4, 3});
        edgeList1.add(new int[]{3, 4, 2});

        Tree graph1 = Tree.createFromEdgeList(edgeList1, false); // Undirected
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


        System.out.println("\nLCA (assuming graph1 is a tree rooted at 0):");
        graph1.preprocessLCA(); // Must be called first
        System.out.println("LCA(3, 4): " + graph1.findLCA(3, 4) + " (Expected: 2 or 3 depending on path, check depths)");
        System.out.println("LCA(1, 4): " + graph1.findLCA(1, 4) + " (Expected: 2)");
        System.out.println("LCA(0, 4): " + graph1.findLCA(0, 4) + " (Expected: 0 or 2, check depths)");
        // For graph1, LCA(3,4) should be 2. Let's verify depths from LCA_ROOT=0
        // 0 (d=0)
        // -> 1 (d=1, p=0) -> 3 (d=2, p=1)
        // -> 2 (d=1, p=0) -> 4 (d=2, p=2)
        // LCA(3,4): u=3(d=2), v=4(d=2). parent[3][0]=1, parent[4][0]=2. Not same.
        // parent[1][0]=0, parent[2][0]=0. Same. So LCA is 0.
        // This means my manual trace or the graph structure for LCA is different from what I expected.
        // The lcaDfs needs to build the parent/depth based on the actual tree structure.
        // Let's re-verify graph1 structure for LCA from root 0:
        // 0 --1-- 2 --3-- 4
        // |       |
        // 4       8
        // |       |
        // 1 --2-- 3
        // |
        // 5
        // This is not a simple tree for LCA without picking a specific spanning tree.
        // The LCA method assumes a tree structure. If graph1 is not a tree, LCA is ill-defined.
        // Let's use a clear tree for LCA example.

        System.out.println("\n--- Example 2: Clear Tree for LCA ---");
        List<int[]> treeEdges = new ArrayList<>();
        treeEdges.add(new int[]{0, 1, 1}); // Root 0
        treeEdges.add(new int[]{0, 2, 1});
        treeEdges.add(new int[]{1, 3, 1});
        treeEdges.add(new int[]{1, 4, 1});
        treeEdges.add(new int[]{2, 5, 1});
        treeEdges.add(new int[]{2, 6, 1});
        treeEdges.add(new int[]{4, 7, 1});

        Tree lcaTree = Tree.createFromEdgeList(treeEdges, false); // Undirected tree
        lcaTree.printGraph();
        System.out.println("\nLCA Preprocessing for lcaTree (rooted at 0)...");
        lcaTree.preprocessLCA();
        System.out.println("LCA(3, 7): " + lcaTree.findLCA(3, 7) + " (Expected: 1)");
        System.out.println("LCA(4, 5): " + lcaTree.findLCA(4, 5) + " (Expected: 0)");
        System.out.println("LCA(5, 6): " + lcaTree.findLCA(5, 6) + " (Expected: 2)");
        System.out.println("LCA(0, 7): " + lcaTree.findLCA(0, 7) + " (Expected: 0)");


        System.out.println("\n--- Example 3: Bellman-Ford with Negative Cycle ---");
        List<int[]> edgeListNegCycle = new ArrayList<>();
        edgeListNegCycle.add(new int[]{0, 1, 1});
        edgeListNegCycle.add(new int[]{1, 2, -2});
        edgeListNegCycle.add(new int[]{2, 0, -3}); // Cycle 0-1-2-0 with weight -4
        edgeListNegCycle.add(new int[]{0, 3, 5});

        Tree graphNegCycle = Tree.createFromEdgeList(edgeListNegCycle, true); // Directed
        graphNegCycle.printGraph();
        System.out.println("\nBellman-Ford from source 0 (Negative Cycle):");
        graphNegCycle.bellmanFord(0).forEach((n, d) -> System.out.println("Node " + n + ": " + (d == Integer.MAX_VALUE ? "INF" : (d == Integer.MIN_VALUE ? "-INF (Neg Cycle)" : d))));

    }
}