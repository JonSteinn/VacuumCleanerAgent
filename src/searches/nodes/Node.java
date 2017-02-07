package searches.nodes;

import level.State;

import java.util.ArrayList;

/**
 * Created by Jonni on 2/6/2017.
 *
 * A general Node that holds reusable attributes.
 * Should not be used directly.
 */
public class Node {
    private State state;
    private ArrayList<String> lastActionList;
    private int G;

    /**
     * Sets values for subclasses.
     *
     * @param state State
     * @param lastActionList ArrayList of String
     * @param g int
     */
    protected Node(State state, ArrayList<String> lastActionList, int g) {
        this.state = state;
        this.lastActionList = lastActionList;
        this.G = g;
    }

    /**
     * Getter for parent of node. Return type depends
     * on the overriding subclass.
     *
     * @param <T> subclass of Node
     * @return subclass of Node
     */
    public <T extends Node> T getParent() {
        return null;
    }

    /**
     * Getter for state.
     *
     * @return State
     */
    public State getState() {
        return this.state;
    }

    /**
     * Getter for action list.
     *
     * @return ArrayList of String
     */
    public ArrayList<String> getActionList() {
        return this.lastActionList;
    }

    /**
     * Getter for G-value.
     *
     * @return int
     */
    public int getG() {
        return this.G;
    }

    // For debugging
    @Override
    public String toString() {
        return "State: " + this.state.toString() + ", G: " + this.getG();
    }
}
