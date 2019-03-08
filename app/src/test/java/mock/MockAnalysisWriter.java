package mock;

import file_operations.analysis_writer.AnalysisWriter;
import model.report.Report;
import model.report.ReportCodeKey;
import model.report.ReportMeta;


public class MockAnalysisWriter extends AnalysisWriter {

    private boolean validatingReturnedTrue;
    private Report receivedReport;
    private ReportMeta receivedReportMeta;
    private String receivedPath;

    @Override
    public void writeReport(Report report, ReportMeta reportMeta, String path) throws IllegalArgumentException {
        this.receivedPath = path;
        this.receivedReport = report;
        this.receivedReportMeta = reportMeta;
        this.validatingReturnedTrue = receivedReport.isValid();
    }

    public String receivedAlgorithm() {
        return receivedReport.getValueOf(ReportCodeKey.ALGORITHM_NAME);
    }

    public String receivedMapInfo() {
        return receivedReport.getValueOf(ReportCodeKey.MAP_INFO);
    }

    public String receivedAlDoc() {
        return receivedReport.getValueOf(ReportCodeKey.IMPLEMENTATION_INFO);
    }

    public String receivedAlSpace() {
        return receivedReport.getValueOf(ReportCodeKey.THEORY_SPACE_COMPLEXITY);
    }

    public String receivedAlTime() {
        return receivedReport.getValueOf(ReportCodeKey.THEORY_TIME_COMPLEXITY);
    }

    public String receivedTestUsedSteps() {
        return receivedReport.getValueOf(ReportCodeKey.STEPS_USED);
    }

    public String receivedTestMaxSteps() {
        return receivedReport.getValueOf(ReportCodeKey.MAX_STEPS);
    }

    public String receivedTestPathWeight() {
        return receivedReport.getValueOf(ReportCodeKey.PATH_WEIGHT);
    }

    public String receivedProcessedMap() {
        return receivedReport.getValueOf(ReportCodeKey.PROCESSED_MAP);
    }

    public boolean isValidatingReturnedTrue() {
        return validatingReturnedTrue;
    }

    public ReportMeta getReceivedReportMeta() { return receivedReportMeta; }

    public String getReceivedPath() {
        return receivedPath;
    }
}
