package mapGenerator;

import IOoperations.mapReader.MapLocator;
import model.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MapGeneratorFromFilesTest {

    private MapGeneratorFromFiles mapGeneratorFromFiles;
    private MapLocator mockLocator;
    List<File> mockFiles;

    @BeforeEach
    public void setUp() throws IOException {
        String fileName = "testMapSuccess.map";
        ClassLoader classLoader = new MapGeneratorFromFilesTest().getClass().getClassLoader();
        File mockMap = new File(classLoader.getResource(fileName).getFile());

        mockFiles = new ArrayList<>();
        mockFiles.add(mockMap);

        mockLocator = mock(MapLocator.class);
        when(mockLocator.findMaps()).thenReturn(mockFiles);
    }

    @Test
    public void testSuccessPathTest() {
        String input = "0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        mapGeneratorFromFiles = new MapGeneratorFromFiles(new Scanner(System.in));
        mapGeneratorFromFiles.setMapLocator(mockLocator);

        WebMap map = mapGeneratorFromFiles.createMap();
        assertEquals(new Point(1, 1), map.getTileAt());
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
    public void noSuchFileForFewRoundsTest() {
        // shouldn't crash
        String input = "2\n2\n2\n2\n2\n2\n2\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        mapGeneratorFromFiles = new MapGeneratorFromFiles(new Scanner(System.in));
        mapGeneratorFromFiles.setMapLocator(mockLocator);
        mapGeneratorFromFiles.createMap();
    }

    @Test
    public void errorInNoMapsFoundTest() throws IOException {
        when(mockLocator.findMaps()).thenReturn(new ArrayList<>());
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        mapGeneratorFromFiles = new MapGeneratorFromFiles(new Scanner(System.in));
        mapGeneratorFromFiles.setMapLocator(mockLocator);
        WebMap map = mapGeneratorFromFiles.createMap();
        assertEquals(null, map);
    }

    @Test
    public void errorInCreationReturnsNullMapTest() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        mapGeneratorFromFiles = new MapGeneratorFromFiles(new Scanner(System.in));
        String fileName = "testMapFail.map";
        ClassLoader classLoader = new MapGeneratorFromFilesTest().getClass().getClassLoader();
        File mockMap = new File(classLoader.getResource(fileName).getFile());
        mockFiles.add(mockMap);
        mapGeneratorFromFiles.setMapLocator(mockLocator);
        WebMap map = mapGeneratorFromFiles.createMap();
        assertEquals(null, map);
    }
}
