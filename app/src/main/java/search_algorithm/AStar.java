package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.structure.Heap;
import model.structure.custom_structure.MinHeap;
import model.structure.pre_made_structure.PreMadeHeap;
import model.web.WeightedPoint;
import search_algorithm.structure_type.DistanceMapType;
import search_algorithm.structure_type.HeapType;

import java.util.Map;

/**
 * classic A*
 */
public class AStar extends AnalysableAlgorithm {
    private final HeapType heapType;
    private final DistanceMapType distanceMapType;

    /**
     * classic A* that extends AnalysableAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     * @param heapType type of heap to construct during searchAlgorithm method
     * @param distanceMapType type of distance map to construct during searchAlgorithm method
     */
    public AStar(AnalysisWriter analysisWriter, HeapType heapType, DistanceMapType distanceMapType) {
        super(analysisWriter);
        if (heapType == null || distanceMapType == null) throw new IllegalArgumentException("Arguments cannot be null");
        this.heapType = heapType;
        this.distanceMapType = distanceMapType;
    }

    /**
     * Runs A* with a heap and uses double[][] for upkeeping current know distances.
     * Heuristic is WeightedPoint's rough estimate result from calculateRoughDistance method.
     *
     * @param timeOfStart    time in nanos of when method is called.
     * @param availableSpace space left in jvm heap when method is called.
     * @param fromToNodeSet  place to store which step is taken form where.
     */
    @Override
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
                double predictedDistance = neighbour.calculateRoughDistance(map.getTileTarget()) + neighbour.weight;
                double newTotalDistance = distanceMap.getDistance(polled) + predictedDistance;

                double currentKnownWeight = distanceMap.getDistance(neighbour);
                if (currentKnownWeight > newTotalDistance) {

                    heap.insert(new WeightedPoint(neighbour.x, neighbour.y, predictedDistance));
                    distanceMap.setDistance(neighbour, neighbour.weight + distanceMap.getDistance(polled));
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

    private Heap<WeightedPoint> initHeap() {
        switch (heapType) {
            case CUSTOM_MIN_HEAP:
                return new MinHeap<>();
            case PRE_MADE_MIN_HEAP:
                return new PreMadeHeap<>();
            default:
                return null;
        }
    }

    private DistanceMap initDistanceMap() {
        switch (distanceMapType) {
            case ARRAY_2D:
                return new DistanceMapAsA2DTable(map.height(), map.width());
            case ARRAY_1D:
                return new DistanceMapAsASingleTable(map.height(), map.width());
            default:
                return null;
        }
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
    public String getDescription() {
        return toString() + " with " + distanceMapType + " to keep track of known distances and " + heapType + " as an implementation of min heap.";
    }

    @Override
    public String toString() {
        return "A Star";
    }

    public DistanceMapType getDistanceMapType() {
        return distanceMapType;
    }

    public HeapType getHeapType() {
        return heapType;
    }
}
