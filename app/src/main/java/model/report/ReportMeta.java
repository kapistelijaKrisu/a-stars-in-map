package model.report;

/**
 * In app memory representation of report's metadata.
 * Used to further analyze search algorithms for comparison and statistics.
 */
public class ReportMeta {

    private String algorithmName;
    private String algorithmImplementationType;
    private Long testMaxSteps;
    private Double testSpace;
    private Double testUsedSteps;
    private Double testPathWeight;
    private Double testTime;

    /**
     * Checks for private members and determines if is valid.
     * Requirement for valid: no member can be null
     * algorithmName and algorithmImplementationType cannot be blank
     * all numeric values must be above 0 with exception of testSpace
     * testSpace can be negative because jvm might release space in middle of algorithm run screwing with this.
     *
     * @return true if valid and falso if not valid
     */
    public boolean isValid() {
        if (algorithmName == null || algorithmName.isBlank()) return false;
        if (algorithmImplementationType == null || algorithmImplementationType.isBlank()) return false;
        if (testMaxSteps == null || testMaxSteps < 0) return false;
        if (testSpace == null) return false;
        if (testUsedSteps == null || testUsedSteps < 0) return false;
        if (testPathWeight == null || testPathWeight < 0) return false;
        if (testTime == null || testTime < 0) return false;
        return true;
    }

    // regular getters and setters
    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmImplementationType() {
        return algorithmImplementationType;
    }

    public void setAlgorithmImplementationType(String algorithmImplementationType) {
        this.algorithmImplementationType = algorithmImplementationType;
    }

    public Long getTestMaxSteps() {
        return testMaxSteps;
    }

    public void setTestMaxSteps(Long testMaxSteps) {
        this.testMaxSteps = testMaxSteps;
    }

    public Double getTestSpace() {
        return testSpace;
    }

    public void setTestSpace(Double testSpace) {
        this.testSpace = testSpace;
    }

    public Double getTestUsedSteps() {
        return testUsedSteps;
    }

    public void setTestUsedSteps(Double testUsedSteps) {
        this.testUsedSteps = testUsedSteps;
    }

    public Double getTestPathWeight() {
        return testPathWeight;
    }

    public void setTestPathWeight(Double testPathWeight) {
        this.testPathWeight = testPathWeight;
    }

    public Double getTestTime() {
        return testTime;
    }

    public void setTestTime(Double testTime) {
        this.testTime = testTime;
    }
}
