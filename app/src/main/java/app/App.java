package app;

import file_operations.analysis_overview.AnalysisCollector;
import file_operations.analysis_overview.AnalysisInspector;
import file_operations.analysis_writer.AnalysisWriter;
import map_generator.MapGenerator;
import map_generator.MapGeneratorFromFiles;
import map_generator.NoWeightSimpleGenerator;
import model.structure.structure_type_enum.*;
import model.web.WebMap;
import search_algorithm.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main program that consists of three parts.
 * Generating map instance of WebMap class for an algorithm.
 * Running an algorithm of choice from list with set map and writes results on a file.
 * Creating statistic of existing report files
 */

public class App {

    private static final String GENERATE_MAP = "1", RUN_ALGORITHM_ANALYSIS = "2", CREATE_STATISTICS = "3";
    private static final String SIMPLE_GENERATOR = "s", LOAD_MAP_GENERATOR = "d";
    private static final boolean RUNNING = true, STOPPED = false;
    private static final String OPTION_TO_RUN_ALL_SEARCHES = "a";

    private Scanner scanner;
    private Map<String, MapGenerator> mapGenerators;
    private Map<AlgorithmCodeKey, AnalysableAlgorithm> algorithmMap;
    private AnalysisInspector analysisInspector;
    private AnalysisCollector analysisCollector;
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
        analysisInspector = new AnalysisInspector();
        analysisCollector = new AnalysisCollector();
        setUpAlgorithms(analysisWriter);
    }

    private void setUpAlgorithms(AnalysisWriter analysisWriter) {
        algorithmMap = new TreeMap<>();

        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_CUSTOM_STACK_AND_PRE_MADE_HASH_SET, new DepthSearch(analysisWriter, StackType.CUSTOM_STACK, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.CUSTOM_STACK, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.CUSTOM_STACK, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_PRE_MADE_HASH_SET, new DepthSearch(analysisWriter, StackType.PRE_MADE_STACK, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.PRE_MADE_STACK, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new DepthSearch(analysisWriter, StackType.PRE_MADE_STACK, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));

        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_CUSTOM_STACK_AND_PRE_MADE_HASH_SET, new BreathSearch(analysisWriter, QueueType.CUSTOM_QUEUE, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.CUSTOM_QUEUE, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.CUSTOM_QUEUE, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_PRE_MADE_HASH_SET, new BreathSearch(analysisWriter, QueueType.PRE_MADE_QUEUE, UniqueSetType.PRE_MADE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_SET_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.PRE_MADE_QUEUE, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET));
        algorithmMap.put(AlgorithmCodeKey.WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET, new BreathSearch(analysisWriter, QueueType.PRE_MADE_QUEUE, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET));

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
            System.out.println("press " + CREATE_STATISTICS + " to create statistics based on reports for each map");
            System.out.println("press anything else to quit");
            String command = scanner.nextLine();

            switch (command) {
                case GENERATE_MAP:
                    generateMap();
                    break;
                case RUN_ALGORITHM_ANALYSIS:
                    runSearch();
                    break;
                case CREATE_STATISTICS:
                    createStatistics();
                    break;
                default:
                    state = STOPPED;
                    break;
            }
        }
    }

    private void createStatistics() {
        var reports = analysisCollector.collectFlattenedReports();
        analysisInspector.createAnalysisStatisticByMap(reports);

    }

    private void generateMap() {
        try {
            System.out.println("Choose generator to set map:");
            for (Map.Entry<String, MapGenerator> pair : mapGenerators.entrySet()) {
                System.out.println("press " + pair.getKey() + " for using: " + pair.getValue().toString());
            }
            System.out.println("Press anything else to return to analysis program.");
            String userChoice = scanner.nextLine();
            var generatedMap = mapGenerators.get(userChoice).createMap();
            if (generatedMap == null || !generatedMap.isValid()) {
                System.out.println("Errors in generated map. Map was not set!");
            } else {
                this.currentMap = generatedMap;
                System.out.println("Map has been set!");
                System.out.println("Chosen map:");
                System.out.println(currentMap.getTextualView());
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Errors in input try again");
        }
    }

    private void runSearch() {
        if (currentMap != null && currentMap.isValid()) {
            System.out.println("Choose algorithm to run on map:");
            try {
                var filteredByName = filterAlgorithmsByName(algorithmMap);
                if (filteredByName.size() == algorithmMap.size()) {
                    runAllAlgorithmAnalysis(filteredByName.values());
                } else {
                    var filteredByImplementationType = filterAlgorithmsByAlgorithmCodeKey(filteredByName);
                    runAllAlgorithmAnalysis(filteredByImplementationType);
                }
            } catch (NullPointerException | IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Error. Input invalid. Returning back to main menu.");
            } catch (IOException | IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
                System.out.println("Errors. Returning back to main menu.");
            }
        } else {
            System.out.println("No map set. returning to main program");
        }
    }

    private Map<AlgorithmCodeKey, AnalysableAlgorithm> filterAlgorithmsByName(Map<AlgorithmCodeKey, AnalysableAlgorithm> filterFrom) {
        List<String> algorithmsByCriteria = filterFrom.values().stream().map(AnalysableAlgorithm::getName).distinct().collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < algorithmsByCriteria.size(); i++) {
            System.out.println("press " + i + " for choosing: " + algorithmsByCriteria.get(i));
        }
        System.out.println("press " + OPTION_TO_RUN_ALL_SEARCHES + " for choosing All");
        String upperOption = scanner.nextLine();

        if (upperOption.equals(OPTION_TO_RUN_ALL_SEARCHES)) {
            return filterFrom;
        } else {
            var chosenAlgorithmName = algorithmsByCriteria.get(Integer.parseInt(upperOption));
            var chosenAlgorithms = filterFrom.entrySet().stream().filter(e -> e.getValue().getName().equals(chosenAlgorithmName))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if (chosenAlgorithms.isEmpty()) throw new IndexOutOfBoundsException();
            return chosenAlgorithms;
        }
    }

    private Collection<AnalysableAlgorithm> filterAlgorithmsByAlgorithmCodeKey(Map<AlgorithmCodeKey, AnalysableAlgorithm> filterFrom) {
        for (Map.Entry<AlgorithmCodeKey, AnalysableAlgorithm> pair : filterFrom.entrySet()) {
            System.out.println("press " + pair.getKey().getStringValue() + " for choosing: " + pair.getKey().getStringValue());
        }
        System.out.println("press " + OPTION_TO_RUN_ALL_SEARCHES + " for choosing All");

        String upperOption = scanner.nextLine();

        if (upperOption.equals(OPTION_TO_RUN_ALL_SEARCHES)) {
            return filterFrom.values();
        } else {
            return filterFrom.entrySet().stream().filter(e -> upperOption.equals(e.getKey().getStringValue()))
                    .map(Map.Entry::getValue).collect(Collectors.toCollection(ArrayList::new));
        }
    }

    private void runAllAlgorithmAnalysis(Collection<AnalysableAlgorithm> algorithmList) throws IOException {
        for (AnalysableAlgorithm analysableAlgorithm : algorithmList) {
            analysableAlgorithm.setMapClean(currentMap);
            analysableAlgorithm.runSearch();
        }
    }

    /**
     * setter for testing
     *
     * @param algorithmMap setter
     */
    public void setAlgorithmMap(Map<AlgorithmCodeKey, AnalysableAlgorithm> algorithmMap) {
        this.algorithmMap = algorithmMap;
    }

    /**
     * setter for testing
     *
     * @param mapGenerators setter
     */
    public void setMapGenerators(Map<String, MapGenerator> mapGenerators) {
        this.mapGenerators = mapGenerators;
    }

    /**
     * setter for testing
     *
     * @param currentMap setter
     */
    public void setCurrentMap(WebMap currentMap) {
        this.currentMap = currentMap;
    }

    /**
     * getter for testing
     *
     * @return currentMap
     */
    public WebMap getCurrentMap() {
        return currentMap;
    }

    /**
     * setter for testing
     *
     * @param analysisInspector setter
     */
    public void setAnalysisInspector(AnalysisInspector analysisInspector) {
        this.analysisInspector = analysisInspector;
    }

    /**
     * setter for testing
     *
     * @param scanner setter
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
