package model.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeightedPointTest {

    @Test
    public void valueTest() {
        WeightedPoint weightedPoint = new WeightedPoint(1, 2, 3);
        assertEquals(1, weightedPoint.x);
        assertEquals(2, weightedPoint.y);
        assertEquals(3, weightedPoint.weight);

        weightedPoint = new WeightedPoint(-4, -5, -6);
        assertEquals(-4, weightedPoint.x);
        assertEquals(-5, weightedPoint.y);
        assertEquals(-6, weightedPoint.weight);
    }

    @Test
    public void equalsTest() {
        WeightedPoint weightedPoint = new WeightedPoint(11, 11, 11);
        WeightedPoint weightedPoint2 = new WeightedPoint(11, 11, 1);
        assertEquals(weightedPoint, weightedPoint2);

        weightedPoint = new WeightedPoint(11, 11, 11);
        weightedPoint2 = new WeightedPoint(11, 11, 11);
        assertEquals(weightedPoint, weightedPoint2);

        weightedPoint = new WeightedPoint(1, 11, 11);
        weightedPoint2 = new WeightedPoint(11, 11, 11);
        assertNotEquals(weightedPoint, weightedPoint2);
    }

    @Test
    public void compareTestTest() {
        WeightedPoint weightedPoint = new WeightedPoint(11, 11, 11);
        WeightedPoint weightedPoint2 = new WeightedPoint(11, 11, 1);
        assertTrue(weightedPoint.compareTo(weightedPoint2) > 0);

        weightedPoint2 = new WeightedPoint(11, 11, 21);
        assertTrue(weightedPoint.compareTo(weightedPoint2) < 0);

        weightedPoint2 = new WeightedPoint(11, 11, 11);
        assertTrue(weightedPoint.compareTo(weightedPoint2) == 0);
    }

    @Test
    public void distanceTestTest() {
        WeightedPoint weightedPoint = new WeightedPoint(11, 11, 11);
        WeightedPoint weightedPoint2 = new WeightedPoint(11, 11, 1);
        assertEquals(0.0, weightedPoint.calculateRoughDistance(weightedPoint2));


        weightedPoint2 = new WeightedPoint(31, -33, 1);
        assertEquals(64, weightedPoint.calculateRoughDistance(weightedPoint2));

        weightedPoint2 = new WeightedPoint(31, 33, 1);
        assertEquals(42, weightedPoint.calculateRoughDistance(weightedPoint2));
    }

    @Test
    public void toStringTest() {
        WeightedPoint weightedPoint = new WeightedPoint(11, 11, 11);
        assertEquals("x: 11 y: 11 w: 11.0", weightedPoint.toString());

        weightedPoint = new WeightedPoint(-11, -11, 12);
        assertEquals("x: -11 y: -11 w: 12.0", weightedPoint.toString());
    }
}
