package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.structure.custom_structure.DistanceMapAsA2DTable;
import model.structure.custom_structure.DistanceMapAsASingleTable;
import model.structure.custom_structure.MinHeap;
import model.structure.pre_made_structure.PreMadeHeap;
import model.structure.structure_interface.DistanceMap;
import model.structure.structure_interface.Heap;
import model.structure.structure_type_enum.DistanceMapType;
import model.structure.structure_type_enum.HeapType;
import model.web.WeightedPoint;

import java.util.Map;

/**
 * classic CustomDijkstra with minheap and int[][] array to hold current known distances.
 */
public class Dijkstra extends AnalysableAlgorithm {
    private final HeapType heapType;
    private final DistanceMapType distanceMapType;

    /**
     * classic CustomDijkstra that extends AnalysableAlgorithm so it handles report writing.
     *
     * @param analysisWriter  writer that writes the analysis report files.
     * @param heapType        type of heap to construct during searchAlgorithm method
     * @param distanceMapType type of distance map to construct during searchAlgorithm method
     */
    public Dijkstra(AnalysisWriter analysisWriter, HeapType heapType, DistanceMapType distanceMapType) {
        super(analysisWriter, "Dijkstra");
        if (heapType == null || distanceMapType == null) throw new IllegalArgumentException("Arguments cannot be null");
        this.heapType = heapType;
        this.distanceMapType = distanceMapType;
    }

    /**
     * Runs CustomDijkstra with a heap and uses double[][] for upkeeping current know distances.
     *
     * @param timeOfStart    time in nanos of when method is called.
     * @param availableSpace space left in jvm heap when method is called.
     * @param fromToNodeSet  place to store which step is taken form where.
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
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
                return;
            }
            for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                double totalWeight = neighbour.weight + distanceMap.getDistance(polled);
                double currentKnownWeight = distanceMap.getDistance(neighbour);
                if (currentKnownWeight > totalWeight) {
                    heap.insert(new WeightedPoint(neighbour.x, neighbour.y, totalWeight));
                    distanceMap.setDistance(neighbour, totalWeight);
                    fromToNodeSet.put(neighbour, polled);
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
        return getName() + " with " + distanceMapType.getTextValue() + " as distance tracker and " + heapType.getTextValue() + " as min heap.";
    }

    /**
     * @return additional documentation of implementation as metadata for sorting reports by category of implementation
     */
    @Override
    public String getShortImpl() {
        return "Distances: " + distanceMapType.getTextValue() + ", Min heap: " + heapType.getTextValue();
    }

    //testing getters

    /**
     * testing only
     *
     * @return what type of distance tracker is used
     */
    public DistanceMapType getDistanceMapType() {
        return distanceMapType;
    }

    /**
     * testing only
     *
     * @return what type of heap is used
     */
    public HeapType getHeapType() {
        return heapType;
    }
}
