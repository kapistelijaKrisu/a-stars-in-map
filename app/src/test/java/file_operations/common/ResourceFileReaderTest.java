package file_operations.common;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceFileReaderTest {

    @Test
    public void readsFileCorrectlyTest() {
        String fileContent = ResourceFileReader.readResourceFile("/maps/testMapFail.map");
        assertEquals(
                "# esimerkki sisällöstä" + System.lineSeparator() +
                        "decode-begin" + System.lineSeparator() +
                        "T 0" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                        "decode-end" + System.lineSeparator() +
                        "start 1 1" + System.lineSeparator() +
                        "target 1 0" + System.lineSeparator() + System.lineSeparator() +
                        "height 2" + System.lineSeparator() +
                        "width 3" + System.lineSeparator() +
                        "map" + System.lineSeparator() +
                        "T.." + System.lineSeparator() + System.lineSeparator() +
                        "T.T", fileContent);
    }

    @Test
    public void fileNotFoundTest() {
        String fileContent = ResourceFileReader.readResourceFile("no such file");
        assertNull(fileContent);
    }
}
