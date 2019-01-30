package main.java.analysis.mapGenerator;

import java.util.Arrays;
import java.util.Scanner;

public class NoWeightSimpleGenerator extends MapGenerator {

    public NoWeightSimpleGenerator(Scanner scanner) {
        super(scanner);
    }

    @Override
    protected int[][] generateTiles() {
        int[][] map = new int[height][width];
        for (int[] row : map) {
            Arrays.fill(row, 1);
        }
        return map;
    }

    @Override
    protected void setConfigValues() {
        super.setConfigValues();
    }

    @Override
    public String toString() {
        return "simple weightless and walless map generator";
    }
}
