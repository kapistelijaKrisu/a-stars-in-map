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

/*         for testin
        var mockMap = new WebMap();
        mockMap.setTileStart(new Point(0, 1));
        mockMap.setTileTarget(new Point(4, 3));
        int[][] map = new int[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                map[i][j] = 1;
            }
        }
        map[0][3] = 0;
        map[1][1] = 0;
        map[2][1] = 0;
        map[2][3] = 0;
        map[3][1] = 0;
        map[3][3] = 0;
        map[4][3] = 0;
        mockMap.setMap(map);

        BreathSearch b = new BreathSearch(analysisWriter);
        b.setMapClean(mockMap);
        DepthSearch d = new DepthSearch(analysisWriter);
        d.setMapClean(mockMap);

        b.runSearch();
        d.runSearch();*/

    }
}
