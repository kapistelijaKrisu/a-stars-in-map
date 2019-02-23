package app;

import file_operations.analysis_writer.AnalysisWriter;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * Entry point for program
     * @param args entrypoint arguments. Not used.
     */
    public static void main(String[] args) throws IOException {

        AnalysisWriter analysisWriter = new AnalysisWriter();
        var app = new App(new Scanner(System.in), analysisWriter);
        app.run();
/* test data...
        AnalysisWriter analysisWriter = new AnalysisWriter();
        var app = new App(new Scanner(System.in), analysisWriter);
        var aaa = new CustomDijkstra(analysisWriter);
        var aa = new AStar(analysisWriter);

        var mockMap = new WebMap();
        mockMap.setTileStart(0, 2);
        mockMap.setTileTarget(14, 3);
        int[][] map = new int[6][17];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 17; j++) {
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

        aaa.setMapClean(mockMap);
        aa.setMapClean(mockMap);

        aa.runSearch();
        aaa.runSearch();*/
    }
}
