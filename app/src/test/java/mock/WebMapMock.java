package mock;

import model.web.WebMap;

public class WebMapMock {
    /**
     *  S 0
     *  0 T
     * @return mvp map
     */
    public static WebMap getMinimumValidMap() {
        var mockMap = new WebMap();
        mockMap.setMap(new int[2][2]);
        mockMap.setTileStart(0, 0);
        mockMap.setTileTarget(1, 1);
        mockMap.getMap()[1][1] = 1;
        mockMap.getMap()[0][0] = 1;
        return mockMap;
    }

    /**
     *   T = 4
     *   S = 2
     *   1 1 1 0 1 1 1
     *   S 0 2 2 2 2 2
     *   3 0 3 0 3 3 3
     *   4 0 4 0 T 4 4
     *   5 5 5 0 5 5 5
     *   6 6 6 6 6 6 6
     * @return valid map
     */
    public static WebMap getValid6x7Map() {
        var mockMap = new WebMap();
        mockMap.setTileStart(0, 1);
        mockMap.setTileTarget(4, 3);
        int[][] map = new int[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                map[i][j] = i+1;
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
        return mockMap;
    }

}
