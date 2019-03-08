package file_operations.analysis_overview;

import model.report.ReportMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Creates statistics on reports that are divided by map and saves results divided by map directories.
 */
public class AnalysisInspector {

    private static final AnalysisOverviewWriter ANALYSIS_OVERVIEW_WRITER = new AnalysisOverviewWriter();

    /**
     * For each map processes reports and writes them to map folder.
     * Processing: Calculate fastest, cheapest and sorts reports by smallest path weight and then by time.
     * Writing is done by AnalysisOverviewWriter
     *
     * @param reports set of reports categorized by map folders
     */
    public void createAnalysisStatisticByMap(Map<File, Set<ReportMeta>> reports) {
        if (reports.isEmpty() || reports.values().isEmpty()) {
            System.out.println("No reports found. Aborting file writing.");
            return;
        }
        for (Map.Entry<File, Set<ReportMeta>> pair : reports.entrySet()) {
            analyseByMap(pair.getKey(), pair.getValue());
        }

    }

    private void analyseByMap(File mapFolder, Set<ReportMeta> reportMetas) {
        if (reportMetas.isEmpty() || !reportMetas.stream().allMatch(ReportMeta::isValid)) return;

        List<ReportMeta> sortedByReportNeeds = reportMetas.stream().sorted((a, b) -> {
            if (a.getTestPathWeight().equals(b.getTestPathWeight())) {
                return (int) (a.getTestTime() - b.getTestTime());
            } else {
                return (int) (a.getTestPathWeight() - b.getTestPathWeight());
            }
        }).collect(Collectors.toList());

        var fastest = reportMetas.stream().min((a, b) -> (int) (a.getTestTime() - b.getTestTime())).orElseThrow();
        var cheapest = reportMetas.stream().min((a, b) -> (int) (a.getTestSpace() - b.getTestSpace())).orElseThrow();

        var map = mapFolder.getName();

        try {
            ANALYSIS_OVERVIEW_WRITER.writeOverview(map, sortedByReportNeeds, fastest, cheapest);
        } catch (IOException e) {
            System.out.println("Failed to save statistics on file");
        }

    }
}
