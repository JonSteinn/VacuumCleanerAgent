package level.environment;

import algorithms.Edge;
import algorithms.EdgeWeightedGraph;
import algorithms.PrimMST;
import level.Orientation;
import level.Position;

import java.util.*;

/**
 * Created by Jonni on 1/30/2017.
 *
 * With our pre-processing we have essentially turned our
 * problem into the travelling salesman problem. We do not
 * however know which direction we are facing when we reach
 * a certain point and therefore the minimum cost is evaluated
 * with regards to directions. Beginning with a set of all
 * reachable dirt and a home position, reachable states will
 * include some subset of that for which we store an estimate
 * as a minimal span tree for the given subset. Initially nothing
 * is stored but as soon as one calls for a heuristic for any subset
 * it is stored and if one were to call it again (with different
 * orientation) it will be ready without any calculations needed.
 */
public class HeuristicCache {

    // Stores total weight of minimal spam trees
    private Map<Set<Position>, Integer> cache;
    // Holds the reference to weights from pre-processing
    private Map<Position, Map<Orientation, Map<Position, Integer>>> weights;

    /**
     * Creates an empty cache and stores a reference to weights.
     *
     * @param preProcess EnvironmentPreProcess
     */
    public HeuristicCache(EnvironmentPreProcess preProcess) {
        cache = new HashMap<>();
        weights = preProcess.getWeights();
    }

    /**
     * Returns minimal spam tree (which is only a part of the actual heuristic)
     * for a given subset of dirt and home position. If the value has not been
     * cached, it will calculate it, store it and return it.
     *
     * @param dirtAndHome Set of Position
     * @return int
     */
    public int getCachedHeuristic(Set<Position> dirtAndHome) {

        Integer heuristic = this.cache.get(dirtAndHome);

        // If not stored
        if (heuristic == null) {

            // Map positions to integers for graph
            Map<Position,Integer> indexMap = new HashMap<>();
            int index = 0;
            for (Position p : dirtAndHome) {
                indexMap.put(p, index++);
            }

            // Create a graph of same size
            EdgeWeightedGraph g = new EdgeWeightedGraph(dirtAndHome.size());
            // Copy set so we can remove from it
            Set<Position> copy = new HashSet<>(dirtAndHome);
            Iterator<Position> it = copy.iterator();
            // Iterate such that each pair is only chosen one
            // 1+2+...+n = n(n+1)/2 = binomial(n,2) in stead of n^2
            while (it.hasNext()) {
                Position from = it.next();
                it.remove();
                for (Position to : copy) {
                    g.addEdge(new Edge(
                            indexMap.get(from),
                            indexMap.get(to),
                            this.getMinWeight(from, to)
                    ));
                }
            }
            this.cache.put(dirtAndHome, new PrimMST(g).weight());
        }
        return this.cache.get(dirtAndHome);
    }

    /**
     * Returns the minimum cost of travelling from A to B starting in any direction.
     *
     * @param A Position
     * @param B Position
     * @return int
     */
    private int getMinWeight(Position A, Position B) {
        int min = Integer.MAX_VALUE;
        for (Orientation o : Orientation.orientations()) {
            int next = this.weights.get(A).get(o).get(B);
            if (next < min) min = next;
        }
        return min;
    }
}
