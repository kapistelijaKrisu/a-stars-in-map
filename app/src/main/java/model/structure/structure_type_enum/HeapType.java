package model.structure.structure_type_enum;

/**
 * Types of heaps used in search algorithms
 */
public enum HeapType {
    CUSTOM_MIN_HEAP("Custom MinHeap"),
    PRE_MADE_MIN_HEAP("Java's PriorityQueue");

    private final String textValue;

    HeapType(String textValue) {
        this.textValue = textValue;
    }

    /**
     * @return description of HeapType's meaning
     */
    public String getTextValue() {
        return this.textValue;
    }
}
