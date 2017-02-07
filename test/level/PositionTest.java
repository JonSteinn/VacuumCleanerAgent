package level;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jonni on 2/6/2017.
 */
public class PositionTest {

    private Position position;
    private Orientation north;
    private Orientation east;
    private Orientation south;
    private Orientation west;

    @Before
    public void setUp() {
        this.position = new Position(15,33);
        this.north = new Orientation("NORTH");
        this.east = new Orientation("EAST");
        this.south = new Orientation("SOUTH");
        this.west = new Orientation("WEST");
    }

    @Test
    public void frontOfTest() {
        assertEquals("N", new Position(15,34), Position.frontOf(this.position, this.north));
        assertEquals("E", new Position(16,33), Position.frontOf(this.position, this.east));
        assertEquals("S", new Position(15,32), Position.frontOf(this.position, this.south));
        assertEquals("W", new Position(14,33), Position.frontOf(this.position, this.west));
    }

    @Test
    public void behindOfTest() {
        assertEquals("N", new Position(15,32), Position.behindOf(this.position, this.north));
        assertEquals("E", new Position(14,33), Position.behindOf(this.position, this.east));
        assertEquals("S", new Position(15,34), Position.behindOf(this.position, this.south));
        assertEquals("W", new Position(16,33), Position.behindOf(this.position, this.west));
    }

    @Test
    public void leftOfTest() {
        assertEquals("N", new Position(14,33), Position.leftOf(this.position, this.north));
        assertEquals("E", new Position(15,34), Position.leftOf(this.position, this.east));
        assertEquals("S", new Position(16,33), Position.leftOf(this.position, this.south));
        assertEquals("W", new Position(15,32), Position.leftOf(this.position, this.west));
    }

    @Test
    public void rightOfTest() {
        assertEquals("N", new Position(16,33), Position.rightOf(this.position, this.north));
        assertEquals("E", new Position(15,32), Position.rightOf(this.position, this.east));
        assertEquals("S", new Position(14,33), Position.rightOf(this.position, this.south));
        assertEquals("W", new Position(15,34), Position.rightOf(this.position, this.west));
    }

    @Test
    public void testGetters() {
        assertEquals("x", 15, this.position.getX());
        assertEquals("y", 33, this.position.getY());
    }

    @Test
    public void testCopyConstructor() {
        assertEquals(this.position, new Position(this.position));
    }

    @Test
    public void testGetDirectionTo() {
        for (Orientation o : Orientation.orientations()) {
            assertEquals(o.toString(), o, this.position.getDirectionTo(Position.frontOf(this.position, o)));
        }
    }

    @Test
    public void testManhattanDistance() {
        assertEquals(66 - 15 + 33 - 12, this.position.manhattanDistance(new Position(66, 12)));
    }

    @Test
    public void testEquals() {
        assertTrue("To self", this.position.equals(this.position));
        assertTrue("To copy", this.position.equals(new Position(this.position)));
        assertFalse(
                "Diff x",
                this.position.equals(new Position(this.position.getX() - 1, this.position.getY()))
        );
        assertFalse(
                "Diff y",
                this.position.equals(new Position(this.position.getX(), this.position.getY() - 1))
        );
        assertFalse(
                "Diff x,y",
                this.position.equals(new Position(this.position.getX() + 1, this.position.getY() - 1))
        );
    }
}