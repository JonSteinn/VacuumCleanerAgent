package game;

import level.Orientation;
import level.Position;
import level.State;
import level.environment.Environment;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jonni on 1/28/2017.
 */
public class PerceptsParserTest {

    private Collection<String> percepts;

    @Before
    public void setUp() throws Exception {
        this.percepts = new HashSet<>();
        this.percepts.add("(HOME 1 1)");
        this.percepts.add("(ORIENTATION NORTH)");
        this.percepts.add("(AT DIRT 1 3)");
        this.percepts.add("(AT DIRT 2 4)");
        this.percepts.add("(AT DIRT 4 1)");
        this.percepts.add("(AT DIRT 3 2)");
        this.percepts.add("(AT DIRT 5 5)");
        this.percepts.add("(AT OBSTACLE 1 2)");
        this.percepts.add("(AT OBSTACLE 3 3)");
        this.percepts.add("(AT OBSTACLE 3 4)");
        this.percepts.add("(AT OBSTACLE 3 5)");
        this.percepts.add("(AT OBSTACLE 5 3)");
        this.percepts.add("(SIZE 5 5)");
    }

    @Test
    public void parseTest() {
        Position home = new Position(1, 1);
        int width = 5;
        int height = 5;
        Set<Position> dirt = new HashSet<>();
        dirt.add(new Position(1,3));
        dirt.add(new Position(2,4));
        dirt.add(new Position(4,1));
        dirt.add(new Position(3,2));
        dirt.add(new Position(5,5));
        Set<Position> obstacles = new HashSet<>();
        obstacles.add(new Position(1,2));
        obstacles.add(new Position(3,3));
        obstacles.add(new Position(3,4));
        obstacles.add(new Position(3,5));
        obstacles.add(new Position(5,3));
        Environment environment = new Environment(home, width, height, dirt, obstacles);
        Orientation orientation = new Orientation(Orientation.Direction.NORTH);

        PerceptsParser.parse(this.percepts);
        assertEquals("Environment (home)", environment.getHome(), PerceptsParser.getEnvironment().getHome());
        assertEquals("Environment (width)", environment.getWidth(), PerceptsParser.getEnvironment().getWidth());
        assertEquals("Environment (height)", environment.getHeight(), PerceptsParser.getEnvironment().getHeight());
        assertEquals("Environment (dirt)", environment.getDirt(), PerceptsParser.getEnvironment().getDirt());
        assertEquals("Environment (obstacles)", environment.getObstacles(), PerceptsParser.getEnvironment().getObstacles());
        assertEquals("State",  new State(orientation, home), PerceptsParser.getInitialState());
    }

}