package model.web;

import java.awt.*;
import java.util.Objects;

/**
 * Extension of java.awt.Point that has an additional value weight of double type
 */
public class WeightedPoint extends Point implements Comparable<WeightedPoint> {
    public double weight;
    private static final double DIVIDER_TO_KEEP_DISTANCE_LESS_THAN_ACTUALLY_IS = 1.42;

    /**
     * @param x      x of Point
     * @param y      y of Point
     * @param weight weight of WeightedPoint
     */
    public WeightedPoint(int x, int y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    /**
     * Creates a Weighted point with a weight of 1
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public WeightedPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.weight = 1;
    }

    /**
     * Calculates a rough estimation of distance by |this.x - other.x| + |this.y - other.y| / 1.42
     * side can be at most 1.414x bigger at sides being 45 degrees so we use 1.42 to keep estimated distance less or equal to actual distance.
     *
     * @param distanceTo where to distance is calculated
     * @return a rough estimate of distance to distanceTo point
     */
    public double calculateRoughDistance(WeightedPoint distanceTo) {
        return (Math.abs(x - distanceTo.x) + Math.abs(y - distanceTo.y)) / DIVIDER_TO_KEEP_DISTANCE_LESS_THAN_ACTUALLY_IS;
        //return this.distance(distanceTo);// too heavy

        //double px = distanceTo.getX() - this.getX();
        //double py = distanceTo.getY() - this.getY();
        //return Math.sqrt(px * px + py * py);
    }

    /**
     * compares weights
     *
     * @param o to compare with
     * @return result of weight - o.weight
     */
    @Override
    public int compareTo(WeightedPoint o) {
        return (int) (weight - o.weight);
    }

    /**
     * Checks if coordinates are same then considers to be equal object
     *
     * @param o to compare with
     * @return true if their x and y coordinate is same. Ignores weight
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightedPoint that = (WeightedPoint) o;
        return y == that.y &&
                x == that.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " w: " + weight;
    }
}
