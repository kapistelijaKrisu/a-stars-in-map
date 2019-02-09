package IOoperations.analysisWriter;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportValidatorTest {

    @Test
    public void validationPassesOnValidMapperTest() {
        ReportValidator reportValidator = new ReportValidator();
        HashMap<String, String> mapper = new HashMap<>(18);
        mapper.put("{algorithm}", "");
        mapper.put("{env_cpu}", "");
        mapper.put("{env_os}", "");
        mapper.put("{env_compiler}", "");
        mapper.put("{env_runtime}", "");
        mapper.put("{env_vm_name}", "");
        mapper.put("{env_vm_version}", "");
        mapper.put("{env_heap}", "");
        mapper.put("{map_info}", "");
        mapper.put("{al_time}", "");
        mapper.put("{al_space}", "");
        mapper.put("{al_doc}", "");
        mapper.put("{test_time}", "");
        mapper.put("{test_space}", "");
        mapper.put("{test_used_steps}", "");
        mapper.put("{test_max_steps}", "");
        mapper.put("{test_path_weight}", "");
        mapper.put("{test_processed_map}", "");

        assertTrue(reportValidator.validateMapper(mapper));
    }

    @Test
    public void validationFailsOnValidButNullMapperTest() {
        ReportValidator reportValidator = new ReportValidator();
        HashMap<String, String> mapper = new HashMap<>(18);
        mapper.put("{algorithm}", "");
        mapper.put("{env_cpu}", "");
        mapper.put("{env_os}", "");
        mapper.put("{env_compiler}", "");
        mapper.put("{env_runtime}", "");
        mapper.put("{env_vm_name}", "");
        mapper.put("{env_vm_version}", "");
        mapper.put("{env_heap}", "");
        mapper.put("{map_info}", "");
        mapper.put("{al_time}", "");
        mapper.put("{al_space}", "");
        mapper.put("{al_doc}", "");
        mapper.put("{test_time}", "");
        mapper.put("{test_space}", "");
        mapper.put("{test_used_steps}", "");
        mapper.put("{test_max_steps}", "");
        mapper.put("{test_path_weight}", "");
        mapper.put("{test_processed_map}", "");

        for (String key : mapper.keySet()) {
            mapper.put(key, null);
            assertFalse(reportValidator.validateMapper(mapper));
            mapper.put(key, "");
        }
        assertTrue(reportValidator.validateMapper(mapper));
    }

    @Test
    public void validationFailsOnPartiallyCompleteMapperTest() {
        ReportValidator reportValidator = new ReportValidator();
        HashMap<String, String> mapper = new HashMap<>(18);
        mapper.put("{algorithm}", "");
        mapper.put("{env_cpu}", "");
        mapper.put("{env_os}", "");
        mapper.put("{env_compiler}", "");
        mapper.put("{env_runtime}", "");
        mapper.put("{env_vm_name}", "");
        mapper.put("{env_vm_version}", "");
        mapper.put("{env_heap}", "");
        mapper.put("{map_info}", "");
        mapper.put("{al_time}", "");
        mapper.put("{al_space}", "");
        mapper.put("{al_doc}", "");
        mapper.put("{test_time}", "");
        mapper.put("{test_space}", "");
        mapper.put("{test_used_steps}", "");
        mapper.put("{test_max_steps}", "");
        mapper.put("{test_path_weight}", "");
        mapper.put("{test_processed_map}", "");

        HashMap<String, String> loopedMapper = new HashMap<>(18);
        loopedMapper.put("{algorithm}", "");
        loopedMapper.put("{env_cpu}", "");
        loopedMapper.put("{env_os}", "");
        loopedMapper.put("{env_compiler}", "");
        loopedMapper.put("{env_runtime}", "");
        loopedMapper.put("{env_vm_name}", "");
        loopedMapper.put("{env_vm_version}", "");
        loopedMapper.put("{env_heap}", "");
        loopedMapper.put("{map_info}", "");
        loopedMapper.put("{al_time}", "");
        loopedMapper.put("{al_space}", "");
        loopedMapper.put("{al_doc}", "");
        loopedMapper.put("{test_time}", "");
        loopedMapper.put("{test_space}", "");
        loopedMapper.put("{test_used_steps}", "");
        loopedMapper.put("{test_max_steps}", "");
        loopedMapper.put("{test_path_weight}", "");
        loopedMapper.put("{test_processed_map}", "");

        for (String key : mapper.keySet()) {
            loopedMapper.remove(key);
            assertFalse(reportValidator.validateMapper(loopedMapper));
            loopedMapper.put(key, "");
        }
    }
}
