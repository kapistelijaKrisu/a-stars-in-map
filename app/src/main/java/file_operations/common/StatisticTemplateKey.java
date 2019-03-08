package file_operations.common;

/**
 * Keys to overview-template for replacement
 * Used by AnalysisOverviewWriter
 */
public enum StatisticTemplateKey {
    MAP_NAME("<map>"),
    FASTEST_TIME_ALGORITHM("<fastest>"),
    CHEAPEST_IN_SPACE_ALGORITHM("<cheapest>"),
    TABLE_OF_ALGORITHM_PERFORMANCE("<performance_table>");

    private final String stringValue;

    StatisticTemplateKey(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}