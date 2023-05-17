import java.util.*;

public class Graph {
    private Map<Vertex, List<Edge>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    public Vertex addVertex(String label) {
        var vertex = new Vertex(label);
        adjVertices.putIfAbsent(vertex, new ArrayList<>());
        return vertex;
    }

    public void removeVertex(String label) {
        Vertex v = new Vertex(label);
        adjVertices.values().forEach(e -> e.removeIf(edge -> edge.getV().equals(v) || edge.getU().equals(v)));
        adjVertices.remove(new Vertex(label));
    }

    public void addEdge(Vertex v1, Vertex v2) {
        adjVertices.get(v1).add(new Edge(v1, v2));
        adjVertices.get(v2).add(new Edge(v2, v1));
    }

    public void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        List<Edge> edgesV1 = adjVertices.get(v1);
        List<Edge> edgesV2 = adjVertices.get(v2);
        if (edgesV1 != null) edgesV1.removeIf(edge -> edge.getV().equals(v2));
        if (edgesV2 != null) edgesV2.removeIf(edge -> edge.getU().equals(v1));
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(adjVertices.keySet());
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        adjVertices.values().forEach(edges::addAll);
        return edges;
    }

    public List<Edge> getIncidentEdges(Vertex v) {
        return adjVertices.get(v);
    }

    public Vertex getOpposite(Vertex v, Edge e) {
        return e.getU().equals(v) ? e.getV() : e.getU();
    }
}

class Vertex {
    String label;

    Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex vertex = (Vertex) obj;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}

class Edge {
    private Vertex u, v;

    Edge(Vertex u, Vertex v) {
        this.u = u;
        this.v = v;
    }

    public Vertex getU() {
        return u;
    }

    public Vertex getV() {
        return v;
    }
}
