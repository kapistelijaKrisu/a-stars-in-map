package map_generator;

import model.web.WebMap;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Simple weightless map generator that asks user for parameters for generating a map.
 */
public class NoWeightSimpleGenerator implements MapGenerator {
    protected final Scanner scanner;
    private int mapWidth, mapHeight;
    private int startX, startY, targetX, targetY;
    private String mapName = "nameless";
    private static final String USER_WANTS_TO_EXIT = "q";

    /**
     *
     * @param scanner used to interact with user for configuring values.
     */
    public NoWeightSimpleGenerator(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * Asks user input values to create a map. And will loop until valid values are given.
     * Chance for exit is after evaluation.
     * Values required by user:
     * width over 0
     * height over 0
     * tile start x coordinate within 0 and width - 1
     * tile start y coordinate within 0 and height - 1
     * tile target x coordinate within 0 and width - 1
     * tile target y coordinate within 0 and height - 1
     * @return a valid map
     *
     */
    @Override
    public WebMap createMap() {
        if (setConfigValues()) {
            WebMap map = new WebMap();
            map.setName(mapName);
            map.setMap(generateTiles());
            map.setTileTarget(targetX, targetY);
            map.setTileStart(startX, startY);
            return map;
        } else return null;
    }

    private int[][] generateTiles() {
        int[][] map = new int[mapHeight][mapWidth];
        for (int[] row : map) {
            Arrays.fill(row, 1);
        }
        return map;
    }

    private boolean setConfigValues() {
        while (true) {
            try {
                System.out.println("set width: ");
                mapWidth =Integer.parseInt(scanner.nextLine());
                System.out.println("set height: ");
                mapHeight = Integer.parseInt(scanner.nextLine());
                System.out.println("set starting location x");
                startX = Integer.parseInt(scanner.nextLine());
                System.out.println("set starting location y");
                startY = Integer.parseInt(scanner.nextLine());
                System.out.println("set target location x");
                targetX = Integer.parseInt(scanner.nextLine());
                System.out.println("set target location y");
                targetY = Integer.parseInt(scanner.nextLine());

                if (startY < 0 || startX < 0 || targetX < 0 || targetY < 0) throw new ArrayIndexOutOfBoundsException();
                if (startY >= mapHeight || startX >= mapWidth || targetX >= mapWidth || targetY >= mapHeight) throw new ArrayIndexOutOfBoundsException();
                if (mapHeight <= 0 || mapWidth <= 0) throw new IllegalArgumentException();
                return true;
            } catch (Exception e) {
                System.out.println("width and height should be between 1-" + Integer.MAX_VALUE + ". x,y values should be between 0-" + (Integer.MAX_VALUE - 1));
                System.out.println("Press q to quit. Anything else to re-try map creation.");
                if (scanner.nextLine().equals(USER_WANTS_TO_EXIT)) return false;
            }
        }
    }

    @Override
    public String toString() {
        return "simple weightless and walless map generator";
    }

}
