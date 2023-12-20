import java.util.*;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Nakul Kuttua
 * @version 1.0
 * @userid nkuttua3
 * @GTID 903520821
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist");
        }
        List<Vertex<T>> list = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        queue.add(start);
        list.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.peek();
            if (curr == null) {
                throw new IllegalArgumentException("curr in queue is null");
            }
            List<VertexDistance<T>> adjusted = adjList.get(curr);
            for (VertexDistance<T> now : adjusted) {
                if (!list.contains(now.getVertex())) {
                    queue.add(now.getVertex());
                    list.add(now.getVertex());
                }
            }
            queue.remove();
        }
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Set<Vertex<T>> vertices = new HashSet<>();
        List<Vertex<T>> finalList = new ArrayList<>();

        dfs(start, vertices, finalList, adjList);
        return finalList;
    }

    /**
     * Private recursive method for dfs
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param vertices the visited vertices
     * @param result the list of vertices to keep track
     * @param adjList the list of adjacent list
     */
    private static <T> void dfs(Vertex<T> start, Set<Vertex<T>> vertices, List<Vertex<T>> result,
                                Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        vertices.add(start);
        result.add(start);
        for (VertexDistance<T> vd : adjList.get(start)) {
            Vertex<T> vertex = vd.getVertex();
            if (!vertices.contains(vertex)) {
                dfs(vertex, vertices, result, adjList);

            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjusted = graph.getAdjList();
        Map<Vertex<T>, Integer> vertices = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pQueue = new PriorityQueue<>();

        for (Vertex<T> vertex : adjusted.keySet()) {
            if (vertex.equals(start)) {
                vertices.put(vertex, 0);
            } else {
                vertices.put(vertex, Integer.MAX_VALUE);
            }
        }
        pQueue.add(new VertexDistance<>(start, 0));
        while (!pQueue.isEmpty()) {
            VertexDistance<T> vd = pQueue.remove();
            for (VertexDistance<T> vertexDistance : adjusted.get(vd.getVertex())) {
                int newDist = vertexDistance.getDistance() + vd.getDistance();
                if (vertices.get(vertexDistance.getVertex()) > newDist) {
                    vertices.put(vertexDistance.getVertex(), newDist);
                    pQueue.add(new VertexDistance<>(vertexDistance.getVertex(), newDist));
                }
            }
        }
        return vertices;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        DisjointSet<Vertex<T>> djSet = new DisjointSet<>();
        Set<Edge<T>> MST = new HashSet<>();
        PriorityQueue<Edge<T>> pQueue = new PriorityQueue<>();
        pQueue.addAll(graph.getEdges());
        while (!pQueue.isEmpty() && MST.size() < graph.getEdges().size() - 1) {
            Edge<T> edge = pQueue.poll();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            if (djSet.find(u) != djSet.find(v)) {
                djSet.union(djSet.find(u), djSet.find(v));
                MST.add(edge);
                Edge<T> e = new Edge<>(v, u, edge.getWeight());
                MST.add(e);
            }
        }
        if (MST.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return MST;

    }
}
