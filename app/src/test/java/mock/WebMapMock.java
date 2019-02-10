package mock;

import model.WebMap;

import java.awt.Point;

public class WebMapMock {
    /**
     *  S 0
     *  0 T
     * @return mvp map
     */
    public static WebMap getMinimumValidMap() {
        var mockMap = new WebMap();
        mockMap.setMap(new int[2][2]);
        mockMap.setTileStart(new Point(0, 0));
        mockMap.setTileTarget(new Point(1, 1));
        mockMap.getMap()[1][1] = 1;
        return mockMap;
    }

    /**
     *   1 1 1 0 1 1 1
     *   S 0 1 1 1 1 1
     *   1 0 1 0 1 1 1
     *   1 0 1 0 T 1 1
     *   1 1 1 0 1 1 1
     *   1 1 1 1 1 1 1
     * @return valid map
     */
    public static WebMap getValid6x7Map() {
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
        return mockMap;
    }

}
