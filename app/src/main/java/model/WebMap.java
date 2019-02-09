package model;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * int[][] type of a web which additionally includes knowledge to act as a web to be searched .
 * These values are tileAt, tileTarget, value of 0 is considered to be a wall. and name can be used as a file name.
 * Negative values are allowed.
 * Data model to be searched by a SearchAlgorithm.
 */
public class WebMap {
    private int[][] map;
    private Point tileAt;
    private Point tileTarget;
    private String name;
    private static final int WALL = 0;
    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

    public WebMap() {
        name = "nameless map";
    }

    /**
     *
     * @param location coordinate of where neighbours are picked from
     * @return list of neigbours from left, right, up and down.
     * Considers of being at the edge of map or being next to a wall which is a cell with value of 0.
     */
    public Collection<Point> getNeighbours(Point location) {
        var neighbours = new ArrayDeque<Point>();

        var under = new Point(location.x, location.y + 1);
        if (isAvailableLocation(under)) neighbours.add(under);

        var over = new Point(location.x, location.y - 1);
        if (isAvailableLocation(over)) neighbours.add(over);

        var right = new Point(location.x + 1, location.y);
        if (isAvailableLocation(right)) neighbours.add(right);

        var left = new Point(location.x - 1, location.y);
        if (isAvailableLocation(left)) neighbours.add(left);

        return neighbours;
    }

    /**
     * @return if such a map can exist, requirements:
     * width and height of map above 0
     * starting location tileAt exists within map
     * target location tileTarget exists within map
     * target is not a wall
     * name is not null and can be used as a file name
     * todo add name to test, improve validaiton by checkoing points are inside map
     */
    public boolean isValid() {
        boolean valid = !(map == null || tileAt == null || tileTarget == null || name == null);
        if (!valid) return false;
        valid = map.length > 0 && map[0].length > 0;
        if (!valid) return false;
        valid = tileAt.x >= 0 && tileAt.x < map[0].length && tileAt.y >= 0 && tileAt.y < map.length;
        if (!valid) return false;
        valid = tileTarget.x >= 0 && tileTarget.x < map[0].length && tileTarget.y >= 0 && tileTarget.y < map.length;
        if (!valid) return false;
        for (int i = 0; i < ILLEGAL_CHARACTERS.length ; i++) {
            for (char charAt: name.toCharArray()) {
                if (charAt == ILLEGAL_CHARACTERS[i]) return false;
            }
        }
        return map[tileTarget.y][tileTarget.x] != WALL;
    }
    private boolean isAvailableLocation(Point neighbourCanditate) {
        if (neighbourCanditate.x < 0 || neighbourCanditate.y < 0) return false;
        if (neighbourCanditate.x >= map[0].length || neighbourCanditate.y >= map.length) return false;
        if (map[neighbourCanditate.y][neighbourCanditate.x] == WALL) return false;
        return true;
    }

    /**
     * @return textual representation of map if it is valid.
     */
    public String getTextualView() {
        if (!isValid()) return "invalid map. Set map, tileAt, tileTarget";
        String mapInString = "width: " + map[0].length + " height: " + map.length + "\n";
        mapInString += "At location: " + tileAt.x + "," + tileAt.y + "\n";
        mapInString += "Target location: " + tileTarget.x + "," + tileTarget.y;

        return mapInString;
    }

    // setters n getters

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
