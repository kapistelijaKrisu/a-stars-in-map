package searchAlgorithm;

import IOoperations.analysisWriter.AnalysisWriter;

import java.awt.*;
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
    protected void searchAlgorithm(long timeOfStart, long spaceAtStart) {
        Point orignalStart = map.getTileStart();

        Map<Point, Point> path = new HashMap();
        Set<Point> visited = new HashSet();
        ArrayDeque<Point> queue = new ArrayDeque<>();

        visited.add(orignalStart);
        queue.add(orignalStart);
        path.put(orignalStart, null);

        while (!queue.isEmpty()) {
            Point polled = queue.poll();
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(path, timeOfStart, spaceAtStart);
                return;
            } else {
                for (Point neighbour : map.getNeighbours(polled)) {
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
