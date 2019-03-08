package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import file_operations.common.DocumentPath;
import model.report.Report;
import model.report.ReportCodeKey;
import model.report.ReportMeta;
import model.web.WebMap;
import model.web.WeightedPoint;
import system_tools.LegalFileName;
import system_tools.SystemSpecReader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for an algorithm that does all the ground work for preparing and writing analysis file.
 */
public abstract class AnalysableAlgorithm {
    protected WebMap map;
    private Report report;
    private ReportMeta reportMeta;
    private String reportFilePath;
    private final AnalysisWriter analysisWriter;
    private final SystemSpecReader systemSpecReader;
    private final String name;

    /**
     * sets given analysis writer instantiates itself
     *
     * @param analysisWriter writer to write analyses on a file
     * @param name           name of the algorithm
     */
    public AnalysableAlgorithm(AnalysisWriter analysisWriter, String name) {
        if (analysisWriter == null) throw new IllegalArgumentException("analysis writer cannot be null");
        if (!LegalFileName.isValidFileName(name))
            throw new IllegalArgumentException("name needs to be a valid filename");
        this.analysisWriter = analysisWriter;
        report = new Report();
        reportMeta = new ReportMeta();
        systemSpecReader = new SystemSpecReader();
        this.name = name;
    }

    /**
     * Implementation of the search algorithm
     * During this call handleReportWriting needs to be called with given parameters to get rest of analysis done.
     *
     * @param timeOfStart    time in nanos of when method is called.
     * @param availableSpace space left in jvm heap when method is called.
     * @param fromToNodeSet  place to store which step is taken form where.
     */
    protected abstract void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet);

    /**
     * Runs implemented algorithm and writes report based on it
     *
     * @throws IllegalStateException when algorithm or map does not have name
     * @throws IOException           failing to write on designated report file
     */
    public void runSearch() throws IllegalStateException, IOException, IllegalArgumentException {
        if (reportFilePath == null) {
            if (map == null || !map.isValid())
                throw new IllegalStateException("Requires valid map, and name for algorithm");
            reportFilePath = DocumentPath.REPORTS.getFilePath() + map.getName() + "/" + name;
        }
        report = new Report();
        Map<WeightedPoint, WeightedPoint> path = new HashMap<>();
        System.out.println("Starting search of " + name + " - " + getShortImpl() + "...");
        searchAlgorithm(System.nanoTime(), systemSpecReader.getAvailableHeapSize(), path);
        fillDefaultTemplateValues(report);
        System.out.println("Analyze completed! Beginning to write report...");
        analysisWriter.writeReport(report, reportMeta, reportFilePath);
        System.out.println("Finished!");
        printConclusion(report);

    }

    private void printConclusion(Report report) {
        System.out.println("Space used: " + report.getValueOf(ReportCodeKey.SPACE_USED));
        System.out.println("Steps taken: " + report.getValueOf(ReportCodeKey.STEPS_USED) + "/" + report.getValueOf(ReportCodeKey.MAX_STEPS));
        System.out.println("Time: " + report.getValueOf(ReportCodeKey.TIME_USED));
        System.out.println("Path weight: " + report.getValueOf(ReportCodeKey.PATH_WEIGHT));
        System.out.println();
    }

    private void fillDefaultTemplateValues(Report report) {
        report.setValueOf(ReportCodeKey.ALGORITHM_NAME, name);
        report.setValueOf(ReportCodeKey.CPU, systemSpecReader.getCpu());
        report.setValueOf(ReportCodeKey.OS, systemSpecReader.getOperatingSystem());
        report.setValueOf(ReportCodeKey.COMPILER, systemSpecReader.getCompiler());
        report.setValueOf(ReportCodeKey.RUNTIME, systemSpecReader.getRuntime());
        report.setValueOf(ReportCodeKey.VM_NAME, systemSpecReader.getVirtualMachineName());
        report.setValueOf(ReportCodeKey.VM_VERSION, systemSpecReader.getVirtualMachineVersion());
        report.setValueOf(ReportCodeKey.ENV_HEAP, systemSpecReader.getAvailableHeapSizeReadable());
        report.setValueOf(ReportCodeKey.MAP_INFO, map.getTextualView());

        report.setValueOf(ReportCodeKey.THEORY_TIME_COMPLEXITY, getTheoreticalTime());
        report.setValueOf(ReportCodeKey.THEORY_SPACE_COMPLEXITY, getTheoreticalSpace());
        report.setValueOf(ReportCodeKey.IMPLEMENTATION_INFO, getDescription());

        reportMeta.setAlgorithmName(name);
        reportMeta.setAlgorithmImplementationType(getShortImpl());

    }

    /**
     * When search is completed insert the resulting path and passed parameters in searchAlgorithm to fill the rest of analyze values
     * Calculates time and space used. Calculates path weight and writes a visual representation of algorithms flow.
     *
     * @param path             all steps from point a to point b that accumulated during searchAlgorithm call
     * @param startTime        time that was given when calling searchAlgorithm
     * @param spaceLeftAtStart space left in jvm that was given when calling searchAlgorithm
     */
    protected void handleReportWriting(Map<WeightedPoint, WeightedPoint> path, long startTime, long spaceLeftAtStart) {
        setTimeElapsed(startTime); //important to do instantly
        long spaceDifference = systemSpecReader.getAvailableHeapSize() - spaceLeftAtStart; // important to do before app's memory changes
        String prettySpaceDifference = (spaceDifference / 1024) + "kb " + spaceDifference % 1024 + "b";

        System.out.println("Search completed! Beginning to analyze search results...");
        report.setValueOf(ReportCodeKey.SPACE_USED, prettySpaceDifference);
        reportMeta.setTestSpace((double) spaceDifference);
        analyzeSearch(path);

    }

    private void analyzeSearch(Map<WeightedPoint, WeightedPoint> path) {
        List<Point> goalPath = new ArrayList<>();
        Point locationAt = map.getTileTarget();

        while (locationAt != null) {
            goalPath.add(locationAt);
            locationAt = path.getOrDefault(locationAt, null);
        }
        double totalPathWeight = 0;
        long max_steps = 0;
        // include space between tiles and line separator
        int bufferAmountNeeded = map.width() * map.height() * 2 + map.height() * (System.lineSeparator().length());
        StringBuilder sb = new StringBuilder(bufferAmountNeeded);

        // going through each coordinate we compare it to given path and mark how coordinates were processed
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                WeightedPoint coordinate = new WeightedPoint(x, y, map.getLocationWeight(x, y));

                if (map.getTileStart().x == x && map.getTileStart().y == y && map.getTileStart().equals(map.getTileTarget())) {
                    sb.append(NodeHandlingType.IS_START_AND_TARGET.getCharValue());
                    max_steps--;

                } else if (map.getTileStart().x == x && map.getTileStart().y == y) {
                    sb.append(NodeHandlingType.START_LOCATION.getCharValue());
                    max_steps--;

                } else if (map.getTileTarget().x == x && map.getTileTarget().y == y) {
                    if (path.containsKey(coordinate)) {
                        sb.append(NodeHandlingType.TARGET_LOCATION_AND_FOUND.getCharValue());
                        totalPathWeight += map.getLocationWeight(coordinate.x, coordinate.y);
                    } else {
                        sb.append(NodeHandlingType.TARGET_LOCATION_AND_NOT_FOUND.getCharValue());
                    }

                } else if (coordinate.weight == 0) {
                    sb.append(NodeHandlingType.WALL.getCharValue());
                    max_steps--;

                } else if (goalPath.contains(coordinate)) {
                    sb.append(NodeHandlingType.PROCESSED_IN_PATH.getCharValue());
                    totalPathWeight += map.getLocationWeight(coordinate.x, coordinate.y);

                } else if (path.containsKey(coordinate)) {
                    sb.append(NodeHandlingType.PROCESSED_NOT_IN_PATH.getCharValue());

                } else {
                    sb.append(NodeHandlingType.NOT_PROCESSED.getCharValue());
                }
                sb.append(' ');
                max_steps++;
            }
            sb.append(System.lineSeparator());
        }

        sb.setLength(Math.max(sb.length() - (1 + System.lineSeparator().length()), 0));
        String platformDependantResultMap = sb.toString();
        platformDependantResultMap = platformDependantResultMap.replaceAll("\\n|\\r\\n", System.lineSeparator());
        //fill analysis values

        String pathWeight = path.get(map.getTileTarget()) == null ? "Target was not found" : "" + (int) totalPathWeight;
        report.setValueOf(ReportCodeKey.PROCESSED_MAP, platformDependantResultMap);
        report.setValueOf(ReportCodeKey.PATH_WEIGHT, "" + pathWeight);
        report.setValueOf(ReportCodeKey.MAX_STEPS, "" + max_steps);
        report.setValueOf(ReportCodeKey.STEPS_USED, "" + (path.size() - 1)); //start node doesn't count

        reportMeta.setTestMaxSteps(max_steps);
        reportMeta.setTestPathWeight(totalPathWeight);
        reportMeta.setTestUsedSteps((double) path.size() - 1);
    }


    private void setTimeElapsed(long startTime) {
        long elapsedTime = System.nanoTime() - startTime;
        long nanos = elapsedTime % 1000000;
        long milliseconds = (elapsedTime / 1000000) % 1000000;
        long seconds = (elapsedTime / 1000000000);
        String timeReport = "";
        if (seconds > 0) timeReport += seconds + "sec ";
        if (milliseconds > 0) timeReport += milliseconds + "ms ";
        timeReport += nanos + "ns";
        report.setValueOf(ReportCodeKey.TIME_USED, timeReport);
        reportMeta.setTestTime((double) elapsedTime);
    }

    /**
     * Used to fill {al_time} of analysis report
     *
     * @return theoretical time
     */
    public abstract String getTheoreticalTime();

    /**
     * Used to fill {al_space} of analysis report
     *
     * @return theoretical space
     */
    public abstract String getTheoreticalSpace();

    /**
     * Used to fill {al_doc} of analysis report
     *
     * @return additional documentation of implementation visible in report
     */
    public abstract String getDescription();

    /**
     * Used to fill metaData column for description. Basically description but shorter.
     *
     * @return additional documentation of implementation as metadata for sorting reports by category of implementation
     */
    public abstract String getShortImpl();

    /**
     * @return Name of algorithm
     */
    public String getName() {
        return name;
    }

    /**
     * Directory is based on map name as well and gets cleaned when a map is set
     * for upkeepin reporting structure.
     * Other than that it's a normal setter.
     *
     * @param map map to be set
     */
    public void setMapClean(WebMap map) {
        this.map = map;
        this.reportFilePath = null;
    }

    // for testing

    /**
     * for testing
     *
     * @return set map for testing
     */
    public WebMap getMap() {
        return map;
    }

    /**
     * for testing
     *
     * @return path for testing
     */
    public String getReportFilePath() {
        return reportFilePath;
    }

    /**
     * for testing
     *
     * @param reportFilePath setter
     */
    public void setReportFilePath(String reportFilePath) {
        this.reportFilePath = reportFilePath;
    }
}
