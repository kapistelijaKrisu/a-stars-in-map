package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic Dijkstra with minheap and int[][] array to hold current known distances.
 */
public class Dijkstra extends AnalysableAlgorithm {

    /**
     * classic Dijkstra that extends AnalysableAlgorithm so it handles report writing.
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
     * @param fromToNodeSet              place to store which step is taken form where.
     */
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {

        PriorityQueue<WeightedPoint> visited = new PriorityQueue<>();
        int[][] distances = new int[map.height()][map.width()];
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                distances[y][x] = Integer.MAX_VALUE;
            }
        }
        distances[map.getTileStart().y][map.getTileStart().x] = 0;
        visited.add(new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0));

        fromToNodeSet.put(map.getTileStart(), null);

        while (!visited.isEmpty()) {
            WeightedPoint polled = visited.poll();
            for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                int totalWeight = getNewWeight(neighbour.weight, distances[polled.y][polled.x]);
                int currentKnownWeight = distances[neighbour.y][neighbour.x];
                if (currentKnownWeight > totalWeight) {
                    visited.add(new WeightedPoint(neighbour.x, neighbour.y, totalWeight));
                    distances[neighbour.y][neighbour.x] = totalWeight;
                    fromToNodeSet.put(neighbour, polled);
                }
                if (neighbour.equals(map.getTileTarget())) {
                    super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
                    return;
                }
            }
        }
        super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
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
