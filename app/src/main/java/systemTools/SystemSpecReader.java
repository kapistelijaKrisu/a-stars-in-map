package systemTools;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.Properties;

/**
 * Helper to get information on some system specifications
 */

public class SystemSpecReader {
    private final OperatingSystem operatingSystem;
    private final HardwareAbstractionLayer hardwareAbstractionLayer;
    private final Runtime runtime = Runtime.getRuntime();
    private final Properties properties;
    /**
     * Initializes few internal helpers
     * Not really testable since results vary by hardware / os / java
     */
    public SystemSpecReader() {
        SystemInfo systemInfo = new SystemInfo();
        operatingSystem = systemInfo.getOperatingSystem();
        hardwareAbstractionLayer = systemInfo.getHardware();
        properties = System.getProperties();
    }

    /**
     * gets cpu name
     * @return cpu name
     */

    public String getCpu() {
        return hardwareAbstractionLayer.getProcessor().getName();
    }

    /**
     *
     * @return compiler defined in properties
     */
    public String getCompiler() {
        return properties.get("sun.management.compiler").toString();
    }

    /**
     *
     * @return runtime defined in properties
     */
    public String getRuntime() {
        return properties.get("java.runtime.version").toString();
    }

    /**
     *
     * @return virtual machine name defined in properties
     */
    public String getVirtualMachineName() {
        return properties.get("java.vm.name").toString();
    }

    /**
     *
     * @return virtual machine verions defined in properties
     */
    public String getVirtualMachineVersion() {
        return properties.get("java.vm.version").toString();
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
    public String getAvailableHeapSizeReadable() {
        long availableMemory = getAvailableHeapSize();
        return availableMemory / (1024 * 1024) + " megabyte " + availableMemory % 1024 + " kilobytes";
    }

    /**
     * @return available heap space of this process
     */
    public long getAvailableHeapSize() {
        return runtime.maxMemory() - runtime.freeMemory();
    }

}
