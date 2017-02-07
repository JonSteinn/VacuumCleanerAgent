package searches.nodes;

import level.State;

import java.util.ArrayList;

/**
 * Created by Jonni on 2/6/2017.
 *
 * A node that holds no heuristic, only actual cost.
 */
public class CostNode extends Node {

    private CostNode parent;

    /**
     * Constructor that calls constructor of super class and sets parent
     *
     * @param parent CostNode
     * @param state State
     * @param lastActionList ArrayList of String
     * @param g int
     */
    public CostNode(CostNode parent, State state, ArrayList<String> lastActionList, int g) {
        super(state, lastActionList, g);
        this.parent = parent;
    }

    @Override
    public CostNode getParent() {
        return this.parent;
    }
}
