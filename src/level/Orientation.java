package level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonni on 1/27/2017.
 */
public class Orientation {

    // Nested enums are static and therefore require no additional overhead
    public enum Direction { NORTH, EAST, SOUTH, WEST }

    private static final ArrayList<Orientation> iterable;
    private static final Map<String, Direction> stringToEnum;
    private static final Map<Integer, Direction> intToEnum;
    static {
        stringToEnum = new HashMap<>();
        stringToEnum.put("NORTH", Direction.NORTH);
        stringToEnum.put("WEST", Direction.WEST);
        stringToEnum.put("SOUTH", Direction.SOUTH);
        stringToEnum.put("EAST", Direction.EAST);
        intToEnum = new HashMap<>();
        intToEnum.put(0, Direction.NORTH);
        intToEnum.put(1, Direction.EAST);
        intToEnum.put(2, Direction.SOUTH);
        intToEnum.put(3, Direction.WEST);
        iterable = new ArrayList<>();
        iterable.add(new Orientation(Direction.NORTH));
        iterable.add(new Orientation(Direction.EAST));
        iterable.add(new Orientation(Direction.SOUTH));
        iterable.add(new Orientation(Direction.WEST));
    }

    /**
     * An iterable for all possible orientations.
     *
     * @return ArrayList of Orientation
     */
    public static ArrayList<Orientation> orientations() {
        return Orientation.iterable;
    }

    /**
     * Returns the orientation to the right of argument.
     *
     * @param o Orientation
     * @return Orientation
     */
    public static Orientation rightOf(Orientation o) {
        Orientation retVal = new Orientation(o.getOrientation());
        retVal.turnRight();
        return retVal;
    }

    /**
     * Returns the orientation to the left of argument.
     *
     * @param o Orientation
     * @return Orientation
     */
    public static Orientation leftOf(Orientation o) {
        Orientation retVal = new Orientation(o.getOrientation());
        retVal.turnLeft();
        return retVal;
    }

    /**
     * Creates a new instance with the same value as the argument.
     *
     * @param o Orientation
     * @return Orientation
     */
    public static Orientation copy(Orientation o) {
        return new Orientation(o.getOrientation());
    }

    private int orientation;

    /**
     * Instance created from string (must be in caps).
     *
     * @param orientation String
     */
    public Orientation(String orientation) {
        this(stringToEnum.get(orientation));
    }

    /**
     * Instance create from enum.
     *
     * @param name Direction
     */
    public Orientation(Direction name) {
        switch (name) {
            case NORTH:
                this.orientation = 0;
                break;
            case EAST:
                this.orientation = 1;
                break;
            case SOUTH:
                this.orientation = 2;
                break;
            case WEST:
                this.orientation = 3;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Calculates the cost of turning from the current Orientation to the argument.
     *
     * @param other Orientation
     * @return Orientation
     */
    public int turnCost(Orientation other) {
        switch (this.getOrientation()) {
            case EAST:
                switch (other.getOrientation()) {
                    case EAST:
                        return 0;
                    case WEST:
                        return 2;
                    case SOUTH:
                        return 1;
                    case NORTH:
                        return 1;
                }
            case NORTH:
                switch (other.getOrientation()) {
                    case EAST:
                        return 1;
                    case WEST:
                        return 1;
                    case SOUTH:
                        return 2;
                    case NORTH:
                        return 0;
                }
            case SOUTH:
                switch (other.getOrientation()) {
                    case EAST:
                        return 1;
                    case WEST:
                        return 1;
                    case SOUTH:
                        return 0;
                    case NORTH:
                        return 2;
                }
            case WEST:
                switch (other.getOrientation()) {
                    case EAST:
                        return 2;
                    case WEST:
                        return 0;
                    case SOUTH:
                        return 1;
                    case NORTH:
                        return 1;
                }
        }
        return Integer.MAX_VALUE; // Unreachable
    }

    /**
     * Turn right from current orientation.
     */
    public void turnRight() {
        this.orientation = (this.orientation + 1) % 4;
    }

    /**
     * Turn left from current orientation.
     */
    public void turnLeft() {
        this.orientation = (this.orientation + 3) % 4;
    }

    /**
     * Returns the enum for the current orientation.
     *
     * @return Direction
     */
    public Direction getOrientation() {
        return intToEnum.get(this.orientation);
    }

    @Override
    public int hashCode() {
        return this.orientation;
    }

    // No null or type checks are done
    @Override
    public boolean equals(Object o) {
        return this == o || this.orientation == ((Orientation) o).orientation;
    }

    @Override
    public String toString() {
        return this.getOrientation().toString();
    }
}
