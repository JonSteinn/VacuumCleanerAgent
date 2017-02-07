package searches.nodes;

import game.PerceptsParser;
import level.Actions;
import level.State;
import level.environment.Environment;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Jonni on 2/6/2017.
 */
public class HeuristicNodeTest {

    @Test
    public void consistentHeuristicTest() {
        Collection<String> percepts = new ArrayList<>(
            Arrays.asList("(HOME 1 1)","(ORIENTATION NORTH)","(AT DIRT 1 3)","(AT DIRT 2 4)","(AT DIRT 4 1)","(AT DIRT 3 2)","(AT DIRT 5 5)","(AT OBSTACLE 1 2)","(AT OBSTACLE 3 3)","(AT OBSTACLE 3 4)","(AT OBSTACLE 3 5)","(AT OBSTACLE 5 3)","(SIZE 5 5)")
        );
        PerceptsParser.parse(percepts);
        Environment env = PerceptsParser.getEnvironment();
        State state = PerceptsParser.getInitialState();
        Set<State> states = new HashSet<>();
        Queue<HeuristicNode> op = new LinkedList<>();
        op.add(new HeuristicNode(null, state, null, 0, env));
        boolean valid = true;
        // State space is 119
        while (states.size() < 119 && valid) {
            HeuristicNode curr = op.poll();
            states.add(curr.getState());
            for (Map.Entry<State, Actions> entry : curr.getState().getReachableStates(env).entrySet()) {
                HeuristicNode child = new HeuristicNode(curr, entry.getKey(), entry.getValue().getActionList(), curr.getG() +  entry.getValue().getCost(), env);
                op.add(child);
                if (child.getF() < curr.getF()) {
                    valid = false;
                }
            }
        }
        assertTrue("Is consistent", valid);
    }
}