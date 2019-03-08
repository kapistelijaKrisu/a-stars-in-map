package model.structure.structure_type_enum;

/**
 * Types of stacks used in search algorithms
 */
public enum StackType {
    PRE_MADE_STACK("Java's ArrayDeque"),
    CUSTOM_STACK("Stack by reference");

    private final String textValue;

    StackType(String textValue) {
        this.textValue = textValue;
    }

    /**
     * @return description of StackType's meaning
     */
    public String getTextValue() {
        return this.textValue;
    }
}
