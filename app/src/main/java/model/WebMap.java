package model;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * int[][] type of a web which additionally includes knowledge to act as a web to be searched .
 * These values are tileStart, tileTarget, value of 0 is considered to be a wall. and name can be used as a file name.
 * Negative values are allowed.
 * Data model to be searched by a SearchAlgorithm.
 */
public class WebMap {
    private int[][] map;
    private int startX, startY, targetX, targetY;
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
    public Collection<WeightedPoint> getNeighbours(WeightedPoint location) {
        var neighbours = new ArrayDeque<WeightedPoint>();

        if (isAvailableLocation(location.x, location.y + 1)){
            neighbours.add(new WeightedPoint(location.x, location.y + 1, map[location.y + 1][location.x]));
        }
        if (isAvailableLocation(location.x, location.y - 1)){
            neighbours.add(new WeightedPoint(location.x, location.y - 1, map[location.y - 1][location.x]));
        }
        if (isAvailableLocation(location.x + 1, location.y)) {
            neighbours.add(new WeightedPoint(location.x + 1, location.y, map[location.y][location.x + 1]));
        }
        if (isAvailableLocation(location.x - 1, location.y)){
            neighbours.add(new WeightedPoint(location.x - 1, location.y, map[location.y][location.x - 1]));
        }

        return neighbours;
    }

    /**
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return WeightedPoint object representing asked coordinate
     * @throws IndexOutOfBoundsException if out of map range
     */
    public WeightedPoint getCoordinate(int x, int y) throws IndexOutOfBoundsException {
        return new WeightedPoint(x, y, map[y][x]);
    }

    /**
     * @return if such a map can exist, requirements:
     * width and height of map above 0
     * starting location tileStart exists within map
     * target location tileTarget exists within map
     * target is not a wall
     * name is not null and can be used as a file name
     * todo add name to test, improve validaiton by checkoing points are inside map
     */
    public boolean isValid() {
        boolean valid = !(map == null || !isAvailableLocation(startX, startY) || !isAvailableLocation(targetX, targetY) || name == null);
        if (!valid) return false;
        valid = map.length > 0 && map[0].length > 0;
        if (!valid) return false;
        valid = startX >= 0 && startX < map[0].length && startY >= 0 && startY < map.length;
        if (!valid) return false;
        valid = targetX >= 0 && targetX < map[0].length && targetY >= 0 && targetY < map.length;
        if (!valid) return false;
        for (int i = 0; i < ILLEGAL_CHARACTERS.length ; i++) {
            for (char charAt: name.toCharArray()) {
                if (charAt == ILLEGAL_CHARACTERS[i]) return false;
            }
        }
        return true;
    }
    private boolean isAvailableLocation(int x, int y) {
        if (x < 0 || y < 0) return false;
        if (x >= map[0].length || y >= map.length) return false;
        if (map[y][x] == WALL) return false;
        return true;
    }

    /**
     * @return textual representation of map if it is valid.
     */
    public String getTextualView() {
        if (!isValid()) return "invalid map. Set map, tileStart, tileTarget";
        String mapInString = "width: " + map[0].length + " height: " + map.length + "\n";
        mapInString += "Start location: " + startX + "," + startY + "\n";
        mapInString += "Target location: " + targetX + "," + targetY;

        return mapInString;
    }


    public WeightedPoint getTileStart() throws IndexOutOfBoundsException {
        return new WeightedPoint(startX, startY, map[startY][startX]);
    }

    public WeightedPoint getTileTarget() throws IndexOutOfBoundsException {
        return new WeightedPoint(targetX, targetY, map[targetY][targetX]);
    }

    public void setTileStart(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    public void setTileTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    // setters n getters

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
