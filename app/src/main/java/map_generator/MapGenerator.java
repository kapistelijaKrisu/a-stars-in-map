package map_generator;

import model.web.WebMap;

public interface MapGenerator {

    /**
     * @return WebMap for a AnalysableAlgorithm to use.
     */
    WebMap createMap();
}