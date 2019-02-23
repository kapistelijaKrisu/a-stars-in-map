package file_operations.map_reader;

import map_generator.MapGeneratorFromFilesTest;
import model.web.WebMap;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MapLoaderTest {

    @Test
    public void testSuccessPathTest() throws IOException {
        String fileName = "testMapSuccess.map";
        ClassLoader classLoader = new MapGeneratorFromFilesTest().getClass().getClassLoader();
        File mockMap = new File(classLoader.getResource(fileName).getFile());
        MapLoader mapLoader = new MapLoader();
        WebMap map = mapLoader.loadMapFromFile(mockMap);

        assertEquals(new Point(1, 1), map.getTileStart());
        assertEquals(new Point(1, 0), map.getTileTarget());
        assertEquals(map.getName(), "testMapSuccess");
        assertTrue(map.isValid());
        int[][] expectedMap = new int[2][3];
        expectedMap[0][0] = 0;
        expectedMap[0][1] = 1;
        expectedMap[0][2] = 1;

        expectedMap[1][0] = 0;
        expectedMap[1][1] = 1;
        expectedMap[1][2] = 0;
        assertEquals(expectedMap.length, map.getMap().length);
        assertEquals(expectedMap[0].length, map.getMap()[0].length);
        assertEquals(expectedMap[1].length, map.getMap()[1].length);

        assertEquals(expectedMap[0][0], map.getMap()[0][0]);
        assertEquals(expectedMap[0][1], map.getMap()[0][1]);
        assertEquals(expectedMap[0][2], map.getMap()[0][2]);

        assertEquals(expectedMap[1][0], map.getMap()[1][0]);
        assertEquals(expectedMap[1][1], map.getMap()[1][1]);
        assertEquals(expectedMap[1][2], map.getMap()[1][2]);
    }

    @Test
    public void errorInCreationReturnsNullMapTest() throws IOException {

        String fileName = "testMapFail.map";
        ClassLoader classLoader = new MapGeneratorFromFilesTest().getClass().getClassLoader();
        File mockMap = new File(classLoader.getResource(fileName).getFile());
        MapLoader mapLoader = new MapLoader();
        WebMap map = null;
        try {

            map = mapLoader.loadMapFromFile(mockMap);
            fail("was supposed to throw nullpointerexception");
        } catch (NullPointerException e) {
            assertNull(map);
        }

    }
}
