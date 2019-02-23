package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.structure.Queue;
import model.structure.Stack;
import model.structure.UniqueSet;
import model.structure.custom_structure.CustomHashSet;
import model.structure.custom_structure.CustomHashSetDynamicSize;
import model.structure.custom_structure.FIFOQueue;
import model.structure.custom_structure.LIFOStack;
import model.structure.premade_structure.PreMadeStack;
import model.structure.premade_structure.PremadeQueue;
import model.structure.premade_structure.PremadeUniqueSet;
import model.web.WeightedPoint;
import search_algorithm.structure_type.QueueType;
import search_algorithm.structure_type.StackType;
import search_algorithm.structure_type.UniqueSetType;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * classic breath-first-search
 */
public class BreathSearch extends AnalysableAlgorithm {
    private QueueType queueType;
    private UniqueSetType uniqueSetType;
    /**
     * classic breath-first-search that extends AnalysableAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     */
    public BreathSearch(AnalysisWriter analysisWriter, QueueType queueType, UniqueSetType uniqueSetType) {
            super(analysisWriter);
            this.queueType = queueType;
            this.uniqueSetType = uniqueSetType;
    }

    /**
     * Runs breath search by adding neighbours of start to que and comparing them to target node in order.
     * @param timeOfStart       time in nanos of when method is called.
     * @param availableSpace    space left in jvm heap when method is called.
     * @param fromToNodeSet              place to store which step is taken form where.
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
                return new PremadeUniqueSet<>();
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
                return new PremadeQueue<>();
            default:
                return null;
        }
    }

            /**
             *
             * @return known time complexity
             */
    @Override
    public String getTheoreticalTime() {
        return "O( | V + E | )";
    }

    /**
     *
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
}
