package file_operations.map_reader;

import model.web.WebMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates a WebMap instance by reading a file
 */
public class MapLoader {
    private enum PROCESS_STATE {BASIC, DECODING, READING_MAP}

    private PROCESS_STATE state = PROCESS_STATE.BASIC;
    private int[][] mapArea;
    private int mapHeight;
    private int mapWidth;
    private int mapLine;
    private String mapName;
    private int mapStartLocationX, mapStartLocationY, mapTargetLocationX, mapTargetLocationY;
    private Map<Character, Integer> decodeValues = new HashMap<>();

    /**
     * Creates a WebMap instance by reading a file. It allows invalid webMaps as long as it can read it from file.
     *
     * @param mapFile filepath to the file
     * @return loaded WebMap instance representing file contents or map. It allows invalid webMaps as long as it can read it from file.
     * @throws IOException          if fails to open or read values from file to create webMap instance
     * @throws NullPointerException if if to open or is missing values from file to create webMap instance
     */
    public WebMap loadMapFromFile(File mapFile) throws IOException, NullPointerException {
        clean();
        var reader = new BufferedReader(new FileReader(mapFile));
        mapName = mapFile.getName().substring(0, mapFile.getName().length() - 4);
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            process(line);
        }
        reader.close();
        return loadValues();
    }

    private WebMap loadValues() {
        WebMap map = new WebMap();
        map.setName(mapName);
        map.setTileStart(mapStartLocationX, mapStartLocationY);
        map.setTileTarget(mapTargetLocationX, mapTargetLocationY);
        map.setMap(mapArea);
        return map;
    }

    private void process(String input) throws IOException, NullPointerException, IllegalStateException {
        String[] tokens = input.split(" ");
        if (tokens[0].equals("#") || tokens[0].equals("")) return;
        if (state == null) throw new IllegalStateException("State cannot be null");
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
                state = PROCESS_STATE.DECODING;
                break;
            case "decode-end":
                state = PROCESS_STATE.BASIC;
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
                state = PROCESS_STATE.READING_MAP;
                break;
            default:
                throw new IOException("invalid format");
        }
    }

    private void processInDecodingState(String[] tokens) {
        if (tokens[0].equals("decode-end")) {
            state = PROCESS_STATE.BASIC;
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
        state = PROCESS_STATE.BASIC;
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
}
