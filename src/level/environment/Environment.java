package level.environment;

import level.Orientation;
import level.Position;
import level.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Jonni on 1/27/2017.
 *
 * Describes the environment which contains the agent.
 */
public class Environment {

    // From input
    private Position home;
    private int width;
    private int height;
    private Set<Position> dirt;
    private Set<Position> obstacles;

    // Calculated
    private Set<State> goalStates;
    private HeuristicCache hCache;
    private EnvironmentPreProcess preProcess;

    /**
     * A constructor for environment that will pre process a lot of values
     * to make search faster as well as set up a heuristic cache for A*.
     *
     * @param home Position
     * @param width int
     * @param height int
     * @param dirt Set of Position
     * @param obstacles Set of Position
     */
    public Environment(Position home, int width, int height, Set<Position> dirt, Set<Position> obstacles) {
        this.home = home;
        this.width = width;
        this.height = height;
        this.dirt = dirt;
        this.obstacles = obstacles;
        this.preProcess = new EnvironmentPreProcess(this);
        this.setGoalStates();
        this.hCache = new HeuristicCache(this.preProcess); // will never be used for non A* searches
    }

    /**
     * Returns the total weight of a minimal span tree through given set.
     * It will use minimum cost of between each point for all possible orientations.
     *
     * @param dirtAndHome Set of Position
     * @return int
     */
    public int getCachedHeuristics(Set<Position> dirtAndHome ) {
        return this.hCache.getCachedHeuristic(dirtAndHome);
    }

    /**
     * Creates a set of all possible goal state, taking into account dirt that are not reachable.
     */
    private void setGoalStates() {
        this.goalStates = new HashSet<>();
        this.goalStates.add(new State(new Orientation(Orientation.Direction.NORTH), this.home, this.preProcess.getReachable()));
        this.goalStates.add(new State(new Orientation(Orientation.Direction.WEST), this.home, this.preProcess.getReachable()));
        this.goalStates.add(new State(new Orientation(Orientation.Direction.SOUTH), this.home, this.preProcess.getReachable()));
        this.goalStates.add(new State(new Orientation(Orientation.Direction.EAST), this.home, this.preProcess.getReachable()));
    }

    /**
     * Returns true iff state is a goal state.
     *
     * @param state State
     * @return boolean
     */
    public boolean isGoalState(State state) {
        return this.goalStates.contains(state);
    }

    /**
     * Returns the optimal path in terms of positions to travel from a
     * Position to another while starting with a given Orientation.
     *
     * @param fromOr Orientation
     * @param from Position
     * @param to Position
     * @return ArrayList of Position
     */
    public ArrayList<Position> getPath(Orientation fromOr, Position from, Position to) {
        return this.preProcess.getPath(fromOr, from, to);
    }

    /**
     * Returns the position in which the agent started in.
     *
     * @return Position
     */
    public Position getHome() {
        return this.home;
    }

    /**
     * Returns a set of all dirt in the environment.
     *
     * @return Set of Position
     */
    public Set<Position> getDirt() {
        return this.dirt;
    }

    /**
     * Returns true iff the argument is the home position.
     *
     * @param p Position
     * @return boolean
     */
    public boolean isHome(Position p) {
        return p.equals(this.home);
    }

    /**
     * Returns the set minus of cleaned and reachable dirt.
     *
     * @param cleaned Set of Position
     * @return Set of Position
     */
    public Set<Position> notCleaned(Set<Position> cleaned) {
        return this.preProcess.getReachable().stream().filter(pos -> !cleaned.contains(pos)).collect(Collectors.toSet());
    }

    /**
     * Returns false if p is out of bounds.
     * Returns false if p contains an obstacle.
     * Returns true otherwise.
     *
     * @param p Position
     * @return boolean
     */
    public boolean isReachable(Position p) {
        return !(p.getX() < 1 || p.getY() < 1 || p.getX() > this.width || p.getY() > this.height || this.obstacles.contains(p));
    }

    /**
     * Getter for width.
     *
     * @return int
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter for height.
     *
     * @return int
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for obstacles.
     *
     * @return Set of Position
     */
    public Set<Position> getObstacles() {
        return this.obstacles;
    }

    /**
     * Returns the path cost of going from A to B starting with orientation o.

     *
     * @param o Orientation
     * @param A Position
     * @param B Position
     * @return int
     */
    public int distanceFromTo(Orientation o, Position A, Position B) {
        return this.preProcess.getWeights().get(A).get(o).get(B);
    }
}
