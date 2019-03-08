package model.report;

/**
 * Report templates all placeholder keys.
 */
public enum ReportCodeKey {
    ALGORITHM_NAME("{algorithm}"),
    CPU("{env_cpu}"),
    OS("{env_os}"),
    COMPILER("{env_compiler}"),
    RUNTIME("{env_runtime}"),
    VM_NAME("{env_vm_name}"),
    VM_VERSION("{env_vm_version}"),
    ENV_HEAP("{env_heap}"),
    MAP_INFO("{map_info}"),
    THEORY_TIME_COMPLEXITY("{al_time}"),
    THEORY_SPACE_COMPLEXITY("{al_space}"),
    IMPLEMENTATION_INFO("{al_doc}"),
    SPACE_USED("{test_space}"),
    STEPS_USED("{test_used_steps}"),
    PROCESSED_MAP("{test_processed_map}"),
    PATH_WEIGHT("{test_path_weight}"),
    MAX_STEPS("{test_max_steps}"),
    TIME_USED("{test_time}");

    private final String stringValue;

    ReportCodeKey(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     *
     * @return string value of the placeholder
     */
    public String getStringValue() {
        return stringValue;
    }
}
