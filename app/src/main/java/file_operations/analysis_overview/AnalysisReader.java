package file_operations.analysis_overview;

import file_operations.common.ReportMetaFileKey;
import model.report.ReportMeta;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Reader to load relevant data of report as ReportMeta object.
 */
public class AnalysisReader {
    private static final int META_DATA_AT_LINE = 0;

    /**
     * Reads from very 1st line all data that is needed to form valid ReportMeta.
     *
     * @param analysisFile report file.
     * @return ReportMeta that is extracted from file if it is valid. else null
     * @throws IOException if opening or reading metadata fails.
     */
    public ReportMeta loadAnalysisFromFile(File analysisFile) throws IOException {
        String fullReport = Files.readAllLines(Paths.get(analysisFile.getPath())).get(META_DATA_AT_LINE);
        var report = new ReportMeta();

        for (ReportMetaFileKey reportMetaFileKey : ReportMetaFileKey.values()) {
            String metadataWrapper = reportMetaFileKey.getStringValue();
            int first = fullReport.indexOf(metadataWrapper) + metadataWrapper.length();
            //offset beginning so it will catch the 2nd aka ending tag
            int second = fullReport.indexOf(metadataWrapper, first + 1);

            //indexOf return -1 if didn't find
            if (first == -1 || second == -1) continue;
            String reportValue = fullReport.substring(first, second);
            setMetaValue(report, reportMetaFileKey, reportValue);
        }
        if (!report.isValid()) throw new IOException("ReportMeta was not loaded correctly");
        return report;
    }

    private void setMetaValue(ReportMeta report, ReportMetaFileKey reportMetaFileKey, String reportValue) {
        if (reportMetaFileKey == null) return;
        switch (reportMetaFileKey) {
            case ALGORITHM_NAME:
                report.setAlgorithmName(reportValue);
                break;
            case ALGORITHM_IMPL:
                report.setAlgorithmImplementationType(reportValue);
                break;
            case TEST_PATH_WEIGHT:
                report.setTestPathWeight(Double.parseDouble(reportValue));
                break;
            case TEST_SPACE_USED:
                report.setTestSpace(Double.parseDouble(reportValue));
                break;
            case TEST_STEPS_USED:
                report.setTestUsedSteps(Double.parseDouble(reportValue));
                break;
            case TEST_TIME_USED:
                report.setTestTime(Double.parseDouble(reportValue));
                break;
            case MAP_MAX_STEPS:
                report.setTestMaxSteps(Long.parseLong(reportValue));
                break;
        }
    }
}
