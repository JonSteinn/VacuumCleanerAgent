package level;

import level.environment.Environment;

import java.util.*;

/**
 * Created by Jonni on 1/27/2017.
 *
 * A state is represented by the agent position, what dirt has been cleaned
 * and which direction the agent is facing. The agent position is limited to
 * that of his original position and any reachable dirt at start.
 */
public class State {

    private Orientation orientation;
    private Position agentPosition;
    private Set<Position> cleaned;

    /**
     * Used to create a initial state. No dirt has been cleaned.
     *
     * @param orientation Orientation
     * @param agentPosition Position
     */
    public State(Orientation orientation, Position agentPosition) {
        this(orientation, agentPosition, new HashSet<>());
    }

    /**
     * Used to create a state with a set of cleaned dirt.
     *
     * @param orientation Orientation
     * @param agentPosition Position
     * @param cleaned Set of Position
     */
    public State(Orientation orientation, Position agentPosition, Set<Position> cleaned) {
        this.orientation = orientation;
        this.agentPosition = agentPosition;
        this.cleaned = cleaned;
    }

    /**
     * Getter for orientation.
     *
     * @return Orientation
     */
    public Orientation getOrientation() {
        return this.orientation;
    }

    /**
     * Returns an estimation of the remaining cost from a state. It
     * is the total weight of a minimal span tree of every remaining
     * dirt and the home position and the minimum cost of reaching
     * this minimal span tree from the agent position. Since our
     * problem has been transformed into a travelling salesman problem,
     * the best possible path would be to reach the span tree with the
     * shortest path to it, arriving at one end while the other end
     * holds the home position.
     *
     * @param env Environment
     * @return int
     */
    public int heuristic(Environment env) {
        Set<Position> remaining = env.notCleaned(this.cleaned);
        remaining.add(env.getHome());
        if (remaining.size() == 1) return env.isHome(this.agentPosition) ?
                0 :
                env.distanceFromTo(this.orientation, this.agentPosition, env.getHome());
        if (env.isHome(this.agentPosition)) return env.getCachedHeuristics(remaining);

        int shortest = Integer.MAX_VALUE;
        for (Position pos : remaining) {
            int dist;
            if ((dist = env.distanceFromTo(this.orientation, this.agentPosition, pos)) < shortest) {
                shortest = dist;
            }
        }
        return shortest + env.getCachedHeuristics(remaining);
    }

    /**
     * Returns a map with all reachable states from the current one as keys
     * and a the actions needed to get there as values.
     *
     * @param env Environment
     * @return Map: State -> Actions
     */
    public Map<State, Actions> getReachableStates(Environment env) {
        // We are happy to use the same references when it does not hurt.

        Map<State, Actions> map = new HashMap<>();
        Set<Position> remaining = env.notCleaned(this.cleaned);

        // Special case when we start on dirt, this will never happen after that
        // I think none of the maps include such a scenario but nevertheless,
        // its better to prepare for it than not. If such a scenario arises, we
        // return 'suck' as the only option.
        if (remaining.contains(this.agentPosition)) {
            HashSet<Position> newRemaining = new HashSet<>(remaining);
            newRemaining.remove(this.agentPosition);
            ArrayList<String> actionList = new ArrayList<>();
            actionList.add(Actions.SUCK);
            map.put(new State(this.orientation, this.agentPosition, newRemaining), new Actions(actionList, 1));
            return map;
        }

        // Add a state to each remaining reachable dirt where we end by cleaning that particular dirt.
        for (Position p : remaining) {
            Actions actions = new Actions(new ArrayList<>(), 0);
            Orientation finalOrientation = generateActionsToPosition(env, actions, p);
            actions.addAction(Actions.SUCK);
            HashSet<Position> newCleaned = new HashSet<>(this.cleaned);
            newCleaned.add(p);
            map.put(new State(finalOrientation, p, newCleaned), actions);
        }

        // Only give 'going home' as an option if we aren't there and remaining is empty.
        if (!env.isHome(this.agentPosition) && remaining.isEmpty()) {
            Actions actions = new Actions(new ArrayList<>(), 0);
            Orientation finalOrientation = generateActionsToPosition(env, actions, env.getHome());
            map.put(new State(finalOrientation, env.getHome(), this.cleaned), actions);
        }

        return map;
    }

    /**
     * Follows the predefined path and constructs a action list from our current state.
     * When we are done, we return the final orientation after finishing the path so
     * we know where to begin from there.
     *
     * @param env Environment
     * @param actions Actions
     * @param p Position
     * @return Orientation
     */
    private Orientation generateActionsToPosition(Environment env, Actions actions, Position p) {

        // This is somewhat messy, sorry about that.

        ArrayList<Position> path = env.getPath(this.orientation, this.agentPosition, p);
        Orientation prev = path.get(0).getDirectionTo(path.get(1));

        // Add turns needed, if any, to start moving in predefined path
        if (!this.orientation.equals(prev)) {
            switch (this.orientation.getOrientation()) {
                case EAST:
                    switch (prev.getOrientation()) {
                        case NORTH:
                            actions.addAction(Actions.TURN_LEFT);
                            break;
                        case SOUTH:
                            actions.addAction(Actions.TURN_RIGHT);
                            break;
                        case WEST:
                            actions.addAction(Actions.TURN_LEFT);
                            actions.addAction(Actions.TURN_LEFT);
                    }
                    break;
                case WEST:
                    switch (prev.getOrientation()) {
                        case NORTH:
                            actions.addAction(Actions.TURN_RIGHT);
                            break;
                        case SOUTH:
                            actions.addAction(Actions.TURN_LEFT);
                            break;
                        case EAST:
                            actions.addAction(Actions.TURN_LEFT);
                            actions.addAction(Actions.TURN_LEFT);
                    }
                    break;
                case NORTH:
                    switch (prev.getOrientation()) {
                        case EAST:
                            actions.addAction(Actions.TURN_RIGHT);
                            break;
                        case WEST:
                            actions.addAction(Actions.TURN_LEFT);
                            break;
                        case SOUTH:
                            actions.addAction(Actions.TURN_LEFT);
                            actions.addAction(Actions.TURN_LEFT);
                    }
                    break;
                case SOUTH:
                    switch (prev.getOrientation()) {
                        case EAST:
                            actions.addAction(Actions.TURN_LEFT);
                            break;
                        case WEST:
                            actions.addAction(Actions.TURN_RIGHT);
                            break;
                        case NORTH:
                            actions.addAction(Actions.TURN_LEFT);
                            actions.addAction(Actions.TURN_LEFT);
                    }
            }
        }

        // Go to second square and start from there
        actions.addAction(Actions.GO);

        // For each square (grid cell) in the path
        for (int i = 2; i < path.size(); i++) {

            // If we need to turn to keep going
            Orientation next = path.get(i - 1).getDirectionTo(path.get(i));
            if (!prev.equals(next)) {
                switch (prev.getOrientation()) {
                    case EAST:
                        switch (next.getOrientation()) {
                            case NORTH:
                                actions.addAction(Actions.TURN_LEFT);
                                break;
                            case SOUTH:
                                actions.addAction(Actions.TURN_RIGHT);
                        }
                        break;
                    case WEST:
                        switch (next.getOrientation()) {
                            case NORTH:
                                actions.addAction(Actions.TURN_RIGHT);
                                break;
                            case SOUTH:
                                actions.addAction(Actions.TURN_LEFT);
                        }
                        break;
                    case NORTH:
                        switch (next.getOrientation()) {
                            case WEST:
                                actions.addAction(Actions.TURN_LEFT);
                                break;
                            case EAST:
                                actions.addAction(Actions.TURN_RIGHT);
                        }
                        break;
                    case SOUTH:
                        switch (next.getOrientation()) {
                            case WEST:
                                actions.addAction(Actions.TURN_RIGHT);
                                break;
                            case EAST:
                                actions.addAction(Actions.TURN_LEFT);
                        }
                }
                prev = next;
            }

            // move to next square
            actions.addAction(Actions.GO);
        }
        return prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        State other = (State)o;
        return (this.orientation.equals(other.orientation)) &&
                (this.agentPosition.equals(other.agentPosition)) &&
                (this.cleaned.equals(other.cleaned));
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = (hash << 5) - hash + this.orientation.hashCode();
        hash = (hash << 5) - hash + this.agentPosition.hashCode();
        hash = (hash << 5) - hash + this.cleaned.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "Agent: " + this.agentPosition + ", Orientation: " + this.orientation + ", Cleaned: " + this.cleaned;
    }
}
