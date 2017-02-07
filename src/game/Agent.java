package game;

import java.util.Collection;

public interface Agent
{
    public void init(Collection<String> percepts);
    public String nextAction(Collection<String> percepts);
}
