package app;

import file_operations.analysis_writer.AnalysisWriter;
import map_generator.MapGenerator;
import map_generator.MapGeneratorFromFiles;
import map_generator.NoWeightSimpleGenerator;
import model.web.*;
import search_algorithm.*;
import search_algorithm.structure_type.*;

import java.io.IOException;
import java.util.*;

/**
 * Main program that consists of two parts.
 * Generating map instance of WebMap class for an algorithm.
 * Running an algorithm of choice from list with set map and writes results on a file.
 */

public class App {

    private static final String GENERATE_MAP = "1", RUN_ALGORITHM_ANALYSIS = "2";
    private static final String SIMPLE_GENERATOR = "s", LOAD_MAP_GENERATOR = "d";
    private static final boolean RUNNING = true, STOPPED = false;

    private final Scanner scanner;
    private Map<String, MapGenerator> mapGenerators;
    private Map<AlgorithmCodeKey, AnalysableAlgorithm> algorithmMap;
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

        mapGenerators = new HashMap<>();
        mapGenerators.put(SIMPLE_GENERATOR, new NoWeightSimpleGenerator(scanner));
        mapGenerators.put(LOAD_MAP_GENERATOR, new MapGeneratorFromFiles(scanner));
        setUpAlgorithmMap(analysisWriter);

    }

    private void setUpAlgorithmMap(AnalysisWriter analysisWriter) {
        algorithmMap = new TreeMap<>();
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_CUSTOM_STACK_AND_PRE_MADE_HASH_SET, new BreathSearch(analysisWriter, QueueType.CUSTOM_QUEUE, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.CUSTOM_QUEUE, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.CUSTOM_QUEUE, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_PRE_MADE_HASH_SET, new BreathSearch(analysisWriter, QueueType.PRE_MADE_QUEUE, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.PRE_MADE_QUEUE, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.PRE_MADE_QUEUE, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_CUSTOM_STACK_AND_PRE_MADE_HASH_SET, new DepthSearch(analysisWriter, StackType.CUSTOM_STACK, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.CUSTOM_STACK, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.CUSTOM_STACK, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_PRE_MADE_HASH_SET, new DepthSearch(analysisWriter, StackType.PRE_MADE_STACK, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.PRE_MADE_STACK, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.PRE_MADE_STACK, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DIJKSTRA_WITH_CUSTOM_HEAP_AND_2D_DISTANCES_ARRAY, new Dijkstra(analysisWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_2D));
        algorithmMap.put(AlgorithmCodeKey.DIJKSTRA_WITH_CUSTOM_HEAP_AND_1D_DISTANCES_ARRAY, new Dijkstra(analysisWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_1D));
        algorithmMap.put(AlgorithmCodeKey.DIJKSTRA_WITH_PRE_MADE_HEAP_AND_2D_DISTANCES_ARRAY, new Dijkstra(analysisWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_2D));
        algorithmMap.put(AlgorithmCodeKey.DIJKSTRA_WITH_PRE_MADE_HEAP_AND_1D_DISTANCES_ARRAY, new Dijkstra(analysisWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_1D));
        algorithmMap.put(AlgorithmCodeKey.A_STAR_WITH_CUSTOM_HEAP_AND_2D_DISTANCES_ARRAY, new AStar(analysisWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_2D));
        algorithmMap.put(AlgorithmCodeKey.A_STAR_WITH_CUSTOM_HEAP_AND_1D_DISTANCES_ARRAY, new AStar(analysisWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_1D));
        algorithmMap.put(AlgorithmCodeKey.A_STAR_WITH_PRE_MADE_HEAP_AND_2D_DISTANCES_ARRAY, new AStar(analysisWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_2D));
        algorithmMap.put(AlgorithmCodeKey.A_STAR_WITH_PRE_MADE_HEAP_AND_1D_DISTANCES_ARRAY, new AStar(analysisWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_1D));

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
                for (Map.Entry<AlgorithmCodeKey, AnalysableAlgorithm> pair : algorithmMap.entrySet()) {
                    System.out.println("press " + pair.getKey().getStringValue() + " for testing: " + pair.getValue().getDescription());
                }
                var algorithm = algorithmMap.get(AlgorithmCodeKey.get(scanner.nextLine()));
                algorithm.setMapClean(currentMap);
                algorithm.runSearch();
                System.out.println("Finished search. Returning to main menu.");
            } catch (IOException e) {
                System.out.println("Errors. Returning back to main menu.");
            } catch (NullPointerException e) {
                System.out.println("Error. Input invalid. Returning back to main menu.");
            }
        } else {
            System.out.println("No map set. returning to main program");
        }
    }

    // for testing
    public void setAlgorithmMap(Map<AlgorithmCodeKey, AnalysableAlgorithm> algorithmMap) {
        this.algorithmMap = algorithmMap;
    }

    public void setMapGenerators(Map<String, MapGenerator> mapGenerators) {
        this.mapGenerators = mapGenerators;
    }

    public void setCurrentMap(WebMap currentMap) {
        this.currentMap = currentMap;
    }
}
