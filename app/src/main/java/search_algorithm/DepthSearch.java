package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.structure.Stack;
import model.structure.UniqueSet;
import model.structure.custom_structure.CustomHashSet;
import model.structure.custom_structure.CustomHashSetDynamicSize;
import model.structure.custom_structure.LIFOStack;
import model.structure.pre_made_structure.PreMadeStack;
import model.structure.pre_made_structure.PreMadeUniqueSet;
import model.web.WeightedPoint;
import search_algorithm.structure_type.StackType;
import search_algorithm.structure_type.UniqueSetType;

import java.util.Map;

/**
 * classic dreath-first-search
 */
public class DepthSearch extends AnalysableAlgorithm {

    private final StackType stackType;
    private final UniqueSetType uniqueSetType;

    /**
     * classic depth-first-search that extends AnalysableAlgorithm so it handles report writing.
     * @param analysisWriter writer that writes the analysis report files.
     * @param stackType type of stack to construct during searchAlgorithm method
     * @param uniqueSetType type of set to construct during searchAlgorithm method
     */
    public DepthSearch(AnalysisWriter analysisWriter, StackType stackType, UniqueSetType uniqueSetType) {
        super(analysisWriter);
        if (stackType == null || uniqueSetType == null) throw new IllegalArgumentException("Arguments cannot be null");
        this.stackType = stackType;
        this.uniqueSetType = uniqueSetType;
    }

    /**
     * Runs depth search by using stack and adding neighbours to it and then comparing popped neightbour wit target.
     *
     * @param timeOfStart    time in nanos of when method is called.
     * @param availableSpace space left in jvm heap when method is called.
     * @param fromToNodeSet  place to store which step is taken form where.
     */
    @Override
    protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
        WeightedPoint originalStart = new WeightedPoint(map.getTileStart().x, map.getTileStart().y, 0);

        UniqueSet<WeightedPoint> visited = initVisitedSet();
        Stack<WeightedPoint> stack = initProcessingStack();

        visited.add(originalStart);
        stack.push(originalStart);
        fromToNodeSet.put(originalStart, null);

        while (!stack.isEmpty()) {
            WeightedPoint polled = stack.pop();
            if (polled.equals(map.getTileTarget())) {
                super.handleReportWriting(fromToNodeSet, timeOfStart, availableSpace);
                return;
            } else {
                for (WeightedPoint neighbour : map.getNeighbours(polled)) {
                    if (!visited.contains(neighbour)) {
                        fromToNodeSet.put(neighbour, polled);
                        visited.add(neighbour);
                        stack.push(neighbour);
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

    private Stack<WeightedPoint> initProcessingStack() {
        switch (stackType) {
            case CUSTOM_STACK:
                return new LIFOStack<>();
            case PRE_MADE_STACK:
                return new PreMadeStack<>();
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
        return toString() + " with " + uniqueSetType + " to keep track of visited edges and " + stackType + " as an implementation of stack.";
    }

    @Override
    public String toString() {
        return "Depth first";
    }

    //testing getters

    public StackType getStackType() {
        return stackType;
    }

    public UniqueSetType getUniqueSetType() {
        return uniqueSetType;
    }
}
