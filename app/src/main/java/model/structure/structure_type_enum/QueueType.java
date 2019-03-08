package model.structure.structure_type_enum;

/**
 * Types of queues used in search algorithms
 */
public enum QueueType {
    CUSTOM_QUEUE("Queue by reference"),
    PRE_MADE_QUEUE("Java ArrayDeque");

    private final String textValue;

    QueueType(String textValue) {
        this.textValue = textValue;
    }
    /**
     * @return description of QueueType's meaning
     */
    public String getTextValue() {
        return this.textValue;
    }
}
