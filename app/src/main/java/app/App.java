package app;

import map_generator.MapGenerator;
import map_generator.MapGeneratorFromFiles;
import map_generator.NoWeightSimpleGenerator;
import model.web.WebMap;
import file_operations.analysis_writer.AnalysisWriter;
import search_algorithm.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main program that consists of two parts.
 * Generating map instance of WebMap class for an algorithm.
 * Running an algorithm of choice from list with set map and writes results on a file.
 */

public class App {

    private static final String GENERATE_MAP = "1", RUN_ALGORITHM_ANALYSIS = "2";
    private static final String WIDTH_FIRST = "b", DEPTH_FIRST = "s", DIJKSTRA = "d", A_STAR = "a";
    private static final String SIMPLE_GENERATOR = "s", LOAD_MAP_GENERATOR = "d";
    private static final boolean RUNNING = true, STOPPED = false;

    private final Scanner scanner;
    private Map<String, MapGenerator> mapGenerators;
    private Map<String, AnalysableAlgorithm> algorithmMap;
    private boolean state;
    private WebMap currentMap;

    /**
     * Sets up app to be ready to run.
     *
     * @param scanner        scanner for user input to be used and shared with rest of the app.
     * @param analysisWriter writer to write down search analysis results.
     */
    public App(Scanner scanner, AnalysisWriter analysisWriter) {
        this.scanner = scanner;
        state = STOPPED;
        currentMap = new WebMap();
        algorithmMap = new HashMap<>();
        algorithmMap.put(WIDTH_FIRST, new BreathSearch(analysisWriter));
        algorithmMap.put(DEPTH_FIRST, new DepthSearch(analysisWriter));
        algorithmMap.put(DIJKSTRA, new Dijkstra(analysisWriter));
        algorithmMap.put(A_STAR, new AStar(analysisWriter));

        mapGenerators = new HashMap<>();
        mapGenerators.put(SIMPLE_GENERATOR, new NoWeightSimpleGenerator(scanner));
        mapGenerators.put(LOAD_MAP_GENERATOR, new MapGeneratorFromFiles(scanner));

    }

    /**
     * Launches main program if not running already.
     * User is asked for interaction.
     * User is guided through options.
     * Test results will be on same level as program in a path /doc/reports/
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
                default:
                    state = STOPPED;
                    break;
            }
        }
    }

    private void generateMap() {
        try {
            System.out.println("Choose generator to set map:");
            for (Map.Entry<String, MapGenerator> pair : mapGenerators.entrySet()) {
                System.out.println("press " + pair.getKey() + " for using: " + pair.getValue().toString());
            }
            System.out.println("Press anything else to return to analysis program.");
            var generatedMap = mapGenerators.get(scanner.nextLine()).createMap();
            if (generatedMap == null || !generatedMap.isValid()) {
                System.out.println("Errors in generated map. Map was not set!");
            } else {
                this.currentMap = generatedMap;
                System.out.println("Map has been set!");
            }
            return;
        } catch (Exception e) {
            System.out.println("Errors in input try again");
        }
    }

    private void runSearch() {
        if (currentMap != null && currentMap.isValid()) {
            try {
                System.out.println("Chosen map:");
                System.out.println(currentMap.getTextualView());
                System.out.println("Choose algorithm to run on map:");
                for (Map.Entry<String, AnalysableAlgorithm> pair : algorithmMap.entrySet()) {
                    System.out.println("press " + pair.getKey() + " for testing: " + pair.getValue().toString());
                }
                var algorithm = algorithmMap.get(scanner.nextLine());
                algorithm.setMapClean(currentMap);
                algorithm.runSearch();
                System.out.println("Finished search. Returning to main program.");
                return;
            } catch (NullPointerException | IOException e) {
                System.out.println("Errors. Going back to analysis program.");
                return;
            }
        } else {
            System.out.println("No map set. returning to main program");
        }
    }

    // for testing
    public void setAlgorithmMap(Map<String, AnalysableAlgorithm> algorithmMap) {
        this.algorithmMap = algorithmMap;
    }

    public void setMapGenerators(Map<String, MapGenerator> mapGenerators) {
        this.mapGenerators = mapGenerators;
    }

    public void setCurrentMap(WebMap currentMap) {
        this.currentMap = currentMap;
    }
}
