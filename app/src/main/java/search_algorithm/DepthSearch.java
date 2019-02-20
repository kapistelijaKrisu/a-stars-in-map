package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic dreath-first-search
 */
public class DepthSearch extends AnalysableAlgorithm {

    /**
     * classic depth-first-search that extends AnalysableAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     */
    public DepthSearch(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    /**
     * Runs depth search by using stack and adding neighbours to it and then comparing popped neightbour wit target.
     * @param timeOfStart       time in nanos of when method is called.
     * @param availableSpace    space left in jvm heap when method is called.
     * @param fromToNodeSet              place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
        WeightedPoint orignalStart = new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0);

        Set<WeightedPoint> visited = new HashSet();
        ArrayDeque<WeightedPoint> queue = new ArrayDeque<>();

        visited.add(orignalStart);
        queue.add(orignalStart);
        fromToNodeSet.put(orignalStart, null);

        while (!queue.isEmpty()) {
            WeightedPoint polled = queue.pollLast();
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
                return;
            } else {
                for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                    if (!visited.contains(neighbour)) {
                        var neighBourWithPathWeight = new WeightedPoint(neighbour.x, neighbour.y, neighbour.weight + polled.weight);
                        fromToNodeSet.put(neighBourWithPathWeight, polled);
                        visited.add(neighBourWithPathWeight);
                        queue.add(neighBourWithPathWeight);
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
        return "depth search";
    }
}
