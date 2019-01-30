package main.java.analysis.mapGenerator;

import main.java.analysis.model.WebMap;

import java.awt.*;
import java.util.Scanner;

public abstract class MapGenerator {

    protected int width, height;
    protected int startX, startY;
    protected int targetX, targetY;

    protected final Scanner scanner;

    public MapGenerator(Scanner scanner) {
        this.scanner = scanner;
    }

    protected abstract int[][] generateTiles();

    protected WebMap generateMap() {
        var webMap = new WebMap();
        webMap.setMap(generateTiles());
        webMap.setTileAt(new Point(startX, startY));
        webMap.setTileTarget(new Point(targetX, targetY));
        return webMap;
    }

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
                return;
            } catch (Exception e) {
                System.out.println("values should be between 1-" + Integer.MAX_VALUE);
            }
        }
    }

    //todo save file on disc, print info of map
    public WebMap createMap() {
        setConfigValues();
        WebMap map = generateMap();
        return  map;
    }
}
