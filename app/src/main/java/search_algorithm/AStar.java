package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic A*
 */
public class AStar extends SearchAlgorithm {

    /**
     * classic A* that extends SearchAlgorithm so it handles report writing.
     *
     * @param analysisWriter writer that writes the analysis report files.
     */
    public AStar(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    /**
     * Runs A* with a heap and uses double[][] for upkeeping current know distances.
     * Heuristic is WeightedPoint's rough estimate result from calculateDistance method.
     *  @param timeOfStart       time in nanos of when method is called.
     *  @param availableSpace    space left in jvm heap when method is called.
     *  @param path              place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> path) {

        PriorityQueue<WeightedPoint> visited = new PriorityQueue<>();
        double[][] distancesKnownFromStart = new double[map.height()][map.width()];
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                distancesKnownFromStart[y][x] = Integer.MAX_VALUE;
            }
        }
        distancesKnownFromStart[map.getTileStart().y][map.getTileStart().x] = 0;
        visited.add(new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0));

        path.put(map.getTileStart(), null);

        while (!visited.isEmpty()) {
            WeightedPoint polled = visited.poll();
            for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                if (!visited.contains(neighbour) ) {
                    double predictedDistance = neighbour.calculateDistance(map.getTileTarget());
                    double totalDistance = neighbour.weight + distancesKnownFromStart[polled.y][polled.x] + predictedDistance * neighbour.weight;

                    double currentKnownWeight = distancesKnownFromStart[neighbour.y][neighbour.x];
                    if (currentKnownWeight > totalDistance) {
                        visited.add(new WeightedPoint(neighbour.x, neighbour.y, totalDistance));
                        distancesKnownFromStart[neighbour.y][neighbour.x] = neighbour.weight + distancesKnownFromStart[polled.y][polled.x];
                        path.put(neighbour, polled);
                    }
                    if (neighbour.equals(map.getTileTarget())) {
                        super.handleReportWriting(path, timeOfStart, availableSpace);
                        return;
                    }
                }
            }
        }
        super.handleReportWriting(path, timeOfStart, availableSpace);
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
        return "A Star";
    }
}
