package mapGenerator;

import model.WebMap;

import java.awt.*;

/**
 * A WebMap generator which needs an implementation of how WebMap values are set.
 */

public abstract class MapGenerator {

    protected int width, height;
    protected int startX, startY;
    protected int targetX, targetY;

    /**
     * @return coordinates of to be generated map.
     */
    protected abstract int[][] generateTiles();

    /**
     * Set int startX, startY, targetX, targetY here however it is wanted.
     */
    protected abstract void setConfigValues();

    protected WebMap generateMap() {
        var webMap = new WebMap();
        webMap.setMap(generateTiles());
        webMap.setTileAt(new Point(startX, startY));
        webMap.setTileTarget(new Point(targetX, targetY));
        return webMap;
    }



    /**
     * @return WebMap for a SearchAlgorithm to use.
     * WebMap map is set by generateTiles().
     * WebMap start point and target point are set by int startX, startY, targetX, targetY,
     * which are configured in setConfigValues.
     */
    public WebMap createMap() {
        setConfigValues();
        WebMap map = generateMap();
        return  map;
    }
}
