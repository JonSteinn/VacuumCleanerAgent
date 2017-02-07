package level;

/**
 * Created by Jonni on 1/27/2017.
 *
 * A 2D point for coordinates in the grid.
 */
public class Position {

    /**
     * Returns the position in front of the current one, given a direction.
     *
     * @param pos Position
     * @param or Orientation
     * @return Position
     */
    public static Position frontOf(Position pos, Orientation or) {
        switch (or.getOrientation()) {
            case NORTH:
                return new Position(pos.x, pos.y + 1);
            case EAST:
                return new Position(pos.x + 1, pos.y);
            case SOUTH:
                return new Position(pos.x, pos.y - 1);
            case WEST:
                return new Position(pos.x - 1, pos.y);
            default:
        }
        return null;
    }

    /**
     * Returns the position behind of the current one, given a direction.
     *
     * @param pos Position
     * @param or Orientation
     * @return Position
     */
    public static Position behindOf(Position pos, Orientation or) {
        switch (or.getOrientation()) {
            case NORTH:
                return new Position(pos.x, pos.y - 1);
            case EAST:
                return new Position(pos.x - 1, pos.y);
            case SOUTH:
                return new Position(pos.x, pos.y + 1);
            case WEST:
                return new Position(pos.x + 1, pos.y);
            default:
        }
        return null;
    }

    /**
     * Returns the position to the left of the current one, given a direction.
     *
     * @param pos Position
     * @param or Orientation
     * @return Position
     */
    public static Position leftOf(Position pos, Orientation or) {
        return frontOf(pos, Orientation.leftOf(or));
    }

    /**
     * Returns the position to the right of the current one, given a direction.
     *
     * @param pos Position
     * @param or Orientation
     * @return Position
     */
    public static Position rightOf(Position pos, Orientation or) {
        return frontOf(pos, Orientation.rightOf(or));
    }

    private int x;
    private int y;

    /**
     * Instance of (x,y) coordinates.
     *
     * @param x int
     * @param y int
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instance created from another's values.
     *
     * @param p Position
     */
    public Position(Position p) {
        this(p.x, p.y);
    }

    /**
     * Getter for x coordinate.
     *
     * @return int
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for y coordinate.
     *
     * @return int
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the direction to a given position. Assumes they only
     * differ in one and is only used for adjacent squares.
     *
     * @param to Position
     * @return Orientation
     */
    public Orientation getDirectionTo(Position to) {
        if (this.x < to.x) return new Orientation(Orientation.Direction.EAST);
        if (this.x > to.x) return new Orientation(Orientation.Direction.WEST);
        if (this.y > to.y) return new Orientation(Orientation.Direction.SOUTH);
        if (this.y < to.y) return new Orientation(Orientation.Direction.NORTH);
        return null;
    }

    /**
     * Returns the manhattan distance to another position.
     *
     * @param p Position
     * @return int
     */
    public int manhattanDistance(Position p) {
        return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = (hash << 5) - hash + this.x;
        hash = (hash << 5) - hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        Position other = (Position)o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
