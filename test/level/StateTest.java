package level;

import level.environment.Environment;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Jonni on 2/6/2017.
 */
public class StateTest {

    private State state;
    private Environment environment;

    @Before
    public void setUp() {
        this.state = new State(new Orientation("NORTH"), new Position(1,1));
        Set<Position> obstacles = new HashSet<>();
        obstacles.add(new Position(1,2));
        obstacles.add(new Position(2,2));
        Set<Position> dirt = new HashSet<>();
        dirt.add(new Position(1,3));
        dirt.add(new Position(3,2));
        this.environment = new Environment(new Position(1,1), 3, 3, dirt, obstacles);
    }

    @Test
    public void testGetters() {
        assertEquals(new Orientation(Orientation.Direction.NORTH), this.state.getOrientation());
    }

    @Test
    public void heuristicTest() {
        // x..
        // oox
        // a..
        // Best possible way with regards to directions is 4+4 as minimal span tree
        assertEquals(8, this.state.heuristic(this.environment));
    }

    @Test
    public void reachableTest() {
        Set<Position> c1 = new HashSet<>();
        c1.add(new Position(3,2));
        State s1 = new State(new Orientation("NORTH"), new Position(3,2), c1);
        Actions a1 = new Actions(new ArrayList<>(), 0);
        a1.addAction(Actions.TURN_RIGHT);
        a1.addAction(Actions.GO);
        a1.addAction(Actions.GO);
        a1.addAction(Actions.TURN_LEFT);
        a1.addAction(Actions.GO);
        a1.addAction(Actions.SUCK);

        Set<Position> c2 = new HashSet<>();
        c2.add(new Position(1,3));
        State s2 = new State(new Orientation("WEST"), new Position(1,3), c2);
        Actions a2 = new Actions(new ArrayList<>(), 0);
        a2.addAction(Actions.TURN_RIGHT);
        a2.addAction(Actions.GO);
        a2.addAction(Actions.GO);
        a2.addAction(Actions.TURN_LEFT);
        a2.addAction(Actions.GO);
        a2.addAction(Actions.GO);
        a2.addAction(Actions.TURN_LEFT);
        a2.addAction(Actions.GO);
        a2.addAction(Actions.GO);
        a2.addAction(Actions.SUCK);

        Set<Map.Entry<State, Actions>> set = this.state.getReachableStates(this.environment).entrySet();
        Iterator<Map.Entry<State, Actions>> it = set.iterator();

        for (Map.Entry<State, Actions> entry : this.state.getReachableStates(this.environment).entrySet()) {
            if (entry.getKey().equals(s1)) {
                assertEquals("Cost of " + s1.toString(), a1.getCost(), entry.getValue().getCost());
                assertEquals("ActionList of " + s1.toString(), a1.getActionList(), entry.getValue().getActionList());
            } else if (entry.getKey().equals(s2)) {
                assertEquals("Cost of " + s2.toString(), a2.getCost(), entry.getValue().getCost());
                assertEquals("ActionList of" + s2.toString(), a2.getActionList(), entry.getValue().getActionList());
            } else {
                assertTrue("Did not find expected states", false);
            }
        }
    }

    @Test
    public void testEquals() {
        Set<Position> cleaned = new HashSet<>();
        cleaned.add(new Position(1,1));
        cleaned.add(new Position(1,2));
        cleaned.add(new Position(1,3));

        assertTrue("To self", this.state.equals(this.state));
        assertTrue("To copy", this.state.equals(new State(new Orientation("NORTH"), new Position(1,1))));
        assertTrue("To copy, with some cleaned",
                new State(new Orientation("WEST"), new Position(1,4), cleaned).equals(new State(new Orientation("WEST"), new Position(1,4), cleaned)));
        assertFalse("Diff or", this.state.equals(new State(new Orientation("SOUTH"), new Position(1,1))));
        assertFalse("Diff pos", this.state.equals(new State(new Orientation("NORTH"), new Position(3,3))));
        assertFalse("Diff cleaned", this.state.equals(new State(new Orientation("NORTH"), new Position(1,1), cleaned)));
    }
}