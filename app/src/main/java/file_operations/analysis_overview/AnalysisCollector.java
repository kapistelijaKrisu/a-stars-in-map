package file_operations.analysis_overview;

import file_operations.common.DocumentPath;
import file_operations.common.FileClassification;
import file_operations.root_file_operations.RootDirectoryLister;
import file_operations.root_file_operations.RootFileLister;
import model.report.ReportMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Collector to collect all metadata aka ReportMeta from existing reports in /maps folder
 */
public class AnalysisCollector {

    private RootDirectoryLister rootDirectoryLister;
    private RootFileLister rootFileLister;
    private AnalysisReader analysisReader;

    /**
     * Collector to collect all metadata aka ReportMeta from existing reports in /maps folder
     */
    public AnalysisCollector() {
        rootDirectoryLister = new RootDirectoryLister();
        rootFileLister = new RootFileLister();
        analysisReader = new AnalysisReader();
    }

    /**
     * Collects all metadata separated by map.
     * All duplicate algorithms by name and implementation are flattened and made an average of.
     * Root folder of maps is /maps
     *
     * @return flattened reports grouped by map
     */
    public Map<File, Set<ReportMeta>> collectFlattenedReports() {
        List<File> mapReportFolders = Arrays.stream(rootDirectoryLister.listFiles(DocumentPath.REPORTS.getFilePath()))
                .collect(Collectors.toList());

        Map<File, Set<ReportMeta>> flattenedReportsPerMap = new TreeMap<>();
        for (File mapDirectory : mapReportFolders) {
            //make average of all algorithm implementation types
            try {
                List<File> allAnalysisReports = rootFileLister.listFiles(
                        DocumentPath.REPORTS.getFilePath() + mapDirectory.getName(), FileClassification.REPORT.getFileType());
                Map<String, List<ReportMeta>> categorizedReports = divideByNameAndImplementationType(allAnalysisReports);
                if (!categorizedReports.isEmpty())
                    flattenedReportsPerMap.put(mapDirectory, flattenReports(categorizedReports));
            } catch (IOException e) {
                System.out.println("Failed to load reports of map folder: " + mapDirectory.getName());
            }
        }
        return flattenedReportsPerMap;
    }

    private Set<ReportMeta> flattenReports(Map<String, List<ReportMeta>> reportsByImplementationType) {
        Comparator<ReportMeta> comp = Comparator.comparing((ReportMeta o) -> (o.getAlgorithmName() + o.getAlgorithmImplementationType()));
        Set<ReportMeta> writeReports = new TreeSet<>(comp);
        for (List<ReportMeta> writeReportByImpl : reportsByImplementationType.values()) {
            if (writeReportByImpl.isEmpty()) continue;
            ReportMeta avg = new ReportMeta();

            avg.setTestUsedSteps(writeReportByImpl.stream().mapToDouble(ReportMeta::getTestUsedSteps).average().orElseThrow());
            avg.setTestPathWeight(writeReportByImpl.stream().mapToDouble(ReportMeta::getTestPathWeight).average().orElseThrow());
            avg.setTestSpace(writeReportByImpl.stream().mapToDouble(ReportMeta::getTestSpace).average().orElseThrow());
            avg.setTestTime(writeReportByImpl.stream().mapToDouble(ReportMeta::getTestTime).average().orElseThrow());
            avg.setAlgorithmName(writeReportByImpl.get(0).getAlgorithmName());
            avg.setAlgorithmImplementationType(writeReportByImpl.get(0).getAlgorithmImplementationType());
            avg.setTestMaxSteps(writeReportByImpl.get(0).getTestMaxSteps());

            writeReports.add(avg);
        }
        return writeReports;
    }

    private Map<String, List<ReportMeta>> divideByNameAndImplementationType(List<File> analysisReports) {
        Map<String, List<ReportMeta>> reportsByAlgorithmType = new TreeMap<>();
        for (File analysis : analysisReports) {
            try {
                ReportMeta writeReport = analysisReader.loadAnalysisFromFile(analysis);
                var implementationType = writeReport.getAlgorithmName() + writeReport.getAlgorithmImplementationType();

                if (reportsByAlgorithmType.get(implementationType) == null) {
                    ArrayList<ReportMeta> writeReportList = new ArrayList<>();
                    reportsByAlgorithmType.put(implementationType, writeReportList);
                }
                reportsByAlgorithmType.get(implementationType).add(writeReport);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return reportsByAlgorithmType;
    }
}
