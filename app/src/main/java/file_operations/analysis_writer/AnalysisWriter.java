package file_operations.analysis_writer;

import file_operations.common.FileClassification;
import file_operations.common.ReportMetaFileKey;
import file_operations.common.ResourceFileReader;
import file_operations.common.Template;
import file_operations.root_file_operations.RootDirectoryBuilder;
import file_operations.root_file_operations.RootFileWriter;
import model.report.Report;
import model.report.ReportMeta;
import system_tools.LegalFileName;

import java.io.IOException;
import java.util.Map;

/**
 * A file writer that uses report-template.md to write an analysis at root level.
 */
public class AnalysisWriter {

    /**
     * Reads report-template.md and replaces keys according to replacingValues. Then writes it to a timestamp by DateConverter .md file at given path directory.
     *
     * @param report     Template keys to be replaced with these values
     * @param reportMeta Metadata that is stored in specific format so it can be read by the app again and be analyzed.
     * @param path       in what directory structure will the report be having app level as root. Will create folders if  missing.
     * @throws IOException              when writing doesn't succeed
     * @throws IllegalArgumentException if path is null or replacingValues is not valid according to Report itself.
     */
    public void writeReport(Report report, ReportMeta reportMeta, String path) throws IOException, IllegalArgumentException {
        if (!report.isValid() || !reportMeta.isValid() || LegalFileName.areValidFileNames(path.split("/")))
            throw new IllegalArgumentException("report, reportMeta and path need to be valid");

        var replacingValues = report.valuesToMap();
        String template = ResourceFileReader.readResourceFile(Template.ANALYSIS.getFileName());

        StringBuilder metaDataText = new StringBuilder();
        metaDataText.append("<!--This is metadata touching this might affect reading the file by this app. ");
        appendMeta(metaDataText, reportMeta);
        metaDataText.append("-->").append(System.lineSeparator());

        for (Map.Entry<String, String> pair : replacingValues.entrySet()) {
            template = template.replace(pair.getKey(), pair.getValue());
        }
        RootDirectoryBuilder.buildDirectories(path);
        RootFileWriter.writeMdFileWithTime(metaDataText.toString() + template, path, reportMeta.getAlgorithmName(), FileClassification.REPORT.getFileType());

    }


    private void appendMeta(StringBuilder sb, ReportMeta reportMeta) {
        sb.append(ReportMetaFileKey.ALGORITHM_NAME.getStringValue())
                .append(reportMeta.getAlgorithmName())
                .append(ReportMetaFileKey.ALGORITHM_NAME.getStringValue());

        sb.append(ReportMetaFileKey.ALGORITHM_IMPL.getStringValue())
                .append(reportMeta.getAlgorithmImplementationType())
                .append(ReportMetaFileKey.ALGORITHM_IMPL.getStringValue());

        sb.append(ReportMetaFileKey.MAP_MAX_STEPS.getStringValue())
                .append(reportMeta.getTestMaxSteps())
                .append(ReportMetaFileKey.MAP_MAX_STEPS.getStringValue());

        sb.append(ReportMetaFileKey.TEST_PATH_WEIGHT.getStringValue())
                .append(reportMeta.getTestPathWeight())
                .append(ReportMetaFileKey.TEST_PATH_WEIGHT.getStringValue());

        sb.append(ReportMetaFileKey.TEST_SPACE_USED.getStringValue())
                .append(reportMeta.getTestSpace())
                .append(ReportMetaFileKey.TEST_SPACE_USED.getStringValue());

        sb.append(ReportMetaFileKey.TEST_STEPS_USED.getStringValue())
                .append(reportMeta.getTestUsedSteps())
                .append(ReportMetaFileKey.TEST_STEPS_USED.getStringValue());

        sb.append(ReportMetaFileKey.TEST_TIME_USED.getStringValue())
                .append(reportMeta.getTestTime())
                .append(ReportMetaFileKey.TEST_TIME_USED.getStringValue());

    }
}
