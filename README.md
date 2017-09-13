# Path finding vacuum cleaner agent 
Made for a course in AI at Reykjavík University in 2017. 

## [![Build Status](https://travis-ci.org/JonSteinn/VacuumCleanerAgent.svg?branch=master)](https://travis-ci.org/JonSteinn/VacuumCleanerAgent)

## Problem
The problem description can be read in full in [description.pdf](https://github.com/JonSteinn/VacuumCleanerAgent/blob/master/description.pdf). I do not own this assignment.

The environment is a grid of cells, where each can contain nothing, dirt or an obstacle and we are to implement an agent that, given full knowledge of the environment, can clean all dirt and return to its original position. Cells with dirt can be blocked from the agent due to obstacles. The actions available to the agent and their cost can be seen in the following table.

| Action | Cost |
| -- | -- |
| Turn on | 1 |
| Turn left | 1 |
| Turn right | 1 |
| Go | 1 |
| Suck | 1 for cells containing dirt, 5 otherwise |
| Turn off | 1 + D*50 if at home, 100 + D*50 othwerise, where D are remainig dirt |

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
TODO
