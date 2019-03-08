package file_operations.common;

/**
 * Commonly used path. MAP for where the maps are located and REPORTS where the reporting is located.
 */
public enum DocumentPath {
    MAP("/maps/"),
    REPORTS("/doc/reports/");

    private final String filePath;

    DocumentPath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return path as string
     */
    public String getFilePath() {
        return filePath;
    }
}
