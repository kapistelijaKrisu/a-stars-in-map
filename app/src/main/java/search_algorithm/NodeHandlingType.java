package search_algorithm;

/**
 * User understandable mapper for how algorithm has progressed in the map
 * used to make visual representation of map
 */
public enum NodeHandlingType {
    IS_START_AND_TARGET('O'),
    START_LOCATION('S'),
    TARGET_LOCATION_AND_NOT_FOUND('N'),
    TARGET_LOCATION_AND_FOUND('F'),
    NOT_PROCESSED('.'),
    PROCESSED_NOT_IN_PATH('!'),
    PROCESSED_IN_PATH('X'),
    WALL('@');

    private final char charValue;

    NodeHandlingType(char charValue) {
        this.charValue = charValue;
    }

    /**
     * @return how the type is written as a character
     */
    public char getCharValue() {
        return this.charValue;
    }
}
