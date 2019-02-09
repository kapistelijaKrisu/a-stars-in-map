package app;

import IOoperations.analysisWriter.AnalysisWriter;
import model.WebMap;
import searchAlgorithm.BreathSearch;
import searchAlgorithm.DepthSearch;

import java.awt.Point;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * Entry point for program
     * 1st argument is devmode, true if argument is true else false.
     * @param args entrypoint arguments.
     */
    public static void main(String[] args) throws IOException {

        AnalysisWriter analysisWriter = new AnalysisWriter();
        boolean devMode = args.length == 0 || args[0].equals("true");
        var app = new App(devMode, new Scanner(System.in), analysisWriter);
        app.run();

        /* for testin
        var mockMap = new WebMap();
        mockMap.setTileAt(new Point(0,0));
        mockMap.setTileTarget(new Point(10,4));

        int size = 33;
        var m = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m[i][j] = 1;
            }
        }
        mockMap.setMap(m);

        BreathSearch b = new BreathSearch(analysisWriter);
        b.setMapClean(mockMap);
        DepthSearch d = new DepthSearch(analysisWriter);
        d.setMapClean(mockMap);

        b.runSearch();
        d.runSearch();*/

    }
}
