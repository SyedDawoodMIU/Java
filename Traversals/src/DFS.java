import java.util.*;

public class DFS {
    private enum State { UNEXPLORED, VISITED, DISCOVERY, BACK }

    public boolean isConnected(Graph graph) {
        Map<Vertex, State> vertexState = new HashMap<>();
        Map<Edge, State> edgeState = new HashMap<>();

        for (Vertex v : graph.getVertices())
            vertexState.put(v, State.UNEXPLORED);

        for (Edge e : graph.getEdges())
            edgeState.put(e, State.UNEXPLORED);

        for (Vertex v : graph.getVertices()) {
            if (vertexState.get(v) == State.UNEXPLORED) {
                dfsComponent(graph, v, vertexState, edgeState);
            }
        }

        // if any vertex is still UNEXPLORED, the graph is not connected
        for (State state : vertexState.values()) {
            if (state == State.UNEXPLORED) {
                return false;
            }
        }
        return true;
    }

    private void dfsComponent(Graph graph, Vertex v, Map<Vertex, State> vertexState, Map<Edge, State> edgeState) {
        vertexState.put(v, State.VISITED);
        startVertexVisit(graph, v);

        for (Edge e : graph.getIncidentEdges(v)) {
            preEdgeVisit(graph, v, e);

            if (edgeState.get(e) == State.UNEXPLORED) {
                Vertex w = graph.getOpposite(v, e);
                edgeVisit(graph, v, e, w);

                if (vertexState.get(w) == State.UNEXPLORED) {
                    edgeState.put(e, State.DISCOVERY);
                    preDiscoveryVisit(graph, v, e, w);
                    dfsComponent(graph, w, vertexState, edgeState);
                    postDiscoveryVisit(graph, v, e, w);
                } else {
                    edgeState.put(e, State.BACK);
                    backEdgeVisit(graph, v, e, w);
                }
            }
        }
        finishVertexVisit(graph, v);
    }

        // Placeholder methods for user-defined actions during DFS traversal
        private void startVertexVisit(Graph graph, Vertex v) {
            // This method can be used to perform an action at the start of visiting a vertex.
            // For example, you might want to print out that you're starting a visit to a vertex:
            System.out.println("Starting visit to vertex: " + v.getLabel());
        }
    
        private void preEdgeVisit(Graph graph, Vertex v, Edge e) {
            // This method can be used to perform an action before visiting an edge.
            // For example, you might want to print out that you're about to visit an edge:
            System.out.println("About to visit edge: " + e);
        }
    
        private void edgeVisit(Graph graph, Vertex v, Edge e, Vertex w) {
            // This method can be used to perform an action when an edge is visited.
            // For example, you might want to print out that you're visiting an edge:
            System.out.println("Visiting edge: " + e);
        }
    
        private void preDiscoveryVisit(Graph graph, Vertex v, Edge e, Vertex w) {
            // This method can be used to perform an action before a discovery visit.
            // For example, you might want to print out that you're about to make a discovery visit:
            System.out.println("About to make a discovery visit from vertex: " + v.getLabel() + " to vertex: " + w.getLabel());
        }
    
        private void postDiscoveryVisit(Graph graph, Vertex v, Edge e, Vertex w) {
            // This method can be used to perform an action after a discovery visit.
            // For example, you might want to print out that you've just finished a discovery visit:
            System.out.println("Just finished a discovery visit from vertex: " + v.getLabel() + " to vertex: " + w.getLabel());
        }
    
        private void backEdgeVisit(Graph graph, Vertex v, Edge e, Vertex w) {
            // This method can be used to perform an action when visiting a back edge.
            // For example, you might want to print out that you're visiting a back edge:
            System.out.println("Visiting a back edge from vertex: " + v.getLabel() + " to vertex: " + w.getLabel());
        }
    
        private void finishVertexVisit(Graph graph, Vertex v) {
            // This method can be used to perform an action at the end of visiting a vertex.
            // For example, you might want to print out that you're finishing a visit to a vertex:
            System.out.println("Finishing visit to vertex: " + v.getLabel());
        }
    
}
