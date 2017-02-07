package level;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jonni on 2/6/2017.
 */
public class OrientationTest {

    private Orientation north;
    private Orientation east;
    private Orientation south;
    private Orientation west;

    @Before
    public void setUp() throws Exception {
        this.north = new Orientation(Orientation.Direction.NORTH);
        this.east = new Orientation(Orientation.Direction.EAST);
        this.south = new Orientation(Orientation.Direction.SOUTH);
        this.west = new Orientation(Orientation.Direction.WEST);
    }

    @Test
    public void iteratorTest() {
        int flags = 0x0;
        for (Orientation o : Orientation.orientations()) {
            switch (o.getOrientation()) {
                case EAST:
                    flags |= 0b1;
                    break;
                case NORTH:
                    flags |= 0b10;
                    break;
                case SOUTH:
                    flags |= 0b100;
                    break;
                case WEST:
                    flags |= 0b1000;
                    break;
                default:
                    flags |= 0b10000;
            }
        }
        assertEquals("All included", 0b1111, flags);
    }

    @Test
    public void testRightOf() {
        assertEquals("w", this.north, Orientation.rightOf(this.west));
        assertEquals("n", this.east, Orientation.rightOf(this.north));
        assertEquals("e", this.south, Orientation.rightOf(this.east));
        assertEquals("s", this.west, Orientation.rightOf(this.south));
    }

    @Test
    public void testLeftOf() {
        assertEquals("w", this.south, Orientation.leftOf(this.west));
        assertEquals("n", this.west, Orientation.leftOf(this.north));
        assertEquals("e", this.north, Orientation.leftOf(this.east));
        assertEquals("s", this.east, Orientation.leftOf(this.south));
    }

    @Test
    public void testCopy() {
        assertEquals(this.south, Orientation.copy(this.south));
    }

    @Test
    public void testConstructors() {
        assertEquals("Using enum", this.south, new Orientation(Orientation.Direction.SOUTH));
        assertEquals("Using string", this.west, new Orientation("WEST"));
    }

    @Test
    public void testTurnCost() {
        assertEquals("s-s", 0, this.south.turnCost(this.south));
        assertEquals("s-w", 1, this.south.turnCost(this.west));
        assertEquals("s-e", 1, this.south.turnCost(this.east));
        assertEquals("s-n", 2, this.south.turnCost(this.north));
        assertEquals("n-s", 2, this.north.turnCost(this.south));
        assertEquals("n-w", 1, this.north.turnCost(this.west));
        assertEquals("n-e", 1, this.north.turnCost(this.east));
        assertEquals("n-n", 0, this.north.turnCost(this.north));
        assertEquals("e-s", 1, this.east.turnCost(this.south));
        assertEquals("e-w", 2, this.east.turnCost(this.west));
        assertEquals("e-e", 0, this.east.turnCost(this.east));
        assertEquals("e-n", 1, this.east.turnCost(this.north));
        assertEquals("w-s", 1, this.west.turnCost(this.south));
        assertEquals("w-w", 0, this.west.turnCost(this.west));
        assertEquals("w-e", 2, this.west.turnCost(this.east));
        assertEquals("w-n", 1, this.west.turnCost(this.north));
    }

    @Test
    public void testTurningRight() {
        Orientation tmp = new Orientation("NORTH");
        for (int i = 0; i < (15 << 2) + 3; i++) tmp.turnRight();
        assertEquals(this.west, tmp);
    }

    @Test
    public void testTurningLeft() {
        Orientation tmp = new Orientation("EAST");
        for (int i = 0; i < (151 << 2) + 1; i++) tmp.turnLeft();
        assertEquals(this.north, tmp);
    }

    @Test
    public void testGetters() {
        assertEquals("N", Orientation.Direction.NORTH, this.north.getOrientation());
        assertEquals("E", Orientation.Direction.EAST, this.east.getOrientation());
        assertEquals("S", Orientation.Direction.SOUTH, this.south.getOrientation());
        assertEquals("W", Orientation.Direction.WEST, this.west.getOrientation());
    }

    @Test
    public void testEquals() {
        assertTrue("S", new Orientation("SOUTH").equals(new Orientation(Orientation.Direction.SOUTH)));
        assertTrue("N", new Orientation("NORTH").equals(new Orientation(Orientation.Direction.NORTH)));
        assertTrue("W", new Orientation("WEST").equals(new Orientation(Orientation.Direction.WEST)));
        assertTrue("E", new Orientation("EAST").equals(new Orientation(Orientation.Direction.EAST)));
    }
}