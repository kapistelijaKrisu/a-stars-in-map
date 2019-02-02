package searchAlgorithm;

import model.WebMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class that does all the ground work for preparing analysis file.
 * work in progress
 */
public abstract class SearchAlgorithm {
    protected WebMap map;
    protected Map<String, String> templateValueMap;
    private String reportFilePath;
    private final AnalysisWriter analysisWriter;


    public SearchAlgorithm() {
        analysisWriter = new AnalysisWriter();
        templateValueMap = new HashMap<>();
    }

    /**
     * Runs implemented algorithm and writes report based on it
     * todo announce completion details...where results written etc
     * @throws IllegalStateException when algorithm or map does not have name
     * @throws IOException failing to write on designated report file
     */
    public void runSearch() throws IllegalStateException, IOException {
        if (reportFilePath == null) {
            if (map == null || !map.isValid()) throw new IllegalStateException("Requires valid map and name, and name for algorithm");
            reportFilePath = "/doc/reports/" + map.getName() + "/" + toString();
        }
        searchAlgorithm();
        analysisWriter.writeReport(templateValueMap, reportFilePath);
    }

    /**
     * Implementation of the search algorithm
     */
    protected abstract void searchAlgorithm();

    /**
     * Directory is based on map name as well and gets cleaned when a map is set
     * for upkeepin reporting structure.
     * Other than that it's a normal setter.
     * @param map map to be set
     */
    public void setMapClean(WebMap map) {
        this.map = map;
        this.reportFilePath = null;
    }

    // for testing
    public WebMap getMap() {
        return map;
    }

    public String getReportFilePath() {
        return reportFilePath;
    }

    public void setReportFilePath(String reportFilePath) {
        this.reportFilePath = reportFilePath;
    }
}
