package searchAlgorithm;

import IOoperations.analysisWriter.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic Dijkstra with minheap and int[][] array to hold current known distances.
 */
public class Dijkstra extends SearchAlgorithm {

    /**
     * classic Dijkstra that extends SearchAlgorithm so it handles report writing.
     *
     * @param analysisWriter writer that writes the analysis report files.
     */
    public Dijkstra(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    /**
     * Runs Dijkstra with a heap and uses double[][] for upkeeping current know distances.
     * @param timeOfStart       time in nanos of when method is called.
     * @param availableSpace    space left in jvm heap when method is called.
     * @param path              place to store which step is taken form where.
     */
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> path) {

        PriorityQueue<WeightedPoint> visited = new PriorityQueue<>();
        int[][] distances = new int[map.height()][map.width()];
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                distances[y][x] = Integer.MAX_VALUE;
            }
        }
        distances[map.getTileStart().y][map.getTileStart().x] = 0;
        visited.add(new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0));

        path.put(map.getTileStart(), null);

        while (!visited.isEmpty()) {
            WeightedPoint polled = visited.poll();
            for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                int totalWeight = getNewWeight(neighbour.weight, distances[polled.y][polled.x]);
                int currentKnownWeight = distances[neighbour.y][neighbour.x];
                if (currentKnownWeight > totalWeight) {
                    visited.add(new WeightedPoint(neighbour.x, neighbour.y, totalWeight));
                    distances[neighbour.y][neighbour.x] = totalWeight;
                    path.put(neighbour, polled);
                }
                if (neighbour.equals(map.getTileTarget())) {
                    super.handleReportWriting(path, timeOfStart, availableSpace);
                    return;
                }
            }
        }
        super.handleReportWriting(path, timeOfStart, availableSpace);
    }

    private int getNewWeight(double dist, double weight) {
        Double safe = Math.min(dist + weight, Integer.MAX_VALUE);
        return safe.intValue();
    }

    /**
     *
     * @return known theoretical time complecity.
     */
    @Override
    public String getTheoreticalTime() {
        return "O( | V + E | log | V |)";
    }
    /**
     *
     * @return known theoretical space complecity.
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
        return "Dijkstra";
    }
}
