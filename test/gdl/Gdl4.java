package gdl;

import game.PerceptsParser;
import level.Orientation;
import level.State;
import level.environment.Environment;
import level.environment.EnvironmentPreProcess;
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
public class Gdl4 {

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
                Arrays.asList("(ORIENTATION NORTH)","(HOME 1 38)","(AT DIRT 14 22)","(AT DIRT 10 2)","(AT DIRT 6 19)","(AT DIRT 7 26)","(AT DIRT 11 10)","(AT DIRT 4 20)","(AT DIRT 20 34)","(AT DIRT 4 7)","(AT DIRT 6 27)","(AT DIRT 20 35)","(AT OBSTACLE 4 40)","(AT OBSTACLE 5 40)","(AT OBSTACLE 12 40)","(AT OBSTACLE 13 40)","(AT OBSTACLE 14 40)","(AT OBSTACLE 15 40)","(AT OBSTACLE 16 40)","(AT OBSTACLE 17 40)","(AT OBSTACLE 18 40)","(AT OBSTACLE 19 40)","(AT OBSTACLE 12 39)","(AT OBSTACLE 13 39)","(AT OBSTACLE 3 38)","(AT OBSTACLE 4 38)","(AT OBSTACLE 5 38)","(AT OBSTACLE 6 38)","(AT OBSTACLE 7 38)","(AT OBSTACLE 8 38)","(AT OBSTACLE 12 38)","(AT OBSTACLE 18 38)","(AT OBSTACLE 19 38)","(AT OBSTACLE 1 37)","(AT OBSTACLE 2 37)","(AT OBSTACLE 3 37)","(AT OBSTACLE 4 37)","(AT OBSTACLE 5 37)","(AT OBSTACLE 6 37)","(AT OBSTACLE 7 37)","(AT OBSTACLE 8 37)","(AT OBSTACLE 18 37)","(AT OBSTACLE 19 37)","(AT OBSTACLE 2 36)","(AT OBSTACLE 5 36)","(AT OBSTACLE 6 36)","(AT OBSTACLE 8 36)","(AT OBSTACLE 18 36)","(AT OBSTACLE 19 36)","(AT OBSTACLE 2 35)","(AT OBSTACLE 5 35)","(AT OBSTACLE 6 35)","(AT OBSTACLE 8 35)","(AT OBSTACLE 12 35)","(AT OBSTACLE 8 34)","(AT OBSTACLE 12 34)","(AT OBSTACLE 14 34)","(AT OBSTACLE 15 34)","(AT OBSTACLE 16 34)","(AT OBSTACLE 17 34)","(AT OBSTACLE 18 34)","(AT OBSTACLE 19 34)","(AT OBSTACLE 3 33)","(AT OBSTACLE 4 33)","(AT OBSTACLE 8 33)","(AT OBSTACLE 12 33)","(AT OBSTACLE 14 33)","(AT OBSTACLE 15 33)","(AT OBSTACLE 16 33)","(AT OBSTACLE 17 33)","(AT OBSTACLE 18 33)","(AT OBSTACLE 19 33)","(AT OBSTACLE 3 32)","(AT OBSTACLE 4 32)","(AT OBSTACLE 8 32)","(AT OBSTACLE 12 32)","(AT OBSTACLE 14 32)","(AT OBSTACLE 15 32)","(AT OBSTACLE 16 32)","(AT OBSTACLE 17 32)","(AT OBSTACLE 18 32)","(AT OBSTACLE 19 32)","(AT OBSTACLE 12 31)","(AT OBSTACLE 14 31)","(AT OBSTACLE 15 31)","(AT OBSTACLE 16 31)","(AT OBSTACLE 17 31)","(AT OBSTACLE 18 31)","(AT OBSTACLE 19 31)","(AT OBSTACLE 1 30)","(AT OBSTACLE 12 30)","(AT OBSTACLE 13 30)","(AT OBSTACLE 14 30)","(AT OBSTACLE 15 30)","(AT OBSTACLE 16 30)","(AT OBSTACLE 17 30)","(AT OBSTACLE 18 30)","(AT OBSTACLE 19 30)","(AT OBSTACLE 20 30)","(AT OBSTACLE 1 29)","(AT OBSTACLE 7 29)","(AT OBSTACLE 8 29)","(AT OBSTACLE 12 29)","(AT OBSTACLE 14 29)","(AT OBSTACLE 17 29)","(AT OBSTACLE 18 29)","(AT OBSTACLE 8 28)","(AT OBSTACLE 12 28)","(AT OBSTACLE 14 28)","(AT OBSTACLE 17 28)","(AT OBSTACLE 18 28)","(AT OBSTACLE 20 28)","(AT OBSTACLE 2 27)","(AT OBSTACLE 3 27)","(AT OBSTACLE 4 27)","(AT OBSTACLE 8 27)","(AT OBSTACLE 12 27)","(AT OBSTACLE 2 26)","(AT OBSTACLE 3 26)","(AT OBSTACLE 4 26)","(AT OBSTACLE 8 26)","(AT OBSTACLE 12 26)","(AT OBSTACLE 15 26)","(AT OBSTACLE 16 26)","(AT OBSTACLE 2 25)","(AT OBSTACLE 3 25)","(AT OBSTACLE 4 25)","(AT OBSTACLE 8 25)","(AT OBSTACLE 12 25)","(AT OBSTACLE 15 25)","(AT OBSTACLE 16 25)","(AT OBSTACLE 2 24)","(AT OBSTACLE 3 24)","(AT OBSTACLE 4 24)","(AT OBSTACLE 8 24)","(AT OBSTACLE 20 24)","(AT OBSTACLE 1 23)","(AT OBSTACLE 2 23)","(AT OBSTACLE 3 23)","(AT OBSTACLE 4 23)","(AT OBSTACLE 5 23)","(AT OBSTACLE 6 23)","(AT OBSTACLE 7 23)","(AT OBSTACLE 8 23)","(AT OBSTACLE 20 23)","(AT OBSTACLE 1 22)","(AT OBSTACLE 2 22)","(AT OBSTACLE 3 22)","(AT OBSTACLE 4 22)","(AT OBSTACLE 5 22)","(AT OBSTACLE 8 22)","(AT OBSTACLE 12 22)","(AT OBSTACLE 1 21)","(AT OBSTACLE 2 21)","(AT OBSTACLE 3 21)","(AT OBSTACLE 4 21)","(AT OBSTACLE 5 21)","(AT OBSTACLE 8 21)","(AT OBSTACLE 12 21)","(AT OBSTACLE 8 20)","(AT OBSTACLE 12 20)","(AT OBSTACLE 15 20)","(AT OBSTACLE 16 20)","(AT OBSTACLE 17 20)","(AT OBSTACLE 20 20)","(AT OBSTACLE 2 19)","(AT OBSTACLE 12 19)","(AT OBSTACLE 15 19)","(AT OBSTACLE 16 19)","(AT OBSTACLE 17 19)","(AT OBSTACLE 20 19)","(AT OBSTACLE 1 18)","(AT OBSTACLE 2 18)","(AT OBSTACLE 3 18)","(AT OBSTACLE 12 18)","(AT OBSTACLE 15 18)","(AT OBSTACLE 16 18)","(AT OBSTACLE 17 18)","(AT OBSTACLE 20 18)","(AT OBSTACLE 1 17)","(AT OBSTACLE 2 17)","(AT OBSTACLE 3 17)","(AT OBSTACLE 5 17)","(AT OBSTACLE 8 17)","(AT OBSTACLE 12 17)","(AT OBSTACLE 15 17)","(AT OBSTACLE 16 17)","(AT OBSTACLE 17 17)","(AT OBSTACLE 1 16)","(AT OBSTACLE 2 16)","(AT OBSTACLE 3 16)","(AT OBSTACLE 8 16)","(AT OBSTACLE 12 16)","(AT OBSTACLE 13 16)","(AT OBSTACLE 14 16)","(AT OBSTACLE 15 16)","(AT OBSTACLE 16 16)","(AT OBSTACLE 17 16)","(AT OBSTACLE 18 16)","(AT OBSTACLE 19 16)","(AT OBSTACLE 20 16)","(AT OBSTACLE 8 15)","(AT OBSTACLE 12 15)","(AT OBSTACLE 2 14)","(AT OBSTACLE 8 14)","(AT OBSTACLE 12 14)","(AT OBSTACLE 14 14)","(AT OBSTACLE 15 14)","(AT OBSTACLE 16 14)","(AT OBSTACLE 17 14)","(AT OBSTACLE 18 14)","(AT OBSTACLE 19 14)","(AT OBSTACLE 8 13)","(AT OBSTACLE 12 13)","(AT OBSTACLE 14 13)","(AT OBSTACLE 15 13)","(AT OBSTACLE 16 13)","(AT OBSTACLE 17 13)","(AT OBSTACLE 18 13)","(AT OBSTACLE 19 13)","(AT OBSTACLE 1 12)","(AT OBSTACLE 2 12)","(AT OBSTACLE 3 12)","(AT OBSTACLE 6 12)","(AT OBSTACLE 7 12)","(AT OBSTACLE 8 12)","(AT OBSTACLE 12 12)","(AT OBSTACLE 18 12)","(AT OBSTACLE 19 12)","(AT OBSTACLE 12 11)","(AT OBSTACLE 13 11)","(AT OBSTACLE 14 11)","(AT OBSTACLE 18 11)","(AT OBSTACLE 19 11)","(AT OBSTACLE 12 10)","(AT OBSTACLE 13 10)","(AT OBSTACLE 14 10)","(AT OBSTACLE 18 10)","(AT OBSTACLE 19 10)","(AT OBSTACLE 12 9)","(AT OBSTACLE 5 8)","(AT OBSTACLE 12 8)","(AT OBSTACLE 15 8)","(AT OBSTACLE 16 8)","(AT OBSTACLE 17 8)","(AT OBSTACLE 15 7)","(AT OBSTACLE 16 7)","(AT OBSTACLE 17 7)","(AT OBSTACLE 4 6)","(AT OBSTACLE 5 6)","(AT OBSTACLE 6 6)","(AT OBSTACLE 20 6)","(AT OBSTACLE 2 5)","(AT OBSTACLE 4 5)","(AT OBSTACLE 5 5)","(AT OBSTACLE 6 5)","(AT OBSTACLE 8 5)","(AT OBSTACLE 18 5)","(AT OBSTACLE 19 5)","(AT OBSTACLE 4 4)","(AT OBSTACLE 5 4)","(AT OBSTACLE 6 4)","(AT OBSTACLE 16 4)","(AT OBSTACLE 17 4)","(AT OBSTACLE 2 3)","(AT OBSTACLE 4 3)","(AT OBSTACLE 5 3)","(AT OBSTACLE 6 3)","(AT OBSTACLE 8 3)","(AT OBSTACLE 14 3)","(AT OBSTACLE 15 3)","(AT OBSTACLE 12 2)","(AT OBSTACLE 13 2)","(AT OBSTACLE 5 1)","(AT OBSTACLE 10 1)","(AT OBSTACLE 11 1)","(SIZE 20 40)")
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
        assertEquals("Complete A*", 202, this.aStar.getCost());
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
        assertEquals("Complete UCS", 202, this.ucs.getCost());
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
