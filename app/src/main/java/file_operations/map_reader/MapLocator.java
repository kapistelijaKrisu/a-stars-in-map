package file_operations.map_reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads list of .map files from maploaderdirectory folder recursively.
 */
public class MapLocator {

    /**
     * @return List of files inside maploaderdirectory folder that are of .map type
     * @throws IOException if maploaderdirectory directory is not found in same lever as program
     */
    public List<File> findMaps() throws IOException {
        File mainMapFolder = new File(new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParent() + "/maploaderdirectory");
        if (!mainMapFolder.exists() || !mainMapFolder.isDirectory())
            throw new IOException("Error. \"maploaderdirectory\" folder not found at root of the project!");

        List<File> locatedMaps = new ArrayList<>();
        recursiveMapAdding(mainMapFolder, locatedMaps);
        return locatedMaps;
    }

    private void recursiveMapAdding(File root, List<File> foundfiles) {
        if (root.isDirectory()) {
            for (File child : root.listFiles()) {
                recursiveMapAdding(child, foundfiles);
            }
        } else if (root.getName().endsWith(".map")) {
            foundfiles.add(root);
        }
    }
}
