package searches;

import level.Actions;
import level.State;
import level.environment.Environment;
import searches.nodes.CostNode;

import java.util.*;

/**
 * Created by Jonni on 2/6/2017.
 *
 * Using breadth first search to find a path. This is complete but not
 * optimal since our model does not have equal cost of travelling between
 * states.
 */
public class BreadthFirstSearch extends Search {
    /**
     * Attempts to find a path to goal state.
     *
     * @param env Environment
     * @param state State
     */
    public BreadthFirstSearch(Environment env, State state) {
        Set<State> closedSet = new HashSet<>();
        Queue<CostNode> openSet = new LinkedList<>();
        openSet.add(new CostNode(null, state, null, 0));

        while (!openSet.isEmpty()) {

            if (openSet.size() > super.maxOpenSetSize) super.maxOpenSetSize = openSet.size();
            super.expansionCounter++;

            CostNode current = openSet.poll();
            closedSet.add(current.getState());

            // Expansion
            for (Map.Entry<State, Actions> child : current.getState().getReachableStates(env).entrySet()) {
                if (!closedSet.contains(child.getKey())) {
                    CostNode childNode = new CostNode(
                            current,
                            child.getKey(),
                            child.getValue().getActionList(),
                            current.getG() + child.getValue().getCost()
                    );
                    if (env.isGoalState(childNode.getState())) {
                        goalNode = childNode;
                        super.time = System.currentTimeMillis() - super.time;
                        return;
                    }
                    openSet.add(childNode);
                    closedSet.add(childNode.getState());
                }
            }
        }
    }
}
