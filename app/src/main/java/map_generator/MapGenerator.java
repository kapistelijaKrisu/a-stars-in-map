package map_generator;

import model.web.WebMap;

/**
 * Interface for generating a WebMap
 */
public interface MapGenerator {

    /**
     * @return WebMap for a AnalysableAlgorithm to use.
     */
    WebMap createMap();
}
