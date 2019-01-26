package model;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Collection;

public class WebMap {
    private int[][] map;
    private Point tileAt;
    private Point tileTarget;

    public WebMap() {

    }

    public Collection<Point> getNeighbours(Point location) {
        var neighbours = new ArrayDeque<Point>();

        var under = new Point(location.x, location.y + 1);
        if (isAvailableLocation(under)) neighbours.add(under);

        var over = new Point(location.x, location.y - 1);
        if (isAvailableLocation(over)) neighbours.add(under);

        var right = new Point(location.x + 1, location.y);
        if (isAvailableLocation(right)) neighbours.add(under);

        var left = new Point(location.x - 1, location.y);
        if (isAvailableLocation(left)) neighbours.add(under);

        return neighbours;
    }

    private boolean isAvailableLocation(Point neighbourCanditate) {
        if (neighbourCanditate.x < 0 || neighbourCanditate.y < 0) return false;
        if (neighbourCanditate.x >= map[0].length || neighbourCanditate.y >= map.length) return false;
        if (map[neighbourCanditate.y][neighbourCanditate.x] == 0) return false;
        return true;
    }

    // todo use string builder
    @Override
    public String toString() {
        String mapInString = "";
        for (int[] yAxis : map) {
            for (int xAxis : yAxis) {
                mapInString += xAxis + " ";
            }
            mapInString += "\n";
            mapInString += "Starting location: " + tileAt.x + "," + tileAt.y + "\n";
            mapInString += "Target location: " + tileTarget.x + "," + tileTarget.y + "\n";
        }
        return mapInString;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Point getTileAt() {
        return tileAt;
    }

    public void setTileAt(Point tileAt) {
        this.tileAt = tileAt;
    }

    public Point getTileTarget() {
        return tileTarget;
    }

    public void setTileTarget(Point tileTarget) {
        this.tileTarget = tileTarget;
    }
}
