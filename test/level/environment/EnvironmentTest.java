package level.environment;

import level.Orientation;
import level.Position;
import level.State;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jonni on 2/6/2017.
 */
public class EnvironmentTest {

    private Environment environment;

    @Before
    public void setUp() {
        Position home = new Position(3, 6);
        int width = 20;
        int height = 40;
        Set<Position> dirt = new HashSet<>();
        dirt.add(new Position(15, 12));
        dirt.add(new Position(18, 36));
        dirt.add(new Position(1, 1));
        Set<Position> obstacle = new HashSet<>();
        obstacle.add(new Position(1,2));
        obstacle.add(new Position(19,39));
        obstacle.add(new Position(5,5));
        this.environment = new Environment(home, width, height, dirt, obstacle);
    }

    @Test
    public void testHeuristicTest() {
        Set<Position> remaining = new HashSet<>(this.environment.getDirt());
        remaining.add(this.environment.getHome());
        assertEquals(55, this.environment.getCachedHeuristics(remaining));
    }

    @Test
    public void isGoalStateTest() {
        for (Orientation o : Orientation.orientations()) {
            assertTrue(o.toString(),
                    this.environment.isGoalState(new State(o, this.environment.getHome(), this.environment.getDirt()))
            );
        }
    }

    @Test
    public void pathToTest() {
        ArrayList<Position> optmialPath = new ArrayList<>();
        optmialPath.add(new Position(3,6));
        optmialPath.add(new Position(2,6));
        optmialPath.add(new Position(2,5));
        optmialPath.add(new Position(2,4));
        optmialPath.add(new Position(2,3));
        optmialPath.add(new Position(2,2));
        optmialPath.add(new Position(2,1));
        optmialPath.add(new Position(1,1));
        assertEquals(
                optmialPath,
                this.environment.getPath(
                        new Orientation("NORTH"),
                        this.environment.getHome(),
                        new Position(1,1)
                )
        );
    }

    @Test
    public void homeTest() {
        assertEquals(new Position(3,6), this.environment.getHome());
        assertTrue(this.environment.isHome(new Position(3,6)));
    }

    @Test
    public void getDirtTest() {
        Set<Position> dirt = new HashSet<>();
        dirt.add(new Position(15, 12));
        dirt.add(new Position(18, 36));
        dirt.add(new Position(1, 1));
        assertEquals(dirt, this.environment.getDirt());
    }

    @Test
    public void getNotCleanedTest() {
        Set<Position> cleaned = new HashSet<>();
        cleaned.add(new Position(15, 12));
        cleaned.add(new Position(1, 1));
        Set<Position> notCleaned = new HashSet<>();
        notCleaned.add(new Position(18, 36));
        assertEquals(notCleaned, this.environment.notCleaned(cleaned));
    }

    @Test
    public void isReachableTest() {
        assertFalse("n", this.environment.isReachable(new Position(22,1)));
        assertFalse("s", this.environment.isReachable(new Position(0,1)));
        assertFalse("e", this.environment.isReachable(new Position(4,41)));
        assertFalse("w", this.environment.isReachable(new Position(5,0)));
        assertFalse("obst.", this.environment.isReachable(new Position(1,2)));
        assertTrue("reachable (home)", this.environment.isReachable(environment.getHome()));
        assertTrue("reachable (20,40)", this.environment.isReachable(new Position(20,40)));
    }

    @Test
    public void getSizeTest() {
        assertEquals("W", 20, this.environment.getWidth());
        assertEquals("H", 40, this.environment.getHeight());
    }

    @Test
    public void getObstacles() {
        Set<Position> obstacle = new HashSet<>();
        obstacle.add(new Position(1,2));
        obstacle.add(new Position(19,39));
        obstacle.add(new Position(5,5));
        assertEquals(obstacle, this.environment.getObstacles());
    }

    @Test
    public void distanceFromToTest() {
        assertEquals("N", 27, this.environment.distanceFromTo(new Orientation("NORTH"), new Position(1,1), new Position(15,12)));
        assertEquals("E", 26, this.environment.distanceFromTo(new Orientation("EAST"), new Position(1,1), new Position(15,12)));
        assertEquals("S", 27, this.environment.distanceFromTo(new Orientation("SOUTH"), new Position(1,1), new Position(15,12)));
        assertEquals("W", 28, this.environment.distanceFromTo(new Orientation("WEST"), new Position(1,1), new Position(15,12)));
    }
}