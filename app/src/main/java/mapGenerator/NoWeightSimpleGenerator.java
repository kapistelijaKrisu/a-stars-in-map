package mapGenerator;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Simple wightless map generator that asks user for parameters for generating a map.
 */
public class NoWeightSimpleGenerator extends MapGenerator {
    protected final Scanner scanner;

    /**
     *
     * @param scanner used to interact with user for configuring values.
     * todo save file on disc, print info of map
     */
    public NoWeightSimpleGenerator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     *
     * @return map of configured height n width with every value being 1.
     */
    @Override
    protected int[][] generateTiles() {
        int[][] map = new int[height][width];
        for (int[] row : map) {
            Arrays.fill(row, 1);
        }
        return map;
    }

    /**
     * Config all values that are needed for generating a valid WebMap.
     * Asks user for parameters until valid values are given.
     * Upon error asks all values again.
     */
    @Override
    protected void setConfigValues() {
        while (true) {
            try {
                System.out.println("set width: ");
                width = Integer.parseInt(scanner.nextLine());
                System.out.println("set height: ");
                height = Integer.parseInt(scanner.nextLine());
                System.out.println("set starting location x");
                startX = Integer.parseInt(scanner.nextLine());
                System.out.println("set starting location y");
                startY = Integer.parseInt(scanner.nextLine());
                System.out.println("set target location x");
                targetX = Integer.parseInt(scanner.nextLine());
                System.out.println("set target location y");
                targetY = Integer.parseInt(scanner.nextLine());

                if (startY < 0 || startX < 0 || targetX < 0 || targetY < 0) throw new ArrayIndexOutOfBoundsException();
                if (startY >= height || startX >= width || targetX >= width || targetY >= height) throw new ArrayIndexOutOfBoundsException();
                if (width < 0 || height < 0) throw new IllegalArgumentException();
                return;
            } catch (Exception e) {
                System.out.println("width and height should be between 1-" + Integer.MAX_VALUE + ". x,y values should be between 0-" + (Integer.MAX_VALUE - 1));
            }
        }
    }

    @Override
    public String toString() {
        return "simple weightless and walless map generator";
    }
}
