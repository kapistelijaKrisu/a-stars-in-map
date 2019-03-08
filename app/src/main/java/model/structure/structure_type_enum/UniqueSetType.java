package model.structure.structure_type_enum;

/**
 * Types of unique sets used in search algorithms
 */
public enum UniqueSetType {
    PRE_MADE_HASH_SET("Java's HashSet"),
    CUSTOM_SET_SIZE_HASH_SET("Static HashSet"),
    CUSTOM_DYNAMIC_SIZE_HASH_SET("Dynamic HashSet");

    private final String textValue;

    UniqueSetType(String textValue) {
        this.textValue = textValue;
    }

    /**
     * @return description of UniqueSetType's meaning
     */
    public String getTextValue() {
        return this.textValue;
    }
}
