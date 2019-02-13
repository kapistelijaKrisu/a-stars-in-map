package searchAlgorithm;

import IOoperations.analysisWriter.AnalysisWriter;
import model.WeightedPoint;

import java.util.*;

/**
 * classic breath-first-search
 */
public class Dijkstra extends SearchAlgorithm {

    /**
     * classic breath-first-search that extends SearchAlgorithm so it handles report writing.
     *
     * @param analysisWriter writer that writes the analysis report files.
     */
    public Dijkstra(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    @Override
    protected void searchAlgorithm(long timeOfStart, long spaceAtStart, Map<WeightedPoint, WeightedPoint> path) {

        PriorityQueue<WeightedPoint> visited = new PriorityQueue<>();
        int[][] distances = new int[map.getMap().length][map.getMap()[0].length];
        for (int y = 0; y < map.getMap().length; y++) {
            for (int x = 0; x < map.getMap()[0].length; x++) {
                visited.add(new WeightedPoint(x, y, 99));
                distances[y][x] = 99;
            }
        }
        distances[map.getTileStart().y][map.getTileStart().x] = 0;
        visited.add(new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0));

        path.put(map.getTileStart(), null);

        while (!visited.isEmpty()) {
            WeightedPoint polled = visited.poll();
            for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                int newKey = getNewWeight(polled.weight, map.getMap()[neighbour.y][neighbour.x]);
                int currentKnownWeight = distances[neighbour.y][neighbour.x];
                if (currentKnownWeight > newKey) {
                    visited.remove(neighbour);
                    visited.add(new WeightedPoint(neighbour.x, neighbour.y, newKey));
                    distances[neighbour.y][neighbour.x] = newKey;
                    path.put(neighbour, polled);
                }
                if (neighbour.equals(map.getTileTarget())) {
                    super.handleReportWriting(path, timeOfStart, spaceAtStart);
                    return;
                }
            }
        }
        super.handleReportWriting(path, timeOfStart, spaceAtStart);
    }

    private int getNewWeight(int dist, int weight) {
        Long safe = Math.min((long) dist + weight, 99);
        return safe.intValue();
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
        return "Dijkstra";
    }
}
