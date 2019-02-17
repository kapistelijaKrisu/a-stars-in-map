package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic breath-first-search
 */
public class BreathSearch extends AnalysableAlgorithm {

    /**
     * classic breath-first-search that extends AnalysableAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     */
    public BreathSearch(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    /**
     * Runs breath search by adding neighbours of start to que and comparing them to target node in order.
     * @param timeOfStart       time in nanos of when method is called.
     * @param availableSpace    space left in jvm heap when method is called.
     * @param fromToNodeSet              place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
        WeightedPoint originalStart = map.getTileStart();
        Set<WeightedPoint> visited = new HashSet();
        ArrayDeque<WeightedPoint> queue = new ArrayDeque<>();

        visited.add(originalStart);
        queue.add(originalStart);
        fromToNodeSet.put(originalStart, null);

        while (!queue.isEmpty()) {
            WeightedPoint polled = queue.poll();
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
                return;
            } else {
                for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                    if (!visited.contains(neighbour)) {
                        fromToNodeSet.put(neighbour, polled);
                        visited.add(neighbour);
                        queue.add(neighbour);
                    }
                }
            }
        }
        super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
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
        return "breath width";
    }
}
