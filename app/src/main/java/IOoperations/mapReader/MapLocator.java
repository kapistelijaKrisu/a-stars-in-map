package IOoperations.mapReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads list of .map files from maps folder that is on same lever as the app.
 */
public class MapLocator {

    /**
     *
     * @return list of files inside maps folder that are of .map type
     * @throws IOException if maps directory is not found in same lever as program
     */
    public List<File> findMaps() throws IOException {
        File folder = new File(new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParent() + "/maps");
        if (folder.exists() == false || folder.isDirectory() == false) throw new IOException("Error. \"maps\" folder not found at root of the project!");
        File[] listOfFiles = folder.listFiles();

        List<File> locatedMaps = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".map")) {
                locatedMaps.add(listOfFiles[i]);
            }
        }
        return locatedMaps;
    }
}
