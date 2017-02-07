package searches.nodes;

import level.Position;

/**
 * Created by Jonni on 2/4/2017.
 *
 * Node for point path A* search. Its state is a position.
 */
public final class PreProcessNode {

    private PreProcessNode parent;
    private Position state;
    private int G;
    private int F;

    /**
     * F value is the cost (G) and the manhattan distance to the goal.
     *
     * @param parent PreProcessNode
     * @param state Position (from)
     * @param G int
     * @param B Position (to)
     */
    public PreProcessNode(PreProcessNode parent, Position state, int G, Position B) {
        this.parent = parent;
        this.state = state;
        this.G = G;
        this.F = G + state.manhattanDistance(B);
    }

    /**
     * Returns the parent of a node in a search tree.
     *
     * @return PreProcessNode
     */
    public PreProcessNode getParent() {
        return this.parent;
    }

    /**
     * Returns the position in the grid of a node.
     *
     * @return Position
     */
    public Position getState() {
        return this.state;
    }

    /**
     * Returns the cost of reaching the node.
     *
     * @return int
     */
    public int getG() {
        return this.G;
    }

    /**
     * Returns the combination of the cost and remaining cost estimate.
     *
     * @return int
     */
    public int getF() {
        return this.F;
    }
}
