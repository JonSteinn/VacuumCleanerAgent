package game;


import level.Orientation;
import level.Position;
import level.State;
import level.environment.Environment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jonni on 1/28/2017.
 *
 * Parses precepts into instances of Environment and State which are
 * available through static functions. Does not handle any errors it
 * must be passed correctly formatted percepts. The class is final
 * to prevent instances of it being made.
 */
public final class PerceptsParser {

    private static Environment environment;
    private static State initialState;

    /**
     * Private no argument constructor to avoid instances.
     */
    private PerceptsParser() { }

    /**
     * Parses a collection of correctly formatted percepts into
     * instances of Environment and initial state.
     *
     * @param percepts Collection of String
     */
    public static void parse(Collection<String> percepts) {

        // Arguments needed for Environment and initial state
        Position home = null;
        int width = 0;
        int height = 0;
        Set<Position> dirt = new HashSet<>();
        Set<Position> obstacles = new HashSet<>();
        Orientation orientation = null;
        Position agentPosition = null;

        Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
        for (String percept : percepts) {

            Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
            if (perceptNameMatcher.matches()) {
                String perceptName = perceptNameMatcher.group(1);

                if (perceptName.equals("HOME")) {
                    Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        home = new Position(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                        agentPosition = new Position(home);
                    }
                }

                else if (perceptName.equals("AT")) {
                    Matcher mOb = Pattern.compile("\\(\\s*AT\\s+OBSTACLE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    Matcher mDi = Pattern.compile("\\(\\s*AT\\s+DIRT\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (mOb.matches()) {
                        obstacles.add(new Position(Integer.parseInt(mOb.group(1)), Integer.parseInt(mOb.group(2))));
                    } else if (mDi.matches()) {
                        dirt.add(new Position(Integer.parseInt(mDi.group(1)), Integer.parseInt(mDi.group(2))));
                    }
                }

                else if (perceptName.equals("ORIENTATION")) {
                    Matcher m = Pattern.compile("\\(\\s*ORIENTATION\\s+([A-Z]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        orientation = new Orientation(m.group(1));
                    }
                }

                else if (perceptName.equals("SIZE")) {
                    Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        width = Integer.parseInt(m.group(1));
                        height = Integer.parseInt(m.group(2));
                    }
                }
            }
        }

        PerceptsParser.environment = new Environment(home, width, height, dirt, obstacles);
        PerceptsParser.initialState = new State(orientation, agentPosition);
    }

    /**
     * Returns a reference to the parsed environment.
     *
     * @return Environment
     */
    public static Environment getEnvironment() {
        return PerceptsParser.environment;
    }

    /**
     * Returns a reference to the parsed State.
     *
     * @return State
     */
    public static State getInitialState() {
        return PerceptsParser.initialState;
    }
}
