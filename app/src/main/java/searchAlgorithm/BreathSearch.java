package searchAlgorithm;

import IOoperations.analysisWriter.AnalysisWriter;
import model.WeightedPoint;

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

    @Override
    protected void searchAlgorithm(long timeOfStart, long spaceAtStart, Map<WeightedPoint, WeightedPoint> path) {
        WeightedPoint orignalStart = map.getTileStart();
        Set<WeightedPoint> visited = new HashSet();
        ArrayDeque<WeightedPoint> queue = new ArrayDeque<>();

        visited.add(orignalStart);
        queue.add(orignalStart);
        path.put(orignalStart, null);

        while (!queue.isEmpty()) {
            WeightedPoint polled = queue.poll();
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(path, timeOfStart, spaceAtStart);
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
        super.handleReportWriting(path, timeOfStart, spaceAtStart);
    }

    @Override
    public String getTheoreticalTime() {
        return "O( | V + E | )";
    }

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
