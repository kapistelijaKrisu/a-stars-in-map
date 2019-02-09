package searchAlgorithm;

import IOoperations.analysisWriter.AnalysisWriter;

import java.awt.*;
import java.util.*;

/**
 * classic dreath-first-search
 */
public class DepthSearch extends SearchAlgorithm {

    /**
     * classic dreath-first-search that extends SearchAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     */
    public DepthSearch(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    @Override
    protected void searchAlgorithm(long timeOfStart, long spaceLeft) {
        Point orignalStart = map.getTileAt();

        Map<Point, Point> path = new HashMap();
        Set<Point> visited = new HashSet();
        ArrayDeque<Point> queue = new ArrayDeque<>();

        visited.add(orignalStart);
        queue.add(orignalStart);

        Point previous = null;

        while (!queue.isEmpty()) {
            Point polled = queue.pollLast();
            path.put(polled, previous);
            previous = polled;
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(path, visited, timeOfStart, spaceLeft);
                return;
            } else {
                for (Point neighbour : map.getNeighbours(polled)) {
                    if (!visited.contains(neighbour)) {
                        visited.add(neighbour);
                        queue.add(neighbour);
                    }
                }
            }
        }
    }

    @Override
    public String getTheoreticalTime() {
        return "O( | V + E| )";
    }

    @Override
    public String getTheoreticalSpace() {
        return "| V |";
    }

    @Override
    public String getAdditionalDocumentation() {
        return "";
    }

    @Override
    public String toString() {
        return "depth search";
    }
}
