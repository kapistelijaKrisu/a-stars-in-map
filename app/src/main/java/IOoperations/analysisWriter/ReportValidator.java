package IOoperations.analysisWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that report values can be mapped correctly
 */
public class ReportValidator {
    private final List<String> mandatoryTemplateKeys;
    public ReportValidator() {
        mandatoryTemplateKeys = new ArrayList<>(18);
        mandatoryTemplateKeys.add("{algorithm}");
        mandatoryTemplateKeys.add("{env_cpu}");
        mandatoryTemplateKeys.add("{env_os}");
        mandatoryTemplateKeys.add("{env_compiler}");
        mandatoryTemplateKeys.add("{env_runtime}");
        mandatoryTemplateKeys.add("{env_vm_name}");
        mandatoryTemplateKeys.add("{env_vm_version}");
        mandatoryTemplateKeys.add("{env_heap}");
        mandatoryTemplateKeys.add("{map_info}");
        mandatoryTemplateKeys.add("{al_time}");
        mandatoryTemplateKeys.add("{al_space}");
        mandatoryTemplateKeys.add("{al_doc}");
        mandatoryTemplateKeys.add("{test_time}");
        mandatoryTemplateKeys.add("{test_space}");
        mandatoryTemplateKeys.add("{test_used_steps}");
        mandatoryTemplateKeys.add("{test_max_steps}");
        mandatoryTemplateKeys.add("{test_path_weight}");
        mandatoryTemplateKeys.add("{test_processed_map}");
    }

    /**
     * @param mapper validation target
     * @return true if ready to be used, false if missing information
     * All keys and values must not be null
     * Required keys:
     * {algorithm}
     * {env_cpu}
     * {env_os}
     * {env_compiler}
     * {env_runtime}
     * {env_vm_name}
     * {env_vm_version}
     * {env_heap}
     * {map_info}
     * {al_time}
     * {al_space}
     * {al_doc}
     * {test_time}
     * {test_space}
     * {test_used_steps}
     * {test_max_steps}
     * {test_path_weight}
     * {test_processed_map}
     */
    public boolean validateMapper(Map<String, String> mapper) {
        for (String mandatoryValue: mandatoryTemplateKeys) {
            if (mapper.get(mandatoryValue) == null) {
                System.out.println("Missing key: "+ mandatoryValue);
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return Condition required for returning true in validateMapper
     */
    public String getValidatorCondition() {
        String validatorCondition = "Following values must exist in mapper and none of existing pairs can be null:\n";
        for (String value: mandatoryTemplateKeys) {
            validatorCondition += value + "\n";
        }
        return validatorCondition;
    }
}
