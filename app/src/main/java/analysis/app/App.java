package main.java.analysis.app;

import main.java.analysis.mapGenerator.MapGenerator;
import main.java.analysis.mapGenerator.NoWeightSimpleGenerator;
import main.java.analysis.model.WebMap;
import main.java.analysis.searchAlgorithm.BreathSearch;
import main.java.analysis.searchAlgorithm.SearchAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    private final Scanner scanner;
    private final Map<String, MapGenerator> mapGenerators;
    private final Map<String, SearchAlgorithm> settingsMap;
    private int state;
    private WebMap currentMap;
    private final boolean devMode;

    //todo magic numbers given names
    public App(boolean devMode) {
        this.devMode = devMode;
        this.scanner = new Scanner(System.in);
        state = 0;
        currentMap = new WebMap();

        settingsMap = new HashMap<>();
        settingsMap.put("b", new BreathSearch());

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
        while (true) {
            try {
                System.out.println("Chosen map:");
                System.out.println(currentMap.toString());
                System.out.println("Choose algorithm to run on map:");
                for (Map.Entry<String, SearchAlgorithm> pair : settingsMap.entrySet()) {
                    System.out.println("press " + pair.getKey() + " for testing: " + pair.getValue().toString());
                }
                settingsMap.get(scanner.nextLine()).runSearch(currentMap);
                return;
            } catch (NullPointerException e) {
                System.out.println("Errors.Going back to analysis program.");
                if (devMode) e.printStackTrace();
                return;
            }
        }
    }
}
