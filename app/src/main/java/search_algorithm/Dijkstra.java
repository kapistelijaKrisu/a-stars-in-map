package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.structure.Heap;
import model.structure.custom_structure.MinHeap;
import model.structure.premade_structure.PremadeHeap;
import model.web.DistanceMap;
import model.web.DistanceMapAsA2dTable;
import model.web.DistanceMapAsASingleTable;
import model.web.WeightedPoint;
import search_algorithm.structure_type.DistanceMapType;
import search_algorithm.structure_type.HeapType;

import java.util.Map;

/**
 * classic CustomDijkstra with minheap and int[][] array to hold current known distances.
 */
public class Dijkstra extends AnalysableAlgorithm {
    private HeapType heapType;
    private DistanceMapType distanceMapType;
    /**
     * classic CustomDijkstra that extends AnalysableAlgorithm so it handles report writing.
     *
     * @param analysisWriter writer that writes the analysis report files.
     */
    public Dijkstra(AnalysisWriter analysisWriter, HeapType heapType, DistanceMapType distanceMapType) {
        super(analysisWriter);
        this.heapType = heapType;
        this.distanceMapType = distanceMapType;
    }
    /**
     * Runs CustomDijkstra with a heap and uses double[][] for upkeeping current know distances.
     * @param timeOfStart       time in nanos of when method is called.
     * @param availableSpace    space left in jvm heap when method is called.
     * @param fromToNodeSet              place to store which step is taken form where.
     */
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
        var heap = initHeap();
        var distanceMap = initDistanceMap();
        distanceMap.setDistance(map.getTileStart(), 0);

        var startNodeWeight = new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0);
        heap.insert(startNodeWeight);
        fromToNodeSet.put(startNodeWeight, null);

        while (!heap.isEmpty()) {
            WeightedPoint polled = heap.next();
            for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                int totalWeight = getNewWeight(neighbour.weight, distanceMap.getDistance(polled));
                int currentKnownWeight = (int) distanceMap.getDistance(neighbour);
                if (currentKnownWeight > totalWeight) {
                    heap.insert(new WeightedPoint(neighbour.x, neighbour.y, totalWeight));
                    distanceMap.setDistance(neighbour, totalWeight);
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

    private Heap<WeightedPoint> initHeap() {
        switch (heapType) {
            case CUSTOM_MIN_HEAP:
                return new MinHeap<>();
            case PRE_MADE_MIN_HEAP:
                return new PremadeHeap<>();
            default:
                return null;
        }
    }

    private DistanceMap initDistanceMap() {
        switch (distanceMapType) {
            case ARRAY_2D:
                return new DistanceMapAsA2dTable(map.height(), map.width());
            case ARRAY_1D:
                return new DistanceMapAsASingleTable(map.height(), map.width());
            default:
                return null;
        }
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
    public String getDescription() {
        return toString() + " with " + distanceMapType + " to keep track of known distances and " + heapType + " as an implementation of min heap.";
    }

    @Override
    public String toString() {
        return "Dijkstra";
    }
}
