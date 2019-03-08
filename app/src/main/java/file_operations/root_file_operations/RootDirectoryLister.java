package file_operations.root_file_operations;

import java.io.File;

/**
 * Lists files having RootFolderFinder.getRootFolder as starting point for path.
 */
public class RootDirectoryLister {
    /**
     * Forms path with RootFolderFinder.getRootFolder + directory and lists files under it non-recursively.
     *
     * @param directory RootFolderFinder.getRootFolder + param is resulting path
     * @return directories at the level of resulting path
     */
    public File[] listFiles(String directory) {
        if (directory == null) directory = "";
        try {
            File mainMapFolder = new File(RootFolderFinder.getRootFolder() + "/" + directory);
            if (!mainMapFolder.exists() || !mainMapFolder.isDirectory()) return new File[0];

            return mainMapFolder.listFiles();
        } catch (Exception e) {
            System.out.println("Error listing files");
            return new File[0];
        }
    }
}
