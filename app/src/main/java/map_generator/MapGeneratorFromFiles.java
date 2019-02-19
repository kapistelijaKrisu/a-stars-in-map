package map_generator;

import file_operations.map_reader.MapLoader;
import file_operations.map_reader.MapLocator;
import model.web.WebMap;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Generates an in app memory of a WebMap that is read from map directory.
 * User is asked which map from the directory is loaded. Uses MapLocator to find maps.
 */
public class MapGeneratorFromFiles implements MapGenerator {
    private Scanner scanner;
    private static final String USER_WANTS_TO_EXIT = "q";
    private MapLocator mapLocator;
    private MapLoader mapLoader;

    public MapGeneratorFromFiles(Scanner scanner) {
        this.scanner = scanner;
        mapLocator = new MapLocator();
        mapLoader = new MapLoader();
    }

    /**
     * if maps folder exists and has files then asks user which map to load
     *
     * @return read map from file, if file has mistakes or map is not valid returns null. refer to app_definition.md for a valid map file.
     */
    @Override
    public WebMap createMap() {
        while (true) {
            try {
                System.out.println("Choose map by typing its number");
                System.out.println("Found maps:");
                var foundMaps = listMaps();
                for (int i = 0; i < foundMaps.size(); i++) {
                    System.out.println(i + ": " + foundMaps.get(i).getName());
                }
                System.out.println("Press " + USER_WANTS_TO_EXIT + " to exit");
                var userChoice = scanner.nextLine();
                if (userChoice.equals(USER_WANTS_TO_EXIT)) {
                    return null;
                } else {
                    return mapLoader.loadMapFromFile(foundMaps.get(Integer.parseInt(userChoice)));
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
        List<File> foundMaps = mapLocator.findMaps();
        if (foundMaps.size() == 0) {
            throw new IOException("No maps found in folder");
        } else {
            return foundMaps;
        }
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
