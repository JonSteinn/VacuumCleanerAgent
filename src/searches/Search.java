package searches;

import level.Actions;
import level.State;
import searches.nodes.Node;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Jonni on 2/6/2017.
 */
public class Search {

    protected Node goalNode;
    protected long time;
    protected int expansionCounter;
    protected int maxOpenSetSize;

    public Search() {
        this.time = System.currentTimeMillis();
        expansionCounter = 0;
        maxOpenSetSize = 0;
    }

    /**
     * Returns goal state.
     *
     * @return State
     */
    public State getGoalState() {
        return this.goalNode == null ? null : this.goalNode.getState();
    }

    /**
     * Returns cost of reaching goal state.
     *
     * @return int
     */
    public int getCost() {
        // The 2 are for turning on and off.
        return this.goalNode.getG() + 2;
    }

    /**
     * Returns the total list of actions needed, along with
     * turning on and off, in the correct order to execute.
     *
     * @return ArrayList of String
     */
    public <T extends Node> ArrayList<String> getActionSequence() {
        Stack<ArrayList<String>> stack = new Stack<>();
        T tmp = (T)this.goalNode;
        while (tmp.getActionList() != null) {
            stack.add(tmp.getActionList());
            tmp = tmp.getParent();
        }
        ArrayList<String> actions = new ArrayList<>();
        actions.add(Actions.TURN_ON);
        while (!stack.isEmpty()) {
            actions.addAll(stack.pop());
        }
        actions.add(Actions.TURN_OFF);
        return actions;
    }

    /**
     * Returns the time that it takes to finish the search in ms.
     *
     * @return long
     */
    public long time() {
        return this.time;
    }

    /**
     * Returns the number of state expansions during the search.
     *
     * @return int
     */
    public int expansionCount() {
        return this.expansionCounter;
    }

    /**
     * Returns the max size of the open set during the search.
     *
     * @return int
     */
    public int maxOpenSetSize() {
        return this.maxOpenSetSize;
    }
}
