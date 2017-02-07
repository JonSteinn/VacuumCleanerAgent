package algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  This is from the Sedgewick&Wayne book.
 *  I pruned it of text since this is just for homework
 *  to make it easier to read through and alter for my needs.
 */
public class EdgeWeightedGraph {
    private final int V;
    private List<Set<Edge>> adj;
    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int v = 0; v < V; v++) {
            adj.add(v, new HashSet<>());
        }
    }
    public int V() {
        return V;
    }
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj.get(v).add(e);
        adj.get(w).add(e);
    }
    public Iterable<Edge> adj(int v) {
        return adj.get(v);
    }
}