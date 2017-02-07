package searches.nodes;

import level.State;
import level.environment.Environment;

import java.util.ArrayList;

/**
 * Created by Jonni on 2/4/2017.
 *
 * A node with an additional field for F-value.
 */
public class HeuristicNode extends Node {

    private HeuristicNode parent;
    private int F;

    /**
     * Constructor that calls constructor of super class,
     * sets parent and stores the F-value for the state.
     *
     * @param parent HeuristicNode
     * @param state State
     * @param actionList ArrayList of String
     * @param g int
     * @param env Environment
     */
    public HeuristicNode(HeuristicNode parent, State state, ArrayList<String> actionList, int g, Environment env) {
        super(state, actionList, g);
        this.parent = parent;
        this.F = g + state.heuristic(env);
    }

    /**
     * Getter for node's parent.
     *
     * @return HeuristicNode
     */
    @Override
    public HeuristicNode getParent() {
        return this.parent;
    }

    /**
     * Getter for F value.
     *
     * @return int
     */
    public int getF() {
        return this.F;
    }
}