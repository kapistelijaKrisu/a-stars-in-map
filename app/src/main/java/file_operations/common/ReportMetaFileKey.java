package file_operations.common;


/**
 * Keys to reportMeta for replacement
 * Used to read and write ReportMeta from/to files
 */
public enum ReportMetaFileKey {
    // comparing against environments not in this scope of project
    ALGORITHM_NAME("<al_algorithm_name>"),
    ALGORITHM_IMPL("<al_algorithm_impl>"),
    // theoretical amount of steps map can have
    MAP_MAX_STEPS("<test_max_steps>"),

    TEST_SPACE_USED("<test_space>"),
    TEST_STEPS_USED("<test_used_steps>"),
    TEST_PATH_WEIGHT("<test_path_weight>"),
    TEST_TIME_USED("<test_time>");

    private final String stringValue;

    ReportMetaFileKey(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * placeholder on template
     *
     * @return template's placeholder
     */
    public String getStringValue() {
        return stringValue;
    }
}