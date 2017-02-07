package searches;

import level.Actions;
import level.State;
import level.environment.Environment;
import searches.nodes.HeuristicNode;

import java.util.*;

/**
 * Created by Jonni on 2/4/2017.
 *
 * Finds a way to travel through the grid, cleaning up all reachable dirt
 * and return back home, with an optimal cost, using a state detecting
 * A* algorithm.
 */
public class AStar extends Search {

    /**
     * Attempts to find a path through the grid, reaching a goal state.
     *
     * @param env Environment
     * @param state State
     */
    public AStar(Environment env, State state) {

        Set<State> closed = new HashSet<>();
        PriorityQueue<HeuristicNode> openSet = new PriorityQueue<>(Comparator.comparingInt(HeuristicNode::getF));
        // Tracking system to update open set if we find better G values. Java.util does not offer anything :(
        Map<State, Integer> openTracker = new HashMap<>();

        // Initial state added
        openSet.add(new HeuristicNode(null, state, null, 0, env));
        openTracker.put(state, 0);

        while (!openSet.isEmpty()) {

            if (openSet.size() > super.maxOpenSetSize) super.maxOpenSetSize = openSet.size();

            HeuristicNode current = openSet.poll();
            openTracker.remove(current.getState());
            closed.add(current.getState());

            if (env.isGoalState(current.getState())) {
                goalNode = current;
                super.time = System.currentTimeMillis() - super.time;
                break;
            }

            super.expansionCounter++;

            // Expand current node
            for (Map.Entry<State, Actions> child : current.getState().getReachableStates(env).entrySet()) {
                if (!closed.contains(child.getKey())) {

                    Integer i = openTracker.get(child.getKey());
                    if (i == null) {
                        // If not in open set
                        openTracker.put(
                                child.getKey(),
                                current.getG() + child.getValue().getCost()
                        );
                        openSet.add(new HeuristicNode(
                                current,
                                child.getKey(),
                                child.getValue().getActionList(),
                                current.getG() + child.getValue().getCost(),
                                env
                        ));
                    } else if (i > current.getG() + child.getValue().getCost()) {
                        // If in open set with more cost
                        HeuristicNode m = new HeuristicNode(
                                current,
                                child.getKey(),
                                child.getValue().getActionList(),
                                current.getG() + child.getValue().getCost(),
                                env);
                        openSet.remove(m);
                        openSet.add(m);
                        openTracker.put(m.getState(), m.getG());
                    }
                }
            }
        }
    }
}