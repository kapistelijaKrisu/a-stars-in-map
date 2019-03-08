package map_generator;

import file_operations.map_reader.MapLoader;
import file_operations.root_file_operations.RootFileLister;
import file_operations.root_file_operations.RootFolderFinder;
import model.web.WebMap;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Generates an in app memory of a WebMap that is read from map directory.
 * User is asked which map from the directory is loaded. Uses RootFileLister to find map files under /maps directory.
 */
public class MapGeneratorFromFiles implements MapGenerator {
    private Scanner scanner;
    private static final String USER_WANTS_TO_EXIT = "q";
    private MapLoader mapLoader;
    private RootFileLister mapLocator;

    public MapGeneratorFromFiles(Scanner scanner) {
        this.scanner = scanner;
        mapLocator = new RootFileLister();
        mapLoader = new MapLoader();
    }

    /**
     * if map folder exists and has files then asks user which map to load
     *
     * @return WebMap from file, if file has mistakes or map is not valid returns null. If map folder at app level directory is empty returns null. refer to app_definition.md for a valid map file.
     */
    @Override
    public WebMap createMap() {
        while (true) {
            try {
                var foundMaps = listMaps();
                System.out.println("Choose map by typing its number");
                for (int i = 0; i < foundMaps.size(); i++) {
                    System.out.println(i + ": " + foundMaps.get(i).getName());
                }
                System.out.println("Press " + USER_WANTS_TO_EXIT + " to exit");
                var userChoice = scanner.nextLine();
                if (userChoice.equals(USER_WANTS_TO_EXIT)) {
                    return null;
                } else {
                    WebMap map = mapLoader.loadMapFromFile(foundMaps.get(Integer.parseInt(userChoice)));
                    if (map != null && map.isValid()) return map;
                    System.out.println("Map is not valid. Please check the map file");
                    return null;
                }
            } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                System.out.println("Illegal input!");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    private List<File> listMaps() throws IOException {
        List<File> foundMaps = mapLocator.listFiles("/maps", ".map");
        if (foundMaps.size() == 0) {
            throw new IOException("No maps found in" + RootFolderFinder.getRootFolder() + "maps/. Or folder does not exist!");
        } else {
            return foundMaps;
        }
    }

    /**
     *
     * @return description of this generator
     */
    @Override
    public String toString() {
        return "map loader from ../maps directory";
    }

    /**
     * testing setter
     * @param mapLocator testing setter
     */
    public void setMapLocator(RootFileLister mapLocator) {
        this.mapLocator = mapLocator;
    }
}
