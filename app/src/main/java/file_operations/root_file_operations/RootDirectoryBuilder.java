package file_operations.root_file_operations;

import java.io.File;

/**
 * Directory builder having RootFolderFinder.getRootFolder as starting point for path.
 */
public class RootDirectoryBuilder {

    /**
     * Does not tell if directories are created because assumption is that they exist after 1st run.
     * mkdirs method does the check for its existence which is why it's ignored.
     * @param reportFilePath builds directories to make this path exist
     *
     */
    public static void buildDirectories(String reportFilePath) {
        File reportDirectory = new File(RootFolderFinder.getRootFolder() + reportFilePath);
        reportDirectory.mkdirs();
    }
}
