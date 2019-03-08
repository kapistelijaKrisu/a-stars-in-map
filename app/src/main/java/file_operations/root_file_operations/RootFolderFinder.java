package file_operations.root_file_operations;

import java.io.File;
import java.util.Objects;

/**
 * Finds the folder where app is running
 */
public class RootFolderFinder {

    /**
     * Finds the folder where app is running. Works with file and jar type app
     *
     * @return path to folder where app is running
     */
    public static String getRootFolder() {
        String root = null;
        String protocol = RootFolderFinder.class.getResource("").getProtocol();
        if (Objects.equals(protocol, "jar")) {
            root = new File(".").getAbsoluteFile().getParent();
            // run in ide
        } else if (Objects.equals(protocol, "file")) {
            root = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParent();
            // run in ide
        }
        return root + "/";
    }
}
