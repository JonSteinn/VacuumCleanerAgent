package level.environment;

import level.Orientation;
import level.Position;
import searches.PreProcessAStar;

import java.util.*;

/**
 * Created by Jonni on 2/2/2017.
 *
 * Analysis of the environment before search algorithms are run. It
 * finds which dirt are reachable, finds the path from every position
 * (reachable dirt and home) depending on starting orientation and
 * calculates minimum cost of travel.
 */
public final class EnvironmentPreProcess {

    // Stores reachable dirt
    private Set<Position> reachable;
    // Stores weights of getting from a position to another starting with different orientation
    private Map<Position, Map<Orientation, Map<Position, Integer>>> weights;
    // Stores the optimal path (in terms of Positions) to get from
    // one point to another while initially facing a direction
    private Map<Position, Map<Orientation, Map<Position, ArrayList<Position>>>> paths;

    private long time;

    /**
     * Pre-calculations to make search faster.
     *
     * @param environment Environment
     */
    public EnvironmentPreProcess(Environment environment) {
        this.reachable = new HashSet<>();
        this.weights = new HashMap<>();
        this.paths = new HashMap<>();

        this.time = System.currentTimeMillis();
        setFromHome(environment);
        setDirt(environment);
        this.time = System.currentTimeMillis() - this.time;
    }

    /**
     * Returns the time it takes to pre process the environment in ms.
     *
     * @return long
     */
    public long getTime() {
        return this.time;
    }

    /**
     * Returns a list of optimal positions to use when travelling
     * from a position to another while initially facing a direction.
     *
     * @param fromOrientation Orientation
     * @param from Position
     * @param to Position
     * @return ArrayList of Position
     */
    public ArrayList<Position> getPath(Orientation fromOrientation, Position from, Position to) {
        return this.paths.get(from).get(fromOrientation).get(to);
    }

    /**
     * Returns the set of all dirt reachable from the starting position.
     *
     * @return Set of Position
     */
    public Set<Position> getReachable() {
        return this.reachable;
    }

    /**
     * Returns the cost of travelling from a position to another.
     *
     * @return Map : Position -> (Map : Orientation -> (Map : Position -> Integer))
     */
    public Map<Position, Map<Orientation, Map<Position, Integer>>> getWeights() {
        return this.weights;
    }

    /**
     * From home position to any dirt and back.
     * Finds reachable dirt and adds to collection.
     * Finds best path and adds to collection.
     * Finds cost and adds to collection.
     *
     * @param env Environment
     */
    private void setFromHome(Environment env) {

        // For all possible dirt
        for (Position p : env.getDirt()) {

            // For all possible directions
            for (Orientation o : Orientation.orientations()) {

                // Try to find path
                PreProcessAStar search = new PreProcessAStar(env.getHome(), p, env, o);

                // If path was found
                if (search.getGoal() != null) {

                    // Store dirt as reachable (each point is only stored ones, regardless of orientation)
                    this.reachable.add(p);

                    // Find the best way back with the same initial orientation
                    PreProcessAStar searchBack = new PreProcessAStar(p, env.getHome(), env, o);

                    // Add path to
                    this.paths.computeIfAbsent(env.getHome(), k -> new HashMap<>());
                    this.paths.get(env.getHome()).computeIfAbsent(o, k -> new HashMap<>());
                    this.paths.get(env.getHome()).get(o).put(p, search.getPath());

                    // Add path from
                    this.paths.computeIfAbsent(p, k -> new HashMap<>());
                    this.paths.get(p).computeIfAbsent(o, k -> new HashMap<>());
                    this.paths.get(p).get(o).put(env.getHome(), searchBack.getPath());

                    // Add cost from and to
                    this.weights.computeIfAbsent(env.getHome(), k -> new HashMap<>());
                    this.weights.get(env.getHome()).computeIfAbsent(o, k -> new HashMap<>());
                    this.weights.computeIfAbsent(p, k -> new HashMap<>());
                    this.weights.get(p).computeIfAbsent(o, k -> new HashMap<>());
                    this.weights.get(env.getHome()).get(o).put(p, search.cost());
                    this.weights.get(p).get(o).put(env.getHome(), searchBack.cost());

                }
            }
        }
    }

    /**
     * From any reachable dirt to another reachable.
     * Finds best path and adds to collection.
     * Finds minimum cost and adds to collection.
     *
     * @param env Environment
     */
    private void setDirt(Environment env) {

        // This is very similar to setFromHome, will only point out difference.

        // Create a set of reachable dirt
        Set<Position> set = new HashSet<>(reachable);

        while (!set.isEmpty()) {
            Iterator<Position> it = set.iterator();
            Position A = it.next();
            it.remove();
            for (Position B : set) {

                for (Orientation o : Orientation.orientations()) {

                    // No adding to reachable here.

                    PreProcessAStar search = new PreProcessAStar(A, B, env, o);
                    PreProcessAStar searchBack = new PreProcessAStar(B, A, env, o);

                    this.paths.computeIfAbsent(A, k -> new HashMap<>());
                    this.paths.get(A).computeIfAbsent(o, k -> new HashMap<>());
                    this.paths.get(A).get(o).put(B, search.getPath());

                    this.paths.computeIfAbsent(B, k -> new HashMap<>());
                    this.paths.get(B).computeIfAbsent(o, k -> new HashMap<>());
                    this.paths.get(B).get(o).put(A, searchBack.getPath());

                    this.weights.computeIfAbsent(A, k -> new HashMap<>());
                    this.weights.computeIfAbsent(B, k -> new HashMap<>());
                    this.weights.get(A).computeIfAbsent(o, k -> new HashMap<>());
                    this.weights.get(B).computeIfAbsent(o, k -> new HashMap<>());
                    this.weights.get(A).get(o).put(B, search.cost());
                    this.weights.get(B).get(o).put(A, searchBack.cost());
                }
            }
        }
    }
}
