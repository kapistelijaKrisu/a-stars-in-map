package model;

import java.awt.Point;
import java.util.Objects;

public class WeightedPoint extends Point implements Comparable<WeightedPoint> {
    public int weight;

    public WeightedPoint(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public WeightedPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.weight = 1;
    }

    @Override
    public int compareTo(WeightedPoint o) {
        return weight - o.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightedPoint that = (WeightedPoint) o;
        return  y == that.y &&
                x == that.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}
