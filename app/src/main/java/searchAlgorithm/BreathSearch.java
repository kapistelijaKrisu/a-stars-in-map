package searchAlgorithm;

import IOoperations.analysisWriter.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic breath-first-search
 */
public class BreathSearch extends SearchAlgorithm {

    /**
     * classic breath-first-search that extends SearchAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     */
    public BreathSearch(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    /**
     * Runs breath search by adding neighbours of start to que and comparing them to target node in order.
     * @param timeOfStart       time in nanos of when method is called.
     * @param availableSpace    space left in jvm heap when method is called.
     * @param path              place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> path) {
        WeightedPoint originalStart = map.getTileStart();
        Set<WeightedPoint> visited = new HashSet();
        ArrayDeque<WeightedPoint> queue = new ArrayDeque<>();

        visited.add(originalStart);
        queue.add(originalStart);
        path.put(originalStart, null);

        while (!queue.isEmpty()) {
            WeightedPoint polled = queue.poll();
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
        return "breath width";
    }
}
