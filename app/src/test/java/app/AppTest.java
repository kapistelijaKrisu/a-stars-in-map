package app;

import file_operations.analysis_overview.AnalysisInspector;
import map_generator.MapGenerator;
import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import search_algorithm.AlgorithmCodeKey;
import search_algorithm.AnalysableAlgorithm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppTest {

    @Rule // in case app does not exit run method
    public Timeout globalTimeout = Timeout.seconds(1);
    private Scanner scanner;
    private App app;
    private MapGenerator mockGenerator;
    private HashMap<String,MapGenerator> mapGenerators;
    private AnalysableAlgorithm mockSearch;
    private AnalysisInspector mockAnalysisInspector;
    private WebMap validMap;

    @BeforeEach
    public void setUo() {
        mapGenerators = new HashMap<>();
        mockGenerator = mock(MapGenerator.class);
        mapGenerators.put("ssd", mockGenerator);

        mockSearch = mock(AnalysableAlgorithm.class);
        when(mockSearch.getName()).thenReturn("mock search");

        validMap = WebMapMock.getMinimumValidMap();

        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockSearch);

        mockAnalysisInspector = mock(AnalysisInspector.class);

        app = new App(null, new MockAnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(validMap);
        app.setMapGenerators(mapGenerators);
        app.setAnalysisInspector(mockAnalysisInspector);
    }

    @Test
    public void loopsTest() throws IOException {
        String input = "1\nb\n1\n1\n1\n1\n1\n1\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.run();

        verify(mockGenerator, never()).createMap();
        verify(mockSearch, never()).runSearch();
    }

    @Test
    public void mapGeneratorSetsValidMapTest() {
        when(mockGenerator.createMap()).thenReturn(validMap);
        mapGenerators.put("test", mockGenerator);

        String input = "1\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.setCurrentMap(null);
        app.run();

        verify(mockGenerator, times(1)).createMap();
        assertEquals(validMap, app.getCurrentMap());
    }

    @Test
    public void algorithmIsCalledWhenValidMapSetTest() throws IOException {
        String runAllSearchAndExit = "2\na\nexit";
        InputStream in = new ByteArrayInputStream(runAllSearchAndExit.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.run();

        verify(mockSearch, times(1)).runSearch();
    }

    @Test
    public void algorithmIsNotCalledWhenInvalidMapSetTest() throws IOException {
        validMap.setTileStart(-1, -1);
        String runAllSearchAndExit = "2\na\nexit";
        InputStream in = new ByteArrayInputStream(runAllSearchAndExit.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.setCurrentMap(validMap);
        app.run();

        verify(mockSearch, never()).runSearch();
    }

    @Test
    public void algorithmNotCalledWhenMapNotSetTest() throws IOException {
        String runAllSearchAndExit = "2\na\nexit";
        InputStream in = new ByteArrayInputStream(runAllSearchAndExit.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.setCurrentMap(null);
        app.run();

        verify(mockSearch, never()).runSearch();
    }

    @Test
    public void algorithmExceptionCalledWhenDoesNotExistTest() throws IOException {
        String input = "2\nnot here\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.run();

        verify(mockSearch, never()).runSearch();
    }

    @Test
    public void algorithmIsCalledAndIOExceptionCaughtTest() throws IOException {
        mockSearch = mock(AnalysableAlgorithm.class);
        doThrow(IOException.class)
                .when(mockSearch)
                .runSearch();
        var mockMap = WebMapMock.getMinimumValidMap();
        String input = "2\na\nexit";
        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockSearch);

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockSearch, times(1)).runSearch();
    }

    @Test
    public void statisticsIsCalledTest() throws IOException {
        String input = "3\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app.setScanner(scanner);
        app.run();

        verify(mockAnalysisInspector, times(1)).createAnalysisStatisticByMap(anyMap());
    }


}
