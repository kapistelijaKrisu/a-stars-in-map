package mapGenerator;

import model.web.WebMap;

public interface MapGenerator {

    /**
     * @return WebMap for a SearchAlgorithm to use.
     */
    WebMap createMap();
}
