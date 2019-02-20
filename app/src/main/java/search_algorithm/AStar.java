package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.web.WeightedPoint;

import java.util.*;

/**
 * classic A*
 */
public class AStar extends AnalysableAlgorithm {

    /**
     * classic A* that extends AnalysableAlgorithm so it handles report writing.
     *
     * @param analysisWriter writer that writes the analysis report files.
     */
    public AStar(AnalysisWriter analysisWriter) {
        super(analysisWriter);
    }

    /**
     * Runs A* with a heap and uses double[][] for upkeeping current know distances.
     * Heuristic is WeightedPoint's rough estimate result from calculateDistance method.
     *
     * @param timeOfStart    time in nanos of when method is called.
     * @param availableSpace space left in jvm heap when method is called.
     * @param fromToNodeSet  place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
        PriorityQueue<WeightedPoint> visited = new PriorityQueue<>();
        double[][] distancesKnownFromStart = new double[map.height()][map.width()];
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                distancesKnownFromStart[y][x] = Integer.MAX_VALUE;
            }
        }
        distancesKnownFromStart[map.getTileStart().y][map.getTileStart().x] = 0;

        var startNodeWeight = new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0);
        visited.add(startNodeWeight);
        fromToNodeSet.put(startNodeWeight, null);

        while (!visited.isEmpty()) {
            WeightedPoint polled = visited.poll();

            for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                double predictedDistance = neighbour.calculateDistance(map.getTileTarget()) + neighbour.weight;
                double newTotalDistance = distancesKnownFromStart[polled.y][polled.x] + predictedDistance;

                double currentKnownWeight = distancesKnownFromStart[neighbour.y][neighbour.x];
                if (currentKnownWeight > newTotalDistance) {

                    visited.add(new WeightedPoint(neighbour.x, neighbour.y, predictedDistance));
                    distancesKnownFromStart[neighbour.y][neighbour.x] = neighbour.weight + distancesKnownFromStart[polled.y][polled.x];
                    var polledPathWeight = new WeightedPoint(polled.x, polled.y, distancesKnownFromStart[polled.y][polled.x]);
                    fromToNodeSet.put(neighbour, polledPathWeight);
                }
                if (neighbour.equals(map.getTileTarget())) {
                    super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
                    return;
                }
            }
        }
        super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
    }

    /**
     * @return known theoretical time complecity.
     */
    @Override
    public String getTheoreticalTime() {
        return "O( | V + E | log | V |)";
    }

    /**
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
