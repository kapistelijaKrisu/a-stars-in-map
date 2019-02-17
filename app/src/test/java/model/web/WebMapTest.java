package model.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class WebMapTest {

    private int[][] data;
    private Collection<WeightedPoint> expectedNeighbours;

    private WebMap map;

    @BeforeEach
    public void setUp() {
        expectedNeighbours = new ArrayList<>();
        data = new int[3][3];
        for (int i = 0; i < 9; i++) {
            data[i / 3][i % 3] = i;
        }
        map = new WebMap();
        map.setMap(data);
    }

    // set/get start/target

    @Test
    public void nameTest() {
        map.setTileTarget(2, 2);
        map.setTileStart(1, 2);
        map.setMap(data);
        assertTrue(map.isValid());
        String test = "name";
        assertTrue(map.isValid());
        char[] illegal_chars = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
        for (int i = 0; i < illegal_chars.length; i++) {
            map.setName(test + illegal_chars[i]);
            assertFalse(map.isValid());
        }

    }

    @Test
    public void printTest() {
        map.setMap(null);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());
        map.setName(null);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());
        map.setTileTarget(2, 2);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());
        map.setTileStart(1, 2);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());
        map.setMap(data);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());

        map.setName("now all values are valid");
        assertEquals("width: 3 height: 3\nStart location: 1,2\nTarget location: 2,2", map.getTextualView());

        map.setTileTarget(1, -1);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());
        map.setTileTarget(-1, 1);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());
        map.setTileTarget(-1, 1);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());
        map.setTileTarget(1, -1);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());

        map.setTileTarget(0, 0);
        assertEquals("invalid map. Set map, tileStart, tileTarget", map.getTextualView());

    }

    @Test //todo next last character
    public void isValidTest() {
        map.setMap(null);

        assertFalse(map.isValid());
        map.setTileTarget(2, 1);
        assertFalse(map.isValid());
        map.setTileStart(1, 2);
        assertFalse(map.isValid());
        map.setMap(data);
        map.setTileStart(-1,-1);
        assertFalse(map.isValid());
        map.setTileStart(2, 2);
        assertTrue(map.isValid());

    }

    @Test
    public void neighboursAll4Test() {
        map.setTileStart(1, 1);

        var neighbours = map.getNeighbours(map.getTileStart());

        expectedNeighbours.add(new WeightedPoint(0,1, 3));
        expectedNeighbours.add(new WeightedPoint(2,1, 5));
        expectedNeighbours.add(new WeightedPoint(1,0, 1));
        expectedNeighbours.add(new WeightedPoint(1,2, 7));

        assertEquals(expectedNeighbours.size(), neighbours.size(), "should have correct amount of neighbours");
        assertTrue(expectedNeighbours.containsAll(neighbours));
    }

    @Test // 0,0 is a wall
    public void neighboursLeftTest() {
        map.setTileStart(0,1);

        var neighbours = map.getNeighbours(map.getTileStart());

        expectedNeighbours.add(new WeightedPoint(1,1, 4));
        expectedNeighbours.add(new WeightedPoint(0,2, 6));

        assertEquals(expectedNeighbours.size(), neighbours.size(), "should have correct amount of neighbours");
        assertTrue(expectedNeighbours.containsAll(neighbours));
    }

    @Test
    public void neighboursRightTest() {
        map.setTileStart(2, 1);

        var neighbours = map.getNeighbours(map.getTileStart());

        expectedNeighbours.add(new WeightedPoint(1,1, 4));
        expectedNeighbours.add(new WeightedPoint(2,0, 2));
        expectedNeighbours.add(new WeightedPoint(2,2, 8));

        assertEquals(expectedNeighbours.size(), neighbours.size(), "should have correct amount of neighbours");
        assertTrue(expectedNeighbours.containsAll(neighbours));
    }

    @Test // 0,0 is a wall
    public void neighboursTopTest() {
        map.setTileStart(1, 0);

        var neighbours = map.getNeighbours(map.getTileStart());

        expectedNeighbours.add(new WeightedPoint(2,0, 2));
        expectedNeighbours.add(new WeightedPoint(1,1, 4));

        assertEquals(expectedNeighbours.size(), neighbours.size(), "should have correct amount of neighbours");
        assertTrue(expectedNeighbours.containsAll(neighbours));
    }

    @Test
    public void neighboursUnderTest() {
        map.setTileStart(1, 2);

        var neighbours = map.getNeighbours(map.getTileStart());

        expectedNeighbours.add(new WeightedPoint(0,2, 6));
        expectedNeighbours.add(new WeightedPoint(2,2, 8));
        expectedNeighbours.add(new WeightedPoint(1,1, 4));

        assertEquals(expectedNeighbours.size(), neighbours.size(), "should have correct amount of neighbours");
        assertTrue(expectedNeighbours.containsAll(neighbours));
    }

}