package file_operations.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceFileReader {

    /**
     * Reads resource from resource file.
     *
     * @param resourceName file path where main/resources is the root.
     * @return read file as a string. or null if failed to read. e.g file doesn't exist
     */
    public static String readResourceFile(String resourceName) {
        try {
            InputStream in = ResourceFileReader.class.getResourceAsStream(resourceName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (NullPointerException e) {
            System.out.println("File not found");
        } catch (Exception e) { //might be security or space related
            System.out.println(e.getMessage());
        }
        return null;
    }
}
