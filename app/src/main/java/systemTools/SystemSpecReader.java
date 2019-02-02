package systemTools;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Helper to get information on some system specifications
 */

public class SystemSpecReader {
    OperatingSystem operatingSystem;
    HardwareAbstractionLayer hardwareAbstractionLayer;
    Runtime runtime = Runtime.getRuntime();
    List<String> interestinPropertyKeys;

    /**
     * Initializes few internal helpers
     * Not really testable since results vary by hardware / os / java
     */
    public SystemSpecReader() {
        SystemInfo systemInfo = new SystemInfo();
        operatingSystem = systemInfo.getOperatingSystem();
        hardwareAbstractionLayer = systemInfo.getHardware();

        interestinPropertyKeys = new ArrayList<>();
        interestinPropertyKeys.add("sun.management.compiler");
        interestinPropertyKeys.add("java.runtime.version");
        interestinPropertyKeys.add("java.vm.version");
        interestinPropertyKeys.add("java.vm.name");
    }

    /**
     * gets cpu name
     *
     * @return cpu name
     */

    public String getCpu() {
        getJvm();
        return hardwareAbstractionLayer.getProcessor().getName();
    }

    /**
     * gets some jvm specs
     *
     * @return properties of process: sun.management.compiler, java.runtime.version, java.vm.version, java.vm.name
     * separated by lineseparator
     */
    public String getJvm() {
        Properties p = System.getProperties();
        String result = "";
        for (String key : interestinPropertyKeys) {
            result += key + ": " + p.get(key) + System.lineSeparator();
        }
        return result;
    }

    /**
     * @return Operating system family name and version
     */
    public String getOperatingSystem() {
        return operatingSystem.getFamily() + " " + operatingSystem.getVersion();
    }

    /**
     * @return human readable available heap space of this process
     */
    public String getAvailableHeapSizeRedable() {
        long availableMemory = getAvailableHeapSize();
        return availableMemory / (1024 * 1024) + " megabyte " + availableMemory % 1024 + " kilobytes";
    }

    /**
     * @return available heap space of this process
     */
    public long getAvailableHeapSize() {
        return runtime.maxMemory() - runtime.freeMemory();
    }

    // testing that the wanted keys are in there
    public List<String> getInterestingPropertyKeys() {
        return interestinPropertyKeys;
    }
}
