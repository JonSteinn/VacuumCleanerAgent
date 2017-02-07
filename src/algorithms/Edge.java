package algorithms;

/**
 *  This is from the Sedgewick&Wayne book.
 *  I pruned it of text since this is just for homework
 *  to make it easier to read through and alter for my needs.
 */
public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final int weight;
    public Edge(int v, int w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    public int weight() {
        return weight;
    }
    public int either() {
        return v;
    }
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }
    @Override
    public int compareTo(Edge that) {
        return Integer.compare(this.weight, that.weight);
    }
}