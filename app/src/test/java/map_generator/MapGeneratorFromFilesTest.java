package map_generator;

import file_operations.root_file_operations.RootFileLister;
import model.web.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapGeneratorFromFilesTest {

    private MapGeneratorFromFiles mapGeneratorFromFiles;
    private RootFileLister mockLocator;
    private List<File> mockFiles;

    @BeforeEach
    public void setUp() throws IOException {
        String fileName = "maps/map_inner_folder/testMapSuccess.map";
        ClassLoader classLoader = MapGeneratorFromFilesTest.class.getClassLoader();
        File mockMap = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());

        mockFiles = new ArrayList<>();
        mockFiles.add(mockMap);

        mockLocator = mock(RootFileLister.class);
        when(mockLocator.listFiles("/maps", ".map")).thenReturn(mockFiles);
    }

    @Test
    public void testSuccessPathTest() {
        String chooseFirstMapListedCommand = "0";
        String input = chooseFirstMapListedCommand + "\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        mapGeneratorFromFiles = new MapGeneratorFromFiles(new Scanner(System.in));
        mapGeneratorFromFiles.setMapLocator(mockLocator);

        WebMap map = mapGeneratorFromFiles.createMap();
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
    public void errorInNoMapsFoundTest() {
        String chooseNonExistentMapCommand = "1", quitCommand = "q";
        String input = chooseNonExistentMapCommand + "\n" + quitCommand + "\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        mapGeneratorFromFiles = new MapGeneratorFromFiles(new Scanner(System.in));
        mapGeneratorFromFiles.setMapLocator(mockLocator);
        WebMap map = mapGeneratorFromFiles.createMap();
        assertEquals(null, map);
    }

    @Test
    public void errorInCreationReturnsNullMapTest() {
        String chooseFaultyMapCommand = "1", quitCommand = "q";
        String input = chooseFaultyMapCommand + "\n" + quitCommand + "\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        mapGeneratorFromFiles = new MapGeneratorFromFiles(new Scanner(System.in));
        String fileName = "maps/testMapFail.map";
        ClassLoader classLoader = this.getClass().getClassLoader();
        File mockMap = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        mockFiles.add(mockMap);
        mapGeneratorFromFiles.setMapLocator(mockLocator);
        WebMap map = mapGeneratorFromFiles.createMap();
        assertNull(map);
    }
}
