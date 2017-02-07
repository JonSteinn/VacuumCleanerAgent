package level.environment;

import level.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jonni on 2/6/2017.
 */
public class HeuristicCacheTest {

    private HeuristicCache hCache;

    @Before
    public void setUp() {
        Set<Position> dirt = new HashSet<>();
        dirt.add(new Position(3,3));
        dirt.add(new Position(6,1));
        dirt.add(new Position(4,1));
        Environment e = new Environment(new Position(1,1), 20, 6, dirt, new HashSet<>());
        EnvironmentPreProcess epp = new EnvironmentPreProcess(e);
        this.hCache = new HeuristicCache(epp);
    }

    @Test
    public void heuristicCacheTest() {
        Set<Position> dirtAndHome = new HashSet<>();
        dirtAndHome.add(new Position(3,3));
        dirtAndHome.add(new Position(1,1));
        dirtAndHome.add(new Position(6,1));
        // MSP: (3,3) -> (1,1)->(4,1)->(6,1)
        // (3,3) -> (1,1) = 2 + 2 + 1 (2 horizontally, 2 vertically, 1 turn)
        // (1,1) -> (4,1) = 3
        // (4,1) -> (6,1) = 2
        int expected = 3 + 2 + (2 + 2 + 1);
        assertEquals(expected, this.hCache.getCachedHeuristic(dirtAndHome));

    }

}