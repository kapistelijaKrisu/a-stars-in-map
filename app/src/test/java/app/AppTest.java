package app;

import mapGenerator.MapGenerator;
import model.WebMap;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import IOoperations.analysisWriter.AnalysisWriter;
import searchAlgorithm.SearchAlgorithm;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;

public class AppTest {

    @Rule // in case app does not exit run method
    public Timeout globalTimeout = Timeout.seconds(1);

    private Scanner scanner;

    private App app;

    @Test
    public void exitsCorrectlyTest() {
        String input = "not a command";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner, new AnalysisWriter());
        app.run();
    }

    @Test
    public void loopsTest() {
        String input = "1\n1\n1\n1\n1\n1\n1\n1\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(false, scanner, new AnalysisWriter());
        app.run();
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
        var mockGenerator = mock(SearchAlgorithm.class);
        var mockMap = createValidMap();
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
        var mockMap = createValidMap();
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
        var mockMap = createValidMap();
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

    private WebMap createValidMap() {
        var mockMap = new WebMap();
        mockMap.setMap(new int[2][2]);
        mockMap.setTileAt(new Point(0,0));
        mockMap.setTileTarget(new Point(1,1));
        mockMap.getMap()[1][1] = 1;
        assertTrue(mockMap.isValid());
        return mockMap;
    }
}
