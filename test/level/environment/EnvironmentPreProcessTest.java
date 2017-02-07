package level.environment;

import level.Orientation;
import level.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jonni on 2/6/2017.
 */
public class EnvironmentPreProcessTest {

    private EnvironmentPreProcess epp;

    @Before
    public void setUp() {
        // ..d
        // .oo
        // Aod

        Set<Position> dirt = new HashSet<>();
        dirt.add(new Position(3,3));
        dirt.add(new Position(3,1));

        Set<Position> obstacles = new HashSet<>();
        obstacles.add(new Position(2,1));
        obstacles.add(new Position(2,2));
        obstacles.add(new Position(3,2));

        Environment environment = new Environment(new Position(1,1), 3, 3, dirt, obstacles);

        this.epp = new EnvironmentPreProcess(environment);
    }

    @Test
    public void testReachable() {
        Set<Position> expected = new HashSet<>();
        expected.add(new Position(3,3));
        assertEquals(expected, this.epp.getReachable());
    }

    @Test
    public void weightTest() {
        assertEquals(new Integer(5), this.epp.getWeights().get(new Position(1,1)).get(new Orientation("NORTH")).get(new Position(3,3)));
        assertEquals(new Integer(7), this.epp.getWeights().get(new Position(1,1)).get(new Orientation("SOUTH")).get(new Position(3,3)));
        assertEquals(new Integer(6), this.epp.getWeights().get(new Position(1,1)).get(new Orientation("WEST")).get(new Position(3,3)));
        assertEquals(new Integer(6), this.epp.getWeights().get(new Position(1,1)).get(new Orientation("EAST")).get(new Position(3,3)));

        assertEquals(new Integer(6), this.epp.getWeights().get(new Position(3,3)).get(new Orientation("NORTH")).get(new Position(1,1)));
        assertEquals(new Integer(6), this.epp.getWeights().get(new Position(3,3)).get(new Orientation("SOUTH")).get(new Position(1,1)));
        assertEquals(new Integer(5), this.epp.getWeights().get(new Position(3,3)).get(new Orientation("WEST")).get(new Position(1,1)));
        assertEquals(new Integer(7), this.epp.getWeights().get(new Position(3,3)).get(new Orientation("EAST")).get(new Position(1,1)));
    }

    @Test
    public void pathTest() {
        ArrayList<Position> optimalPath = new ArrayList<>();
        optimalPath.add(new Position(1,1));
        optimalPath.add(new Position(1,2));
        optimalPath.add(new Position(1,3));
        optimalPath.add(new Position(2,3));
        optimalPath.add(new Position(3,3));
        assertEquals("From", optimalPath,
                this.epp.getPath(new Orientation("NORTH"), new Position(1,1), new Position(3,3))
        );
        Collections.reverse(optimalPath);
        assertEquals("To", optimalPath,
                this.epp.getPath(new Orientation("NORTH"), new Position(3,3), new Position(1,1))
        );
    }

    @Test
    public void pathTest2() {
        // When orientation can yield different paths
        // .d.
        // .o.
        // .a.

        Set<Position> dirt = new HashSet<>();
        dirt.add(new Position(2,3));
        Set<Position> obstacles = new HashSet<>();
        obstacles.add(new Position(2,2));

        Environment e = new Environment(new Position(2,1), 3,3, dirt, obstacles);
        EnvironmentPreProcess ep = new EnvironmentPreProcess(e);

        ArrayList<Position> westPath = new ArrayList<>();
        westPath.add(new Position(2,1));
        westPath.add(new Position(1,1));
        westPath.add(new Position(1,2));
        westPath.add(new Position(1,3));
        westPath.add(new Position(2,3));
        ArrayList<Position> eastPath = new ArrayList<>();
        eastPath.add(new Position(2,1));
        eastPath.add(new Position(3,1));
        eastPath.add(new Position(3,2));
        eastPath.add(new Position(3,3));
        eastPath.add(new Position(2,3));

        assertEquals(westPath, ep.getPath(new Orientation("WEST"), new Position(2,1), new Position(2,3)));
        assertEquals(eastPath, ep.getPath(new Orientation("EAST"), new Position(2,1), new Position(2,3)));
    }
}