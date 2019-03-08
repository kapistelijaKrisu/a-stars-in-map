package file_operations.common;

/**
 * Locations in resource folder for templates
 * ANALYSIS for single run of algorithm statistics
 * ANALYSIS_AS_A_TABLE_ROW for a single algorithm as row template
 * OVERVIEW_TEMPLATE for overview of algorithm performance template
 */
public enum Template {
    ANALYSIS("/report-template.md"),
    ANALYSIS_AS_A_TABLE_ROW("/overview-algorithm-row.md"),
    OVERVIEW_TEMPLATE("/overview-template.md");

    private final String fileName;

    Template(String nodeType) {
        this.fileName = nodeType;
    }

    /**
     * @return path as string to the resource file
     */
    public String getFileName() {
        return fileName;
    }
}
