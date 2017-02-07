package algorithms;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  This is from the Sedgewick&Wayne book.
 *  I pruned it of text since this is just for homework
 *  to make it easier to read through and alter for my needs.
 */
public class PrimMST {
    private Edge[] edgeTo;
    private int[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Integer> pq;

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new int[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Integer.MAX_VALUE;

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new LinkedList<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.add(e);
            }
        }
        return mst;
    }

    public int weight() {
        int weight = 0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }
}