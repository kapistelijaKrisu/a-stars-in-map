package file_operations.common;

/**
 * App uses limited amount of file endings and recognizes certain type of files by them.
 * MAP for a .map file which represents a WebMap as a file.
 * REPORT for .report.md ending file which represents an overview a single search algorithm run.
 * STATISTIC for .statistics.md ending files represents an overview of all search runs done on a single map.
 */
public enum FileClassification {
    MAP(".map"),
    REPORT(".report.md"),
    STATISTICS(".statistics.md");

    private final String fileType;

    FileClassification(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return file's ending
     */
    public String getFileType() {
        return fileType;
    }
}

