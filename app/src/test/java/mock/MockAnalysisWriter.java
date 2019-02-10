package mock;

import IOoperations.analysisWriter.AnalysisWriter;
import IOoperations.analysisWriter.ReportValidator;

import java.io.IOException;
import java.util.Map;


public class MockAnalysisWriter extends AnalysisWriter {

    private boolean validatingReturnedTrue;
    private Map<String, String> receivedReplacingValues;
    private String receivedPath;
    private static final ReportValidator reportValidator = new ReportValidator();

    @Override
    public void writeReport(Map<String, String> replacingValues, String path) throws IOException, IllegalArgumentException {
        this.validatingReturnedTrue = MockAnalysisWriter.reportValidator.validateMapper(replacingValues);
        this.receivedPath = path;
        this.receivedReplacingValues = replacingValues;
    }

    public String receivedAlgorithm() {
        return receivedReplacingValues.get("{algorithm}");
    }

    public String receivedMapInfo() {
        return receivedReplacingValues.get("{map_info}");
    }

    public String receivedAlDoc() {
        return receivedReplacingValues.get("{al_doc}");
    }

    public String receivedAlSpace() {
        return receivedReplacingValues.get("{al_space}");
    }

    public String receivedAlTime() {
        return receivedReplacingValues.get("{al_time}");
    }

    public String receivedTestUsedSteps() {
        return receivedReplacingValues.get("{test_used_steps}");
    }

    public String receivedTestMaxSteps() {
        return receivedReplacingValues.get("{test_max_steps}");
    }

    public String receivedTestPathWeight() {
        return receivedReplacingValues.get("{test_path_weight}");
    }

    public String receivedProcessedMap() {
        return receivedReplacingValues.get("{test_processed_map}");
    }

    public boolean isValidatingReturnedTrue() {
        return validatingReturnedTrue;
    }

    public Map<String, String> getReceivedReplacingValues() {
        return receivedReplacingValues;
    }

    public String getReceivedPath() {
        return receivedPath;
    }
}
