package app;

import mapGenerator.MapGenerator;
import mapGenerator.NoWeightSimpleGenerator;
import model.WebMap;
import searchAlgorithm.BreathSearch;
import searchAlgorithm.SearchAlgorithm;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  Main program that consists of two parts.
 *  Generating map of WebMap class for an algorithm.
 *  Running an algorithm of choice from list with set map and writes results on a file.
 */

public class App {

    private static final String GENERATE_MAP = "1", RUN_ALGORITHM_ANALYSIS = "2", DEV_DERP = "3";
    private static final String BREATH_WIDTH = "b";
    private static final String SIMPLE_GENERATOR = "s";
    private static final boolean RUNNING = true, STOPPED = false;

    private final Scanner scanner;
    private Map<String, MapGenerator> mapGenerators;
    private Map<String, SearchAlgorithm> algorithmMap;
    private static boolean state;
    private WebMap currentMap;
    private final boolean devMode;

    public App(boolean devMode, Scanner scanner) {
        this.devMode = devMode;
        this.scanner = scanner;
        state = STOPPED;
        currentMap = new WebMap();
        algorithmMap = new HashMap<>();
        algorithmMap.put(BREATH_WIDTH, new BreathSearch());

        mapGenerators = new HashMap<>();
        mapGenerators.put(SIMPLE_GENERATOR, new NoWeightSimpleGenerator(scanner));

    }

    /**
     *  Launches main program if not running already.
     *  User is asked for interaction.
     *  User is guided through options.
     *  todo add path where to save test results later as param.
     */


    public void run() {
        state = RUNNING;
        while (state == RUNNING) {

            System.out.println("press " + GENERATE_MAP + " to generate map");
            System.out.println("press " + RUN_ALGORITHM_ANALYSIS + " to run search algorhitm on set map");
            System.out.println("press anything else to quit");
            String command = scanner.nextLine();

            switch (command) {
                case GENERATE_MAP:
                    generateMap();
                    break;
                case RUN_ALGORITHM_ANALYSIS:
                    runSearch();
                    break;
                case DEV_DERP: // testing file creation currently
                    if (devMode) {
                        try {
                            var algorithm = algorithmMap.get(BREATH_WIDTH);
                            currentMap.setTileTarget(new Point(2, 1));
                            currentMap.setTileAt(new Point(2, 2));
                            currentMap.setMap(new int[3][3]);
                            algorithm.setMapClean(currentMap);
                            algorithm.runSearch();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        state = STOPPED;
                    }
                    break;

                default:
                    state = STOPPED;
            }
        }
    }

    /**
     *  Sets map to be used for analysis.
     *  Requires user input.
     *  User is guided through options.
     *  todo load map option
     */
    private void generateMap() {
        try {
            System.out.println("Choose generator to set map:");
            for (Map.Entry<String, MapGenerator> pair : mapGenerators.entrySet()) {
                System.out.println("press " + pair.getKey() + " for using: " + pair.getValue().toString());
            }
            System.out.println("Press anything else to return to analysis program.");
            this.currentMap = mapGenerators.get(scanner.nextLine()).createMap();
            System.out.println("Map has been set!");
            return;
        } catch (Exception e) {
            System.out.println("Errors in input try again");
            if (devMode) e.printStackTrace();
        }
    }

    /**
     *  Runs search algorithm analysis of users choice from list that app has.
     *  Requires user input.
     *  User is guided through options.
     */

    private void runSearch() {
        if (currentMap != null && currentMap.isValid()) {
            try {
                System.out.println("Chosen map:");
                System.out.println(currentMap.getTextualView());
                System.out.println("Choose algorithm to run on map:");
                for (Map.Entry<String, SearchAlgorithm> pair : algorithmMap.entrySet()) {
                    System.out.println("press " + pair.getKey() + " for testing: " + pair.getValue().toString());
                }
                var algorithm = algorithmMap.get(scanner.nextLine());
                algorithm.setMapClean(currentMap);
                algorithm.runSearch();
                System.out.println("Finished search. Returning to main program.");
                return;
            } catch (NullPointerException e) {
                System.out.println("Errors. Going back to analysis program.");
                if (devMode) e.printStackTrace();
                return;
            } catch (IOException e) {
                System.out.println("Unable to handle IO operations");
                if (devMode) e.printStackTrace();
                return;
            }
        } else {
            System.out.println("No map set. returning to main program");
        }
    }

    // for testing
   public void setAlgorithmMap(Map<String, SearchAlgorithm> algorithmMap) {
        this.algorithmMap = algorithmMap;
   }

    public void setMapGenerators(Map<String, MapGenerator> mapGenerators) {
        this.mapGenerators = mapGenerators;
    }

    public void setCurrentMap(WebMap currentMap) {
        this.currentMap = currentMap;
    }
}
