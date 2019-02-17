package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic dreath-first-search
 */
public class DepthSearch extends SearchAlgorithm {

    /**
     * classic depth-first-search that extends SearchAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     */
    public DepthSearch(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    /**
     * Runs depth search by using stack and adding neighbours to it and then comparing popped neightbour wit target.
     * @param timeOfStart       time in nanos of when method is called.
     * @param availableSpace    space left in jvm heap when method is called.
     * @param path              place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> path) {
        WeightedPoint orignalStart = map.getTileStart();

        Set<WeightedPoint> visited = new HashSet();
        ArrayDeque<WeightedPoint> queue = new ArrayDeque<>();

        visited.add(orignalStart);
        queue.add(orignalStart);
        path.put(orignalStart, null);

        while (!queue.isEmpty()) {
            WeightedPoint polled = queue.pollLast();
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(path, timeOfStart, availableSpace);
                return;
            } else {
                for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                    if (!visited.contains(neighbour)) {
                        path.put(neighbour, polled);
                        visited.add(neighbour);
                        queue.add(neighbour);
                    }
                }
            }
        }
        super.handleReportWriting(path, timeOfStart, availableSpace);
    }

    /**
     *
     * @return known time complexity
     */
    @Override
    public String getTheoreticalTime() {
        return "O( | V + E | )";
    }

    /**
     *
     * @return known space complexity
     */
    @Override
    public String getTheoreticalSpace() {
        return "| V |";
    }

    @Override
    public String getAdditionalDocumentation() {
        return "TBD";
    }

    @Override
    public String toString() {
        return "depth search";
    }
}
