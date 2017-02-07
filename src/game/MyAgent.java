package game;

import level.State;
import level.environment.Environment;
import searches.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by Jonni on 1/28/2017.
 */
public class MyAgent implements Agent {

    private List<String> actions;
    private int index;

    @Override
    public void init(Collection<String> percepts) {
        PerceptsParser.parse(percepts);
        Environment env = PerceptsParser.getEnvironment();
        State init = PerceptsParser.getInitialState();

        /* ****************************** */
        /* Change for different algorithm */
        /* ****************************** */
        Search search =
                new AStar(env, init);
        //        new UniformCostSearch(env, init);
        //        new BreadthFirstSearch(env, init);
        //        new DepthFirstSearch(env, init);

        this.actions = search.getActionSequence();
        this.index = 0;
    }
    @Override
    public String nextAction(Collection<String> percepts) {
        return this.actions.get(index++);
    }
}
