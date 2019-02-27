package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.structure.Queue;
import model.structure.UniqueSet;
import model.structure.custom_structure.CustomHashSet;
import model.structure.custom_structure.CustomHashSetDynamicSize;
import model.structure.custom_structure.FIFOQueue;
import model.structure.pre_made_structure.PreMadeQueue;
import model.structure.pre_made_structure.PreMadeUniqueSet;
import model.web.WeightedPoint;
import search_algorithm.structure_type.QueueType;
import search_algorithm.structure_type.UniqueSetType;

import java.util.Map;

/**
 * classic breath-first-search
 */
public class BreathSearch extends AnalysableAlgorithm {
    private final QueueType queueType;
    private final UniqueSetType uniqueSetType;

    /**
     * classic breath-first-search that extends AnalysableAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     * @param queueType type of queue to construct during searchAlgorithm method
     * @param uniqueSetType type of set to construct during searchAlgorithm method
     */
    public BreathSearch(AnalysisWriter analysisWriter, QueueType queueType, UniqueSetType uniqueSetType) {
        super(analysisWriter);
        if (queueType == null || uniqueSetType == null) throw new IllegalArgumentException("Arguments cannot be null");
        this.queueType = queueType;
        this.uniqueSetType = uniqueSetType;
    }

    /**
     * Runs breath search by adding neighbours of start to que and comparing them to target node in order.
     *
     * @param timeOfStart    time in nanos of when method is called.
     * @param availableSpace space left in jvm heap when method is called.
     * @param fromToNodeSet  place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
        WeightedPoint orignalStart = new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0);

        UniqueSet<WeightedPoint> visited = initVisitedSet();
        Queue<WeightedPoint> queue = initProcessingQueue();

        visited.add(orignalStart);
        queue.enqueue(orignalStart);
        fromToNodeSet.put(orignalStart, null);

        while (!queue.isEmpty()) {
            WeightedPoint polled = queue.dequeue();
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
                return;
            } else {
                for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                    if (!visited.contains(neighbour)) {
                        fromToNodeSet.put(neighbour, polled);
                        visited.add(neighbour);
                        queue.enqueue(neighbour);
                    }
                }
            }
        }
        super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
    }

    private UniqueSet<WeightedPoint> initVisitedSet() {
        switch (uniqueSetType) {
            case PRE_MADE_HASH_SET:
                return new PreMadeUniqueSet<>();
            case CUSTOM_SET_SIZE_HASH_SET:
                return new CustomHashSetDynamicSize<>();
            case CUSTOM_DYNAMIC_SIZE_HASH_SET:
                return new CustomHashSet<>(map.height() * map.width());
            default:
                return null;
        }
    }

    private Queue<WeightedPoint> initProcessingQueue() {
        switch (queueType) {
            case CUSTOM_QUEUE:
                return new FIFOQueue<>();
            case PRE_MADE_QUEUE:
                return new PreMadeQueue<>();
            default:
                return null;
        }
    }

    /**
     * @return known time complexity
     */
    @Override
    public String getTheoreticalTime() {
        return "O( | V + E | )";
    }

    /**
     * @return known space complexity
     */
    @Override
    public String getTheoreticalSpace() {
        return "| V |";
    }

    @Override
    public String getDescription() {
        return toString() + " with " + uniqueSetType + " to keep track of visited edges and " + queueType + " as an implementation of queue.";
    }

    @Override
    public String toString() {
        return "Breath first";
    }

    //testing getters

    public QueueType getQueueType() {
        return queueType;
    }

    public UniqueSetType getUniqueSetType() {
        return uniqueSetType;
    }
}
