package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WebMapTest {

    private int[][] data;
    private Point atLocation;
    private Point targetLocation;
    private Collection<Point> expectedNeighbours;

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

    @Test
    public void settersAndGettersTest() {
        atLocation = new Point(1,2);
        targetLocation = new Point(2,3);

        map.setTileTarget(targetLocation);
        map.setTileAt(atLocation);

        assertEquals(data, map.getMap());
        assertEquals(targetLocation, map.getTileTarget());
        assertEquals(atLocation, map.getTileAt());
    }

    @Test //todo remove last character
    public void printTest() {
        atLocation = new Point(1,2);
        targetLocation = new Point(2,3);
        map.setMap(null);

        assertEquals("invalid map. Set map, tileAt, tileTarget", map.toString());
        map.setTileTarget(targetLocation);
        assertEquals("invalid map. Set map, tileAt, tileTarget", map.toString());
        map.setTileAt(atLocation);
        assertEquals("invalid map. Set map, tileAt, tileTarget", map.toString());
        map.setMap(data);
        map.setTileAt(null);
        assertEquals("invalid map. Set map, tileAt, tileTarget", map.toString());
        map.setTileAt(atLocation);
        assertEquals("0 1 2 \n" +
                "At location: 1,2\n" +
                "Target location: 2,3\n" +
                "3 4 5 \n" +
                "At location: 1,2\n" +
                "Target location: 2,3\n" +
                "6 7 8 \n" +
                "At location: 1,2\n" +
                "Target location: 2,3\n", map.toString());

    }

    @Test //todo remove last character
    public void isValidTest() {
        atLocation = new Point(1,2);
        targetLocation = new Point(2,3);
        map.setMap(null);

        assertFalse(map.isValid());
        map.setTileTarget(targetLocation);
        assertFalse(map.isValid());
        map.setTileAt(atLocation);
        assertFalse(map.isValid());
        map.setMap(data);
        map.setTileAt(null);
        assertFalse(map.isValid());
        map.setTileAt(atLocation);
        assertTrue(map.isValid());

    }

    @Test
    public void neighboursAll4Test() {
        atLocation = new Point(1,1);
        map.setTileAt(atLocation);

        var neighbours = map.getNeighbours(map.getTileAt());

        expectedNeighbours.add(new Point(0,1));
        expectedNeighbours.add(new Point(2,1));
        expectedNeighbours.add(new Point(1,0));
        expectedNeighbours.add(new Point(1,2));

        neighbourTest(neighbours);
    }

    @Test // 0,0 is a wall
    public void neighboursLeftTest() {
        atLocation = new Point(0,1);
        map.setTileAt(atLocation);

        var neighbours = map.getNeighbours(map.getTileAt());

        expectedNeighbours.add(new Point(1,1));
        expectedNeighbours.add(new Point(0,2));

        neighbourTest(neighbours);
    }

    @Test
    public void neighboursRightTest() {
        atLocation = new Point(2,1);
        map.setTileAt(atLocation);

        var neighbours = map.getNeighbours(map.getTileAt());

        expectedNeighbours.add(new Point(1,1));
        expectedNeighbours.add(new Point(2,0));
        expectedNeighbours.add(new Point(2,2));

        neighbourTest(neighbours);
    }

    @Test // 0,0 is a wall
    public void neighboursTopTest() {
        atLocation = new Point(1,0);
        map.setTileAt(atLocation);

        var neighbours = map.getNeighbours(map.getTileAt());

        expectedNeighbours.add(new Point(2,0));
        expectedNeighbours.add(new Point(1,1));

        neighbourTest(neighbours);
    }

    @Test
    public void neighboursUnderTest() {
        atLocation = new Point(1,2);
        map.setTileAt(atLocation);

        var neighbours = map.getNeighbours(map.getTileAt());

        expectedNeighbours.add(new Point(0,2));
        expectedNeighbours.add(new Point(2,2));
        expectedNeighbours.add(new Point(1,1));

        neighbourTest(neighbours);
    }

    private void neighbourTest(Collection<Point> actual) {
        assertEquals(expectedNeighbours.size(), actual.size(), "should have correct amount of neighbours");

        var found = new HashMap<Point, Boolean>();
        for (Point p: expectedNeighbours) {
            found.put(p, false);
        }

        for (Point neighbout: actual) {
            found.put(neighbout, true);
        }

        for (Map.Entry<Point, Boolean> pair : found.entrySet()) {
            assertEquals(true, pair.getValue(), "point " + pair.getKey().toString() + " should be true");
        }
    }
}