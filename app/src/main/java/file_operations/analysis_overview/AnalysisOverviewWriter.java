package file_operations.analysis_overview;

import file_operations.common.*;
import file_operations.root_file_operations.RootFileWriter;
import model.report.ReportMeta;
import system_tools.NanoSecondPrettified;

import java.io.IOException;
import java.util.Collection;

/**
 * Statistic file writer using folloring templates overview-template.md and overview-algorithm-row.md to write statistic overviews.
 */
public class AnalysisOverviewWriter {

    /**
     * Writes statistics of overview on a file. It does simple template value replacing so cheapest and fastest are on user's responsibility that they indeed are cheapest and fastest.
     *
     * @param mapName     map name that is added on template. Also used as a part of resulting file name.
     * @param reportMetas metadata that is added on template
     * @param fastest     fastest metadata that is added on template
     * @param cheapest    cheapest metadata that is added on template
     * @throws IOException if operation fails. Possible reasons: null params or non-existing params on file-system or general IOException.
     */
    public void writeOverview(String mapName, Collection<ReportMeta> reportMetas, ReportMeta fastest, ReportMeta cheapest) throws IOException {
        String overViewTemplate = ResourceFileReader.readResourceFile(Template.OVERVIEW_TEMPLATE.getFileName());

        overViewTemplate = overViewTemplate.replace(StatisticTemplateKey.MAP_NAME.getStringValue(), mapName);
        overViewTemplate = overViewTemplate.replace(StatisticTemplateKey.FASTEST_TIME_ALGORITHM.getStringValue(), createAnalysisRow(fastest));
        overViewTemplate = overViewTemplate.replace(StatisticTemplateKey.CHEAPEST_IN_SPACE_ALGORITHM.getStringValue(), createAnalysisRow(cheapest));

        StringBuilder analysisRowsText = new StringBuilder();
        for (ReportMeta writeReport : reportMetas) {
            analysisRowsText.append(createAnalysisRow(writeReport)).append(System.lineSeparator());
        }
        overViewTemplate = overViewTemplate.replace(StatisticTemplateKey.TABLE_OF_ALGORITHM_PERFORMANCE.getStringValue(), analysisRowsText.toString());


        RootFileWriter.writeMdFileWithTime(overViewTemplate, DocumentPath.REPORTS.getFilePath() + mapName, mapName, FileClassification.STATISTICS.getFileType());
    }

    private String createAnalysisRow(ReportMeta reportMeta) {
        String analysisRow = ResourceFileReader.readResourceFile(Template.ANALYSIS_AS_A_TABLE_ROW.getFileName());
        analysisRow = analysisRow.replace(ReportMetaFileKey.ALGORITHM_NAME.getStringValue(), reportMeta.getAlgorithmName());
        analysisRow = analysisRow.replace(ReportMetaFileKey.ALGORITHM_IMPL.getStringValue(), reportMeta.getAlgorithmImplementationType());
        analysisRow = analysisRow.replace(ReportMetaFileKey.MAP_MAX_STEPS.getStringValue(), reportMeta.getTestMaxSteps() + "");
        analysisRow = analysisRow.replace(ReportMetaFileKey.TEST_PATH_WEIGHT.getStringValue(), reportMeta.getTestPathWeight() + "");
        analysisRow = analysisRow.replace(ReportMetaFileKey.TEST_SPACE_USED.getStringValue(), reportMeta.getTestSpace() + "");
        analysisRow = analysisRow.replace(ReportMetaFileKey.TEST_STEPS_USED.getStringValue(), reportMeta.getTestUsedSteps() + "");
        String timeReport = NanoSecondPrettified.prettifyNanoSeconds(reportMeta.getTestTime().longValue());
        analysisRow = analysisRow.replace(ReportMetaFileKey.TEST_TIME_USED.getStringValue(), timeReport + "");
        return analysisRow;
    }
}
