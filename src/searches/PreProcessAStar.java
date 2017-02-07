package searches;

import level.Orientation;
import level.Position;
import level.environment.Environment;
import searches.nodes.PreProcessNode;

import java.util.*;

/**
 * Created by Jonni on 2/4/2017.
 *
 * Searches path from a position to another in a given environment.
 * Additional cost is placed on each turn in order to minimize them.
 */
public final class PreProcessAStar {
    private PreProcessNode goal;

    /**
     * Performs A* search for a path from A to B in an environment
     * env where the initial orientation is initOr.
     *
     * @param A Position (from)
     * @param B Position (to)
     * @param env Environment
     * @param initOr Orientation
     */
    public PreProcessAStar(Position A, Position B, Environment env, Orientation initOr) {

        Set<Position> closed = new HashSet<>();
        PriorityQueue<PreProcessNode> open = new PriorityQueue<>(Comparator.comparingInt(PreProcessNode::getF));
        // Since java.util does not provide an option to get reference from pq, we use double book keeping.
        Map<Position, Integer> openTracker = new HashMap<>();

        // Initial state added
        open.add(new PreProcessNode(null, A, 0, B));
        openTracker.put(A, 0);

        while (!open.isEmpty()) {

            PreProcessNode current = open.poll();
            openTracker.remove(current.getState());
            closed.add(current.getState());

            if (current.getState().equals(B)) {
                goal = current;
                break;
            }

            // Adjacent squares. North is used to make frontOf up, etc.
            Position[] children = {
                    Position.frontOf(current.getState(), new Orientation(Orientation.Direction.NORTH)),
                    Position.behindOf(current.getState(), new Orientation(Orientation.Direction.NORTH)),
                    Position.leftOf(current.getState(), new Orientation(Orientation.Direction.NORTH)),
                    Position.rightOf(current.getState(), new Orientation(Orientation.Direction.NORTH))
            };

            for (Position child : children) {
                // If not: out of bounds, containing obstacle, already expanded
                if (env.isReachable(child) && !closed.contains(child)) {
                    Integer i = openTracker.get(child);
                    int g = getG(current, child, initOr);
                    // If not currently in open set
                    if (i == null) {
                        openTracker.put(child, current.getG() + g);
                        open.add(new PreProcessNode(current, child, current.getG() + g, B));
                    } else {
                        // If currently in open set with higher G cost.
                        if (i > current.getG() + g) {
                            PreProcessNode childNode = new PreProcessNode(current, child, current.getG() + g, B);
                            open.remove(childNode);
                            open.add(childNode);
                            openTracker.put(child, current.getG() + g);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the goal node that was found
     *
     * @return PreProcessNode
     */
    public PreProcessNode getGoal() {
        return this.goal;
    }

    /**
     * Returns the cost of the path found.
     *
     * @return int
     */
    public int cost() {
        return this.goal.getG();
    }

    /**
     * Returns every position in correct order that was
     * visited to reach the goal state.
     *
     * @return ArrayList of Position
     */
    public ArrayList<Position> getPath() {
        ArrayList<Position> path = new ArrayList<>();
        PreProcessNode temp = this.goal;
        while (temp != null) {
            path.add(temp.getState());
            temp = temp.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Returns the cost of moving from current to child.
     *
     * @param current PreProcessNode
     * @param child Position
     * @param init Orientation
     * @return int
     */
    private int getG(PreProcessNode current, Position child, Orientation init) {
        return 1 + ((current.getParent() == null) ?
                init.turnCost(current.getState().getDirectionTo(child)) :
                current
                        .getParent()
                        .getState()
                        .getDirectionTo(current.getState())
                        .turnCost(current.getState().getDirectionTo(child)));
    }
}
