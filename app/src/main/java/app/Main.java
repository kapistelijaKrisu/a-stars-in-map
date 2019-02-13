package app;

import IOoperations.analysisWriter.AnalysisWriter;
import model.WebMap;
import searchAlgorithm.BreathSearch;
import searchAlgorithm.DepthSearch;
import searchAlgorithm.Dijkstra;

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
  //      app.run();

//         for testin
        var mockMap = new WebMap();
        mockMap.setTileStart(0, 1);
        mockMap.setTileTarget(4, 3);
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

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                System.out.print(map[y][x]+ " ");
            }
            System.out.println("");
        }
        System.out.println("");
      //  mockMap.getNeighbours(new WeightedPoint(4,2));
        /*

```
v v v # v v v   0
S # v v v v v   1
X # v # v v v   2
X # v # N . .   3
X X X # X v v   4
v v X X X v v   5

0 1 2 3 4 5 6

1 1 1 0 1 1 1
1 0 1 1 1 1 1
1 0 1 0 1 1 1
1 0 1 0 1 1 1
1 1 1 0 1 1 1
1 1 1 1 1 1 1
         */

        BreathSearch b = new BreathSearch(analysisWriter);
        b.setMapClean(mockMap);
        DepthSearch d = new DepthSearch(analysisWriter);
        d.setMapClean(mockMap);
        Dijkstra dd = new Dijkstra(analysisWriter);
        dd.setMapClean(mockMap);

        b.runSearch();
        d.runSearch();
        dd.runSearch();

    }
}
