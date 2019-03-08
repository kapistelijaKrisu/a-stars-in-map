package file_operations.root_file_operations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads list of files from given folder recursively having RootFolderFinder.getRootFolder as starting point for path.
 */
public class RootFileLister {
    /**
     * Lists all files under at the app level. To narrow down search use params.
     *
     * @param directory  RootFolderFinder.getRootFolder added directory results in the path under which all files are listed from.
     * @param fileSuffix ending part of resulting filename
     * @return List of files inside directory folder that are of fileSuffix type
     * @throws IOException if IO fails due to non-existing path.
     */
    public List<File> listFiles(String directory, String fileSuffix) throws IOException {
        if (directory == null) directory = "";
        if (fileSuffix == null) fileSuffix = "";

        File mainMapFolder = new File(RootFolderFinder.getRootFolder() + "/" + directory);
        if (!mainMapFolder.exists() || !mainMapFolder.isDirectory())
            throw new IOException("Error. " + directory + " folder not found at root of the project!");

        List<File> locatedFiles = new ArrayList<>();
        recursiveFileListing(mainMapFolder, locatedFiles, fileSuffix);
        return locatedFiles;
    }

    private void recursiveFileListing(File root, List<File> foundFiles, String fileType) {
        if (root.isDirectory()) {
            for (File child : root.listFiles()) {
                recursiveFileListing(child, foundFiles, fileType);
            }
        } else if (root.getName().endsWith(fileType)) {
            foundFiles.add(root);
        }
    }
}
