package level;

import java.util.ArrayList;

/**
 * Created by Jonni on 1/27/2017.
 *
 * A tuple of action list and their total cost.
 */
public class Actions {

    // Predefined actions allocated in memory and reused so we only need to store references
    public static final String TURN_ON = "TURN_ON";
    public static final String TURN_OFF = "TURN_OFF";
    public static final String TURN_RIGHT = "TURN_RIGHT";
    public static final String TURN_LEFT = "TURN_LEFT";
    public static final String GO = "GO";
    public static final String SUCK = "SUCK";

    private ArrayList<String> actionList;
    private int cost;

    /**
     * @param actions ArrayList of String
     * @param cost int
     */
    public Actions(ArrayList<String> actions, int cost) {
        this.actionList = actions;
        this.cost = cost;
    }

    /**
     * Getter for action list.
     *
     * @return ArrayList of String
     */
    public ArrayList<String> getActionList() {
        return this.actionList;
    }

    /**
     * Getter for total cost of action list.
     *
     * @return int
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Adds an action to the action list.
     *
     * @param action String
     */
    public void addAction(String action) {
        this.actionList.add(action);
        this.cost++;
    }
}
