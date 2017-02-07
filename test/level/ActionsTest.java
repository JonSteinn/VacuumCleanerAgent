package level;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jonni on 2/6/2017.
 */
public class ActionsTest {

    private Actions actions;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        this.list = new ArrayList<>();
        this.list.add(Actions.GO);
        this.list.add(Actions.TURN_OFF);
        this.actions = new Actions(this.list, 55);
    }

    @Test
    public void testGetters() {
        assertEquals("Cost", 55, this.actions.getCost());
        assertEquals("List", this.list, this.actions.getActionList());
    }

    @Test
    public void testAdding() {
        ArrayList<String> tmp = new ArrayList<>(this.list);
        tmp.add(Actions.TURN_RIGHT);
        this.actions.addAction(Actions.TURN_RIGHT);
        assertEquals("Cost", 56, this.actions.getCost());
        assertEquals("List", tmp, this.actions.getActionList());
    }
}