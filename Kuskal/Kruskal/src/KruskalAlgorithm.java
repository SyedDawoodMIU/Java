import java.util.*;

class Edge {
    String src, dest;
    int weight;

    public Edge(String src, String dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

public class KruskalAlgorithm {
    private static void KruskalMST(ArrayList<Edge> edges, int V) {
        // Sort all the edges in ascending order of their weight.
        Collections.sort(edges, Comparator.comparingInt(o -> o.weight));

        // Create a new array to store the parent of each node. Initially, each node is
        // its own parent.
        Map<String, String> parent = new HashMap<>();
        for (Edge edge : edges) {
            parent.put(edge.src, edge.src);
            parent.put(edge.dest, edge.dest);
        }

        // Create a new list to store the edges of the minimum spanning tree.
        ArrayList<Edge> mst = new ArrayList<>();

        // Iterate over all the edges.
        for (Edge edge : edges) {
            // Find the parent of the source and destination of the current edge.
            String x = find(parent, edge.src);
            String y = find(parent, edge.dest);

            // If the parents are different, it means the current edge will not form a
            // cycle. So, we add it to our result.
            if (!x.equals(y)) {
                mst.add(edge);

                // After adding the edge to the result, we perform union of the two sets. It
                // makes one as the parent of the other
                parent.put(x, y);
            }
        }

        // Finally, print the edges of the minimum spanning tree.
        for (Edge edge : mst)
            System.out.println("Edge: " + edge.src + " -- " + edge.dest + " has weight: " + edge.weight);
    }

   
    // The find function will follow the parent pointers until it reaches a node
    // that is its own parent (the root of the tree), and returns this root node.
    private static String find(Map<String, String> parent, String i) {
        if (i.equals(parent.get(i)))
            return i;
        return find(parent, parent.get(i));
    }

    public static void main(String[] args) {
        int V = 9; // Number of vertices in graph
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge("JFK", "BOS", 187));
        edges.add(new Edge("JFK", "MIA", 1258));
        edges.add(new Edge("JFK", "ORD", 740));
        edges.add(new Edge("BOS", "ORD", 867));
        edges.add(new Edge("MIA", "ORD", 1197));
        edges.add(new Edge("ORD", "LAX", 1745));
        edges.add(new Edge("ORD", "DFW", 802));
        edges.add(new Edge("ORD", "SFO", 1846));
        edges.add(new Edge("ORD", "BWI", 621));
        edges.add(new Edge("ORD", "PVD", 849));
        edges.add(new Edge("LAX", "SFO", 337));
        edges.add(new Edge("LAX", "DFW", 1235));
        edges.add(new Edge("DFW", "SFO", 1464));
        edges.add(new Edge("DFW", "BWI", 1302));
        edges.add(new Edge("DFW", "PVD", 1515));
        edges.add(new Edge("SFO", "BWI", 2442));
        edges.add(new Edge("SFO", "PVD", 2704));
        edges.add(new Edge("BWI", "PVD", 325));

        KruskalMST(edges, V);
    }
}
