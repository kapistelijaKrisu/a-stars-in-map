package app;

import file_operations.analysis_writer.AnalysisWriter;
import map_generator.MapGenerator;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.Rule;
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

    @Test
    public void loopsTest() throws IOException {
        var mapGenerators = new HashMap<String, MapGenerator>();
        var mockGenerator = mock(MapGenerator.class);
        mapGenerators.put("ssd", mockGenerator);
        var mockSearch = mock(AnalysableAlgorithm.class);
        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockSearch);

        String input = "1\nb\n1\n1\n1\n1\n1\n1\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app = new App(scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setMapGenerators(mapGenerators);
        app.run();

        verify(mockGenerator, never()).createMap();
        verify(mockSearch, never()).runSearch();
    }

    @Test
    public void mapGeneratorSetsValidMapTest() {
        var mapGenerator = new HashMap<String, MapGenerator>();
        var mockGenerator = mock(MapGenerator.class);
        var generatedMockMap = WebMapMock.getMinimumValidMap();
        when(mockGenerator.createMap()).thenReturn(generatedMockMap);
        mapGenerator.put("test", mockGenerator);
        String input = "1\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app = new App(scanner, new AnalysisWriter());
        app.setMapGenerators(mapGenerator);
        app.run();

        verify(mockGenerator, times(1)).createMap();
        assertEquals(generatedMockMap, app.getCurrentMap());
    }

    @Test
    public void algorithmIsCalledWhenValidMapSetTest() throws IOException {
        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        var mockAlgorithm = mock(AnalysableAlgorithm.class);
        var mockMap = WebMapMock.getMinimumValidMap();
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockAlgorithm);
        String input = "2\nssd\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app = new App(scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockAlgorithm, times(1)).runSearch();
    }

    @Test
    public void algorithmIsNotCalledWhenInvalidMapSetTest() throws IOException {
        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        var mockAlgorithm = mock(AnalysableAlgorithm.class);
        var mockMap = new WebMap();
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockAlgorithm);
        String input = "2\nssd\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app = new App(scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockAlgorithm, never()).runSearch();
    }

    @Test
    public void algorithmNotCalledWhenMapNotSetTest() throws IOException {
        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        var mockAlgorithm = mock(AnalysableAlgorithm.class);
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockAlgorithm);
        String input = "2\nssd\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app = new App(scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.run();

        verify(mockAlgorithm, never()).runSearch();
    }

    @Test
    public void algorithmExceptionCalledWhenDoesNotExistTest() throws IOException {
        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        var mockAlgorithm = mock(AnalysableAlgorithm.class);
        var mockMap = WebMapMock.getMinimumValidMap();
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockAlgorithm);
        String input = "2\nnot here\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app = new App(scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockAlgorithm, never()).runSearch();
    }

    @Test
    public void algorithmIsCalledAndIOExceptionCaughtTest() throws IOException {
        var algorithmMap = new HashMap<AlgorithmCodeKey, AnalysableAlgorithm>();
        var mockAlgorithm = mock(AnalysableAlgorithm.class);
        doThrow(IOException.class)
                .when(mockAlgorithm)
                .runSearch();
        var mockMap = WebMapMock.getMinimumValidMap();
        algorithmMap.put(AlgorithmCodeKey.get("ssd"), mockAlgorithm);
        String input = "2\nssd\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner = new Scanner(System.in);
        app = new App(scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockAlgorithm, times(1)).runSearch();
    }


}
