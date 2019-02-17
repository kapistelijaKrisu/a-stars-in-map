package app;

import map_generator.MapGenerator;
import model.web.WebMap;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import file_operations.analysis_writer.AnalysisWriter;
import search_algorithm.SearchAlgorithm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import mock.WebMapMock;

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
        mapGenerators.put("test", mockGenerator);
        var mockSearch = mock(SearchAlgorithm.class);
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        algorithmMap.put("test", mockSearch);

        String input = "1\nb\n1\n1\n1\n1\n1\n1\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(false, scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setMapGenerators(mapGenerators);
        app.run();

        verify(mockGenerator, never()).createMap();
        verify(mockSearch, never()).runSearch();
    }

    @Test
    public void mapGeneratorIsCalledTest() {
        var mapGenerator = new HashMap<String, MapGenerator>();
        var mockGenerator = mock(MapGenerator.class);
        mapGenerator.put("test", mockGenerator);
        String input = "1\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner, new AnalysisWriter());
        app.setMapGenerators(mapGenerator);
        app.run();

        verify(mockGenerator, times(1)).createMap();
    }

    @Test
    public void algorithmIsCalledWhenValidMapSetTest() throws IOException {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockSearch = mock(SearchAlgorithm.class);
        var mockMap = WebMapMock.getMinimumValidMap();
        algorithmMap.put("test", mockSearch);
        String input = "2\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockSearch, times(1)).runSearch();
    }

    @Test
    public void algorithmIsNotCalledWhenInvalidMapSetTest() throws IOException {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockGenerator = mock(SearchAlgorithm.class);
        var mockMap = new WebMap();
        algorithmMap.put("test", mockGenerator);
        String input = "2\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockGenerator, never()).runSearch();
    }

    @Test
    public void algorithmNotCalledWhenMapNotSetTest() throws IOException {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockGenerator = mock(SearchAlgorithm.class);
        algorithmMap.put("test", mockGenerator);
        String input = "2\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.run();

        verify(mockGenerator, never()).runSearch();
    }

    @Test
    public void algorithmExceptionCalledWhenDoesNotExistTest() throws IOException {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockGenerator = mock(SearchAlgorithm.class);
        var mockMap = WebMapMock.getMinimumValidMap();
        algorithmMap.put("test", mockGenerator);
        String input = "2\nnot here\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockGenerator, never()).runSearch();
    }

    @Test
    public void algorithmIsCalledAndIOExceptionCaughtTest() throws IOException {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockGenerator = mock(SearchAlgorithm.class);
        doThrow(IOException.class)
                .when(mockGenerator)
                .runSearch();
        var mockMap = WebMapMock.getMinimumValidMap();
        algorithmMap.put("test", mockGenerator);
        String input = "2\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner, new AnalysisWriter());
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockGenerator, times(1)).runSearch();
    }


}
