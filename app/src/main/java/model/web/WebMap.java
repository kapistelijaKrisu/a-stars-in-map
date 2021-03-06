package model.web;

import system_tools.LegalFileName;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * int[][] type of a web which additionally includes knowledge to act as a web to be searched .
 * These values are tileStart, tileTarget, value of 0 is considered to be a wall. and name can be used as a file name.
 * Negative values are allowed.
 * Data model to be searched by a AnalysableAlgorithm.
 */
public class WebMap {
    /**
     * Max values for width and height
     */
    public static final int MAX_HEIGHT = 10000, MAX_WIDTH = 10000;
    private int[][] map;
    private int startX, startY, targetX, targetY;
    private String name;
    private static final int WALL = 0;


    public WebMap() {
        name = "nameless map";
    }

    /**
     * @param location coordinate of where neighbours are picked from
     * @return list of neigbours from left, right, up and down.
     * Considers of being at the edge of map or being next to a wall which is a cell with value of 0.
     */
    public Collection<WeightedPoint> getNeighbours(WeightedPoint location) {
        var neighbours = new ArrayDeque<WeightedPoint>();

        if (isAvailableLocation(location.x, location.y + 1)) {
            neighbours.add(new WeightedPoint(location.x, location.y + 1, map[location.y + 1][location.x]));
        }
        if (isAvailableLocation(location.x, location.y - 1)) {
            neighbours.add(new WeightedPoint(location.x, location.y - 1, map[location.y - 1][location.x]));
        }
        if (isAvailableLocation(location.x + 1, location.y)) {
            neighbours.add(new WeightedPoint(location.x + 1, location.y, map[location.y][location.x + 1]));
        }
        if (isAvailableLocation(location.x - 1, location.y)) {
            neighbours.add(new WeightedPoint(location.x - 1, location.y, map[location.y][location.x - 1]));
        }

        return neighbours;
    }

    /**
     * @param x x-coordinate
     * @param y y-coordinate
     * @return WeightedPoint object representing asked coordinate
     * @throws IndexOutOfBoundsException if out of map range
     */
    public int getLocationWeight(int x, int y) throws IndexOutOfBoundsException {
        return map[y][x];
    }

    /**
     * @return true if such a map is considered valid else prints out all errors and returns false, requirements:
     * width and height of map above 0
     * starting location tileStart exists within map
     * target location tileTarget exists within map
     * target or start point is not a wall
     * LegalFileName.isValidFileName(name) returns true
     * width is less or equal to 10000, height is less or equal to 10000
     */
    public boolean isValid() {
        if (map == null) {
            System.out.println("map cannot be null");
            return false;
        }
        try {
            if (!isAvailableLocation(startX, startY)) {
                System.out.println("Start Coordinates are invalid");
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Start Coordinates are invalid");
            return false;
        }
        try {
            if (!isAvailableLocation(targetX, targetY) || name == null) {
                System.out.println("Target Coordinates are invalid");
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Target Coordinates are invalid");
            return false;
        }
        try {
            if (map.length <= 0 && map[0].length <= 0) {
                System.out.println("map height and width cannot be 0 or lower");
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("map height and width cannot be 0 or lower");
            return false;
        }
        if (map.length > MAX_HEIGHT && map[0].length > MAX_WIDTH) {
            System.out.println("map height cannot exceed " + MAX_HEIGHT + " and width cannot exceed " + MAX_WIDTH);
            return false;
        }
        if (!LegalFileName.isValidFileName(name)) {
            System.out.println("Illegal name!");
            return false;
        }
        return true;
    }

    private boolean isAvailableLocation(int x, int y) {
        if (x < 0 || y < 0) return false;
        else if (x >= map[0].length || y >= map.length) return false;
        else return (map[y][x] != WALL);
    }

    /**
     * @return textual representation of map if it is valid.
     */
    public String getTextualView() {
        if (!isValid()) return "Invalid map. Set map, tileStart, tileTarget correctly.";
        String mapInString = "Width: " + map[0].length + " Height: " + map.length + System.lineSeparator();
        mapInString += "Start location: " + startX + "," + startY + System.lineSeparator();
        mapInString += "Target location: " + targetX + "," + targetY;

        return mapInString;
    }

    /**
     * @return current starting location
     * @throws IndexOutOfBoundsException if coordinates are out of map
     */
    public WeightedPoint getTileStart() throws IndexOutOfBoundsException {
        return new WeightedPoint(startX, startY, map[startY][startX]);
    }

    /**
     * @return current target location
     * @throws IndexOutOfBoundsException if coordinates are out of map
     */
    public WeightedPoint getTileTarget() throws IndexOutOfBoundsException {
        return new WeightedPoint(targetX, targetY, map[targetY][targetX]);
    }

    /**
     * Sets starting coordinates.
     * get will throw exception if given coordinates don't exist
     *
     * @param x start x-coordinate
     * @param y start y-coordinate
     */
    public void setTileStart(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    /**
     * Sets target coordinates.
     * get will throw exception if given coordinates don't exist
     *
     * @param x target x-coordinate
     * @param y target y-coordinate
     */
    public void setTileTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    // setters n getters

    public int width() throws IndexOutOfBoundsException {
        return map[0].length;
    }

    public int height() throws NullPointerException {
        return map.length;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    /**
     * Should be used for testing only
     *
     * @return internal map
     */
    public int[][] getMap() {
        return map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
