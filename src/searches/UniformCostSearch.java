package searches;

import level.Actions;
import level.State;
import level.environment.Environment;
import searches.nodes.CostNode;

import java.util.*;

/**
 * Created by Jonni on 2/6/2017.
 *
 * Finds a way to travel through the grid, cleaning up all reachable dirt
 * and return back home, with an optimal cost.
 */
public class UniformCostSearch extends Search {

    /**
     * Attempts to find a path to goal state.
     *
     * @param env Environment
     * @param state State
     */
    public UniformCostSearch(Environment env, State state) {

        Set<State> closed = new HashSet<>();
        PriorityQueue<CostNode> openSet = new PriorityQueue<>(Comparator.comparingInt(CostNode::getG));
        // Tracking system to update open set if we find better G values. Java.util does not offer anything :(
        Map<State, Integer> openTracker = new HashMap<>();

        openSet.add(new CostNode(null, state, null, 0));
        openTracker.put(state, 0);

        while (!openSet.isEmpty()) {

            if (openSet.size() > super.maxOpenSetSize) super.maxOpenSetSize = openSet.size();

            CostNode current = openSet.poll();
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
                        openSet.add(new CostNode(
                                current,
                                child.getKey(),
                                child.getValue().getActionList(),
                                current.getG() + child.getValue().getCost()
                        ));
                    } else if (i > current.getG() + child.getValue().getCost()) {
                        // If in open set with more cost
                        CostNode m = new CostNode(
                                current,
                                child.getKey(),
                                child.getValue().getActionList(),
                                current.getG() + child.getValue().getCost()
                        );
                        openSet.remove(m);
                        openSet.add(m);
                        openTracker.put(m.getState(), m.getG());
                    }
                }
            }
        }
    }
}