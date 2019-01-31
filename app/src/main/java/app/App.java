package app;

import mapGenerator.MapGenerator;
import mapGenerator.NoWeightSimpleGenerator;
import model.WebMap;
import searchAlgorithm.BreathSearch;
import searchAlgorithm.SearchAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    private final Scanner scanner;
    private Map<String, MapGenerator> mapGenerators;
    private Map<String, SearchAlgorithm> algorithmMap;
    private int state;
    private WebMap currentMap;
    private final boolean devMode;

    //todo magic numbers given names
    public App(boolean devMode, Scanner scanner) {
        this.devMode = devMode;
        this.scanner = scanner;
        state = 0;
        currentMap = new WebMap();
        algorithmMap = new HashMap<>();
        algorithmMap.put("b", new BreathSearch());

        mapGenerators = new HashMap<>();
        mapGenerators.put("s", new NoWeightSimpleGenerator(scanner));

    }

    /* todo add path where to save test results later as param
    todo load map option*/
    public void run() {
        state = 1;
        while (state != 0) {

            System.out.println("press 1 to generate map");
            System.out.println("press 2 to run search algorhitm on set map");
            System.out.println("press anything else to quit");
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    generateMap();
                    break;
                case "2":
                    runSearch();
                    break;

                default:
                    state = 0;
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
            this.currentMap = mapGenerators.get(scanner.nextLine()).createMap();
            System.out.println("Map has been set!");
            return;
        } catch (Exception e) {
            System.out.println("Errors in input try again");
            if (devMode) e.printStackTrace();
        }
    }


    private void runSearch() {
        if (currentMap != null && currentMap.isValid()) {
            try {
                System.out.println("Chosen map:");
                System.out.println(currentMap.toString());
                System.out.println("Choose algorithm to run on map:");
                for (Map.Entry<String, SearchAlgorithm> pair : algorithmMap.entrySet()) {
                    System.out.println("press " + pair.getKey() + " for testing: " + pair.getValue().toString());
                }
                algorithmMap.get(scanner.nextLine()).runSearch(currentMap);
                System.out.println("Finished search. Returning to main program.");
                return;
            } catch (NullPointerException e) {
                System.out.println("Errors. Going back to analysis program.");
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
