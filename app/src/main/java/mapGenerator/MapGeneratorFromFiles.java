package mapGenerator;

import IOoperations.mapReader.MapLocator;
import model.WebMap;
import model.WeightedPoint;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *  Generates an in app memory of a WebMap that is read from map directory.
 *  User is asked which map from the directory is loaded
 */
public class MapGeneratorFromFiles implements MapGenerator {
    private Scanner scanner;
    private static final int BASIC = 0, DECODING = 1, READING_MAP = 2;
    private int state = BASIC;
    private Map<Character, Integer> decodeValues = new HashMap<>();
    private int[][] mapArea;
    private int mapHeight;
    private int mapWidth;
    private int mapLine;
    private String mapName;
    private int mapStartLocationX, mapStartLocationY, mapTargetLocationX, mapTargetLocationY;
    private MapLocator mapLocator;

    public MapGeneratorFromFiles(Scanner scanner) {
        this.scanner = scanner;
        mapLocator = new MapLocator();
    }

    /**
     * if maps folder exists and has files then asks user which map to load
     * @return read map from file, if file has mistakes returns null. refer to app_definition.md for a valid map file.
     */
    @Override
    public WebMap createMap() {
        try {
            File mapFile = chooseMap(getMaps());
            loadMapFromFile(mapFile);
            WebMap map = new WebMap();
            map.setName(mapName);
            map.setTileStart(mapStartLocationX , mapStartLocationY);
            map.setTileTarget(mapTargetLocationX, mapTargetLocationY);
            map.setMap(mapArea);
            return map;
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void loadMapFromFile(File mapFile) throws IOException, NullPointerException {
        clean();
        var reader = new BufferedReader(new FileReader(mapFile));
        mapName = mapFile.getName().substring(0, mapFile.getName().length() - 4);
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            process(line);
        }
        reader.close();
    }

    private List<File> getMaps() throws IOException {
        List<File> foundMaps = mapLocator.findMaps();
        if (foundMaps.size() == 0) {
            throw new IOException("No maps found in folder");
        } else {
            return foundMaps;
        }
    }

    private File chooseMap(List<File> foundMaps) {
        while (true) {
            try {
                System.out.println("Choose map by typing its number");
                System.out.println("Found maps:");
                for (int i = 0; i < foundMaps.size(); i++) {
                    System.out.println(i + ": " + foundMaps.get(i).getName());
                }
                return foundMaps.get(Integer.parseInt(scanner.nextLine()));

            } catch (IndexOutOfBoundsException | NullPointerException e) {
                System.out.println("Illegal input!");
            }
        }
    }

    private void process(String input) throws IOException, NullPointerException {
        String[] tokens = input.split(" ");
        if (tokens[0].equals("#") || tokens[0].equals("")) return;
        switch (state) {
            case BASIC:
                processInBasicState(tokens);
                break;
            case DECODING:
                processInDecodingState(tokens);
                break;
            case READING_MAP:
                processInMapReadingState(tokens[0]);
                break;
        }
    }

    private void processInBasicState(String[] tokens) throws IOException {
        switch (tokens[0]) {
            case "decode-begin":
                state = DECODING;
                break;
            case "decode-end":
                state = BASIC;
                break;
            case "start":
                mapStartLocationX = Integer.parseInt(tokens[1]);
                mapStartLocationY = Integer.parseInt(tokens[2]);
                break;
            case "target":
                mapTargetLocationX = Integer.parseInt(tokens[1]);
                mapTargetLocationY = Integer.parseInt(tokens[2]);
                break;
            case "width":
                mapWidth = Integer.parseInt(tokens[1]);
                break;
            case "height":
                mapHeight = Integer.parseInt(tokens[1]);
                break;
            case "map":
                mapArea = new int[mapHeight][mapWidth];
                state = READING_MAP;
                break;
            default:
                throw new IOException("invalid format");
        }
    }

    private void processInDecodingState(String[] tokens) {
        if (tokens[0].equals("decode-end")) {
            state = BASIC;
        } else {
            decodeValues.put(tokens[0].charAt(0), Integer.parseInt(tokens[1]));
        }
    }

    private void processInMapReadingState(String tokens) {
        for (int i = 0; i < tokens.length(); i++) {
            char token = tokens.charAt(i);
            if (token != ' ') {
                mapArea[mapLine][i] = decodeValues.get(token);
            }
        }
        mapLine++;
    }

    private void clean() {
        state = BASIC;
        decodeValues = new HashMap<>();
        mapStartLocationX = -1;
        mapTargetLocationX = -1;
        mapStartLocationY = -1;
        mapTargetLocationY = -1;
        mapArea = null;
        mapHeight = 0;
        mapWidth = 0;
        mapName = null;
        mapLine = 0;
    }


    @Override
    public String toString() {
        return "map loader from maps directory";
    }

    //for testing
    public void setMapLocator(MapLocator mapLocator) {
        this.mapLocator = mapLocator;
    }
}
