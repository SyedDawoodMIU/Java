import java.util.*;

public class Graph {
    private int vertices;
    private LinkedList<Integer>[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination) {
        adjacencyList[source].add(destination);
        adjacencyList[destination].add(source);
    }

    public boolean isConnected(boolean bfs) {
        boolean[] visited = new boolean[vertices];

        if (bfs) {
            bfs(0, visited);
        } // Start BFS from vertex 0 (can choose any starting vertex)
        else {
            dfs(0, visited);
        } // Start DFS from vertex 0 (can choose any starting vertex)

        // Check if all vertices were visited
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int vertex, boolean[] visited) {
        visited[vertex] = true;
        for (int neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited);
            }
        }
    }

    private void bfs(int startVertex, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        visited[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();

            for (int neighbor : adjacencyList[vertex]) {
                // If the neighbor has not been visited, mark it as visited and add it to the queue
                // check for visited to avoid cycles
                if (!visited[neighbor] && !queue.contains(neighbor)) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create a graph
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // Check if the graph is connected
        boolean connected = graph.isConnected(true);
        System.out.println("Is the graph connected? " + connected);
    }
}
