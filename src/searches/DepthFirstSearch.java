package searches;

import level.Actions;
import level.State;
import level.environment.Environment;
import searches.nodes.CostNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Jonni on 2/6/2017.
 *
 * Using depth first search to find path. This is complete
 * due to finite states and no circles.
 */
public class DepthFirstSearch extends Search {

    /**
     * Attempt to find a path to a goal state.
     *
     * @param env Environment
     * @param state State
     */
    public DepthFirstSearch(Environment env, State state) {
        super();
        Set<State> closedSet = new HashSet<>();
        Stack<CostNode> openSet = new Stack<>();
        openSet.add(new CostNode(null, state, null, 0));

        while (!openSet.isEmpty()) {

            if (openSet.size() > super.maxOpenSetSize) super.maxOpenSetSize = openSet.size();
            super.expansionCounter++;

            CostNode current = openSet.pop();
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
                }
            }
        }
    }
}
