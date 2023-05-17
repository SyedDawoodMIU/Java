import java.util.*;

public class BFS {
    private enum State { UNEXPLORED, VISITED }

    public void bfs(Graph graph) {
        Map<Vertex, State> vertexState = new HashMap<>();
        Map<Edge, State> edgeState = new HashMap<>();
        
        for (Vertex v : graph.getVertices())
            vertexState.put(v, State.UNEXPLORED);

        for (Edge e : graph.getEdges())
            edgeState.put(e, State.UNEXPLORED);
        
        for (Vertex v : graph.getVertices()) {
            if (vertexState.get(v) == State.UNEXPLORED) {
                bfsComponent(graph, v, vertexState, edgeState);
            }
        }
    }

    private void bfsComponent(Graph graph, Vertex s, Map<Vertex, State> vertexState, Map<Edge, State> edgeState) {
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(s);
        vertexState.put(s, State.VISITED);

        while (!queue.isEmpty()) {
            Vertex v = queue.remove();
            for (Edge e : graph.getIncidentEdges(v)) {
                if (edgeState.get(e) == State.UNEXPLORED) {
                    Vertex w = graph.getOpposite(v, e);
                    if (vertexState.get(w) == State.UNEXPLORED) {
                        edgeState.put(e, State.VISITED);  // discovery edge
                        vertexState.put(w, State.VISITED);
                        queue.add(w);
                    } else {
                        edgeState.put(e, State.VISITED);  // cross edge
                    }
                }
            }
        }
    }
}
