package gdl;

import game.PerceptsParser;
import level.Orientation;
import level.State;
import level.environment.Environment;
import org.junit.Before;
import org.junit.Test;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFirstSearch;
import searches.UniformCostSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jonni on 1/30/2017.
 */
public class Gdl1 {

    private Collection<String> percepts;
    private Environment env;
    private State state;
    private AStar aStar;
    private UniformCostSearch ucs;
    private BreadthFirstSearch bfs;
    private DepthFirstSearch dfs;

    @Before
    public void setUp() throws Exception {
        this.percepts = new ArrayList<>(
                Arrays.asList("(HOME 1 1)","(ORIENTATION NORTH)","(AT DIRT 1 3)","(AT DIRT 2 4)","(AT DIRT 4 1)","(AT DIRT 3 2)","(AT DIRT 5 5)","(AT OBSTACLE 1 2)","(AT OBSTACLE 3 3)","(AT OBSTACLE 3 4)","(AT OBSTACLE 3 5)","(AT OBSTACLE 5 3)","(SIZE 5 5)")
        );
        PerceptsParser.parse(this.percepts);
        this.env = PerceptsParser.getEnvironment();
        this.state = PerceptsParser.getInitialState();

        this.aStar = new AStar(this.env, this.state);
        this.ucs = new UniformCostSearch(this.env, this.state);
        this.bfs = new BreadthFirstSearch(this.env, this.state);
        this.dfs = new DepthFirstSearch(this.env, this.state);
    }

    @Test
    public void completenessAStarTest() {
        assertEquals("Complete A*", 42, this.aStar.getCost());
    }

    @Test
    public void goalStateAStarTest() {
        assertTrue(
                "Goal state A*",
                this.aStar.getGoalState().
                        equals(new State(new Orientation(
                                Orientation.Direction.NORTH),
                                env.getHome(),
                                this.env.notCleaned(new HashSet<>())
                        ))
                        ||
                        this.aStar.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.EAST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.aStar.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.SOUTH),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.aStar.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.WEST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
        );
    }

    @Test
    public void completenessUniformCostSearchTest() {
        assertEquals("Complete UCS", 42, this.ucs.getCost());
    }

    @Test
    public void goalStateUniformCostSearchTest() {
        assertTrue(
                "Goal state UCS",
                this.ucs.getGoalState().
                        equals(new State(new Orientation(
                                Orientation.Direction.NORTH),
                                env.getHome(),
                                this.env.notCleaned(new HashSet<>())
                        ))
                        ||
                        this.ucs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.EAST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.ucs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.SOUTH),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.ucs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.WEST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
        );
    }

    @Test
    public void goalStateBreadthFirstSearchTest() {
        assertTrue(
                "Goal state BFS",
                this.bfs.getGoalState().
                        equals(new State(new Orientation(
                                Orientation.Direction.NORTH),
                                env.getHome(),
                                this.env.notCleaned(new HashSet<>())
                        ))
                        ||
                        this.bfs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.EAST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.bfs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.SOUTH),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.bfs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.WEST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
        );
    }

    @Test
    public void goalStateDepthFirstSearchTest() {
        assertTrue(
                "Goal state DFS",
                this.dfs.getGoalState().
                        equals(new State(new Orientation(
                                Orientation.Direction.NORTH),
                                env.getHome(),
                                this.env.notCleaned(new HashSet<>())
                        ))
                        ||
                        this.dfs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.EAST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.dfs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.SOUTH),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
                        ||
                        this.dfs.getGoalState().
                                equals(new State(new Orientation(
                                        Orientation.Direction.WEST),
                                        env.getHome(),
                                        this.env.notCleaned(new HashSet<>())
                                ))
        );
    }
}