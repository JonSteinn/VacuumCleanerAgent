package searches.nodes;

import game.PerceptsParser;
import level.Orientation;
import level.Position;
import level.environment.Environment;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Jonni on 2/6/2017.
 */
public class PreProcessNodeTest {

    @Test
    public void consistentHeuristicTest() {
        Collection<String> percepts = new ArrayList<>(
                Arrays.asList("(HOME 1 1)","(ORIENTATION NORTH)","(AT DIRT 1 3)","(AT DIRT 2 4)","(AT DIRT 4 1)","(AT DIRT 3 2)","(AT DIRT 5 5)","(AT OBSTACLE 1 2)","(AT OBSTACLE 3 3)","(AT OBSTACLE 3 4)","(AT OBSTACLE 3 5)","(AT OBSTACLE 5 3)","(SIZE 5 5)")
        );
        PerceptsParser.parse(percepts);
        Environment env = PerceptsParser.getEnvironment();

        for (Orientation initOr : Orientation.orientations()) {
            Set<Position> states = new HashSet<>();
            Queue<PreProcessNode> op = new LinkedList<>();
            op.add(new PreProcessNode(null, new Position(1, 1), 0, new Position(5, 5)));
            boolean valid = true;
            // State space is 119
            while (states.size() < 20 && valid) {

                PreProcessNode current = op.poll();
                if (states.contains(current.getState())) continue;
                states.add(current.getState());

                Position[] children = {
                        Position.frontOf(current.getState(), new Orientation(Orientation.Direction.NORTH)),
                        Position.behindOf(current.getState(), new Orientation(Orientation.Direction.NORTH)),
                        Position.leftOf(current.getState(), new Orientation(Orientation.Direction.NORTH)),
                        Position.rightOf(current.getState(), new Orientation(Orientation.Direction.NORTH))
                };
                for (Position child : children) {
                    if (env.isReachable(child)) {
                        int g = getG(current, child, initOr);
                        PreProcessNode childN = new PreProcessNode(current, child, current.getG() + g, new Position(5, 5));
                        if (childN.getF() < current.getF()) valid = false;
                        op.add(childN);
                    }
                }
            }
            assertTrue("Is consistent (" + initOr.toString() + ")", valid);
        }
    }

    // From PreProcessAStar
    private int getG(PreProcessNode current, Position child, Orientation init) {
        return 1 + ((current.getParent() == null) ?
                init.turnCost(current.getState().getDirectionTo(child)) :
                current
                        .getParent()
                        .getState()
                        .getDirectionTo(current.getState())
                        .turnCost(current.getState().getDirectionTo(child)));
    }
}