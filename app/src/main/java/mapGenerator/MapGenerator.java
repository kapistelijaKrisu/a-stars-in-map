package mapGenerator;

import model.WebMap;

public interface MapGenerator {

    /**
     * @return WebMap for a SearchAlgorithm to use.
     * WebMap map is set by generateTiles().
     * WebMap mapStartLocation point and mapTargetLocation point are set by int startX, startY, targetX, targetY,
     * which should be configured in setConfigValues.
     */
    public WebMap createMap();
}
