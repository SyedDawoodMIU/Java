public class App {
    public static void main(String[] args) throws Exception {
        // create a new graph
        Graph graph = new Graph();

        // add some vertices
        Vertex A = graph.addVertex("A");
        Vertex B = graph.addVertex("B");
        Vertex C = graph.addVertex("C");
        Vertex D = graph.addVertex("D");
        Vertex E = graph.addVertex("E");

        // add some edges
        graph.addEdge(A, B);
        graph.addEdge(A, C);
        graph.addEdge(B, D);
        graph.addEdge(C, D);
        graph.addEdge(D, E);

        // create a BFS instance and perform BFS
        BFS bfs = new BFS();
        bfs.bfs(graph);

        DFS dfs = new DFS();
        dfs.isConnected(graph);
    }
}
