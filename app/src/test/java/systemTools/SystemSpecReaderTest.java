package systemTools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemSpecReaderTest {

    @Test
    public void interestedKeysExistTest() {
        var reader = new SystemSpecReader();
        var keys = reader.getInterestingPropertyKeys();
        assertTrue(keys.contains("sun.management.compiler"));
        assertTrue(keys.contains("java.runtime.version"));
        assertTrue(keys.contains("java.vm.version"));
        assertTrue(keys.contains("java.vm.name"));
    }
}
