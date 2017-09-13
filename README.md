# Path finding vacuum cleaner agent 
Made for a course in AI at Reykjavík University in 2017. 

## [![Build Status](https://travis-ci.org/JonSteinn/VacuumCleanerAgent.svg?branch=master)](https://travis-ci.org/JonSteinn/VacuumCleanerAgent)

## Problem
The problem description can be read in full in [description.pdf](https://github.com/JonSteinn/VacuumCleanerAgent/raw/master/description.pdf). I do not own this assignment.

The environment is a grid of cells, where each can contain nothing, dirt or an obstacle and we are to implement an agent that, given full knowledge of the environment, can clean all dirt and return to its original position. Cells with dirt can be blocked from the agent due to obstacles. The actions available to the agent and their cost can be seen in the following table.

| Action | Cost |
| -- | -- |
| Turn on | 1 |
| Turn left | 1 |
| Turn right | 1 |
| Go | 1 |
| Suck | 1 for cells containing dirt, 5 otherwise |
| Turn off | 1 + 50D if at home, 100 + 50D othwerise, where D are remainig dirt |

The main function starts a client and we are given an interface for our agent with a initilization method (which gets all information about the environment) and next action method. The server is sent the next action from the client until the simulation is over.

## Stats
Fastest handin that year. Stats can be found [here](https://docs.google.com/spreadsheets/d/1pYKJ_a5K4XxxZ1q8YwL4wYrWxpgpm36-Zy5e_yazva0/edit?usp=sharing).

## Server and client
A server and client is provided with this project along with 10 maps. I have converted all of those into JUnit tests so it should suffice to run the test. I do not own these maps, the client or the server.

## Sedgewick and Wayne's algorithms
I borrowed a Graph datastructure, heap and a MST algorithm from Sedgewick and Wayne. All of which are in a package called algorithms, which I do not own!

## Project structure
    .
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar
    │       └── gradle-wrapper.properties
    ├── src
    │   ├── algorithms
    │   │   ├── Edge.java
    │   │   ├── EdgeWeightedGraph.java
    │   │   ├── IndexMinPQ.java
    │   │   └── PrimMST.java
    │   ├── game
    │   │   ├── Agent.java
    │   │   ├── GamePlayer.java
    │   │   ├── MyAgent.java
    │   │   ├── NanoHTTPD.java
    │   │   └── PerceptsParser.java
    │   ├── level
    │   │   ├── environment
    │   │   │   ├── Environment.java
    │   │   │   ├── EnvironmentPreProcess.java
    │   │   │   └── HeuristicCache.java
    │   │   ├── Actions.java
    │   │   ├── Orientation.java
    │   │   ├── Position.java
    │   │   └── State.java
    │   ├── searches
    │   │   ├── nodes
    │   │   │   ├── CostNode.java
    │   │   │   ├── HeuristicNode.java
    │   │   │   ├── Node.java
    │   │   │   └── PreProcessNode.java
    │   │   ├── AStar.java
    │   │   ├── BreadthFirstSearch.java
    │   │   ├── DepthFirstSearch.java
    │   │   ├── PreProcessAStar.java
    │   │   ├── Search.java
    │   │   └── UniformCostSearch.java
    │   └── Main.java
    ├── test
    │   ├── game
    │   │   └── PerceptsParserTest.java
    │   ├── gdl
    │   │   ├── Gdl1.java
    │   │   ├── Gdl2.java
    │   │   ├── Gdl3.java
    │   │   ├── Gdl4.java
    │   │   ├── Gdl5.java
    │   │   ├── Gdl6.java
    │   │   ├── Gdl7.java
    │   │   ├── Gdl8.java
    │   │   ├── Gdl9.java
    │   │   └── Gdl10.java
    │   ├── level
    │   │   │── environment
    │   │   │   └── EnvironmentPreProcessTest.java
    │   │   │   └── EnvironmentTest.java
    │   │   │   └── HeuristicCacheTest.java
    │   │   ├── ActionsTest.java
    │   │   ├── OrientationTest.java
    │   │   ├── PositionTest.java
    │   │   └── StateTest.java
    │   └── searches
    │       ├── HeuristicNodeTest.java
    │       └── PreProcessNodeTest.java
    ├── .gitignore
    ├── .travis.yml
    ├── AUTHORS
    ├── LICENSE
    ├── README.md
    ├── build.gradle
    ├── description.pdf
    ├── gradlew
    └── gradlew.bat

## Overview of approach
The code is thoroughly tested and all test environments have been set up as unit test so I never actually used the GDLs, server and client during development.

### Agent
My agent begins by parsing the information sent at start to construct instance of its initial state and environment. It then performs what search is chosen (see below). The results are then stored in a list and fed, one at a time, in each call for next action.

```Java
/* ****************************** */
/* Change for different algorithm */
/* ****************************** */
Search search =
        new AStar(env, init);
//        new UniformCostSearch(env, init);
//        new BreadthFirstSearch(env, init);
//        new DepthFirstSearch(env, init);
```

### Preprocessing
The first thing I did was to preprocess the environment. I used A* with manhattan distance heuristic to find all reachable dirts from our original position and disregarded those that weren't reachable. I also stored the minimal cost and path of going from A given some orientation O (at A) to B, where A and B are not equal and A and B are any of the reachable dirts or home cell. 

Now we know the best way to travel from one dirt (or home) to any reachable dirt (or home) given any initial direction so this problem has become a choice of which dirt to travel to next, rather than which actions available to the agent, to use next. That is, we have transformed this problem into the traveling salesman problem.

### State
The state contains the agent's orientation and position as well as the position it has already cleaned.

```Java
public class State {
    private Orientation orientation;
    private Position agentPosition;
    private Set<Position> cleaned;
}
```

### Heuristic
The heuristic used is the minimal span tree of all the remaining dirty cells along with the home position which to we add the shortest distance from the agent current position (already cleaned) to the MST.

### Heuristic caching
Since states differ in agent's orientation but the MST does not, we do not want to recalculate it just because of orientation. Therefore we use a cache. Initially empty, whenever we compute the MST for any set of position we cache it and use it for all scenarios with those positions. That is returned and the agent's shortest path to the MST is added to it, which depends on his orientation (while the MST does not).

### A*
The following shows the A* used in the search. It has been pruned of statistical gatherings and comments. It keeps a closed set which are never added to the frontier if there. Also, if state is already in frontier, we disregard the more expansive node.

```Java
public class AStar extends Search {
    public AStar(Environment env, State state) {
        Set<State> closed = new HashSet<>();
        PriorityQueue<HeuristicNode> openSet = new PriorityQueue<>(Comparator.comparingInt(HeuristicNode::getF));
        Map<State, Integer> openTracker = new HashMap<>();
        openSet.add(new HeuristicNode(null, state, null, 0, env));
        openTracker.put(state, 0);
        while (!openSet.isEmpty()) {
            HeuristicNode current = openSet.poll();
            openTracker.remove(current.getState());
            closed.add(current.getState());
            if (env.isGoalState(current.getState())) {
                goalNode = current;
                break;
            }
            for (Map.Entry<State, Actions> child : current.getState().getReachableStates(env).entrySet()) {
                if (!closed.contains(child.getKey())) {
                    Integer i = openTracker.get(child.getKey());
                    if (i == null) {
                        openTracker.put(
                                child.getKey(),
                                current.getG() + child.getValue().getCost()
                        );
                        openSet.add(new HeuristicNode(
                                current,
                                child.getKey(),
                                child.getValue().getActionList(),
                                current.getG() + child.getValue().getCost(),
                                env
                        ));
                    } else if (i > current.getG() + child.getValue().getCost()) {
                        HeuristicNode m = new HeuristicNode(
                                current,
                                child.getKey(),
                                child.getValue().getActionList(),
                                current.getG() + child.getValue().getCost(),
                                env);
                        openSet.remove(m);
                        openSet.add(m);
                        openTracker.put(m.getState(), m.getG());
                    }
                }
            }
        }
    }
}
```
