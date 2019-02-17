package model.structure;

public enum NodeHandlingType {
    IS_START_AND_TARGET('O'),
    START_LOCATION('S'),
    TARGET_LOCATION_AND_NOT_FOUND('N'),
    TARGET_LOCATION_AND_FOUND('F'),
    NOT_PROCESSED('.'), PROCESSED_NOT_IN_PATH('v'),
    PROCESSED_IN_PATH('X'),
    WALL('#');

    private final char charValue;

    NodeHandlingType(char nodeType) {
        this.charValue = nodeType;
    }

    public char getCharValue() {
        return this.charValue;
    }
}
