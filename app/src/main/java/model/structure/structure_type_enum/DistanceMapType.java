package model.structure.structure_type_enum;

/**
 * Types of distance trackers used in search algorithms
 */
public enum DistanceMapType {
    ARRAY_2D("double[][] array"),
    ARRAY_1D("double[] as 2D array");

    private final String textValue;

    DistanceMapType(String textValue) {
        this.textValue = textValue;
    }

    /**
     * @return description of DistanceMapType's meaning
     */
    public String getTextValue() {
        return this.textValue;
    }
}
