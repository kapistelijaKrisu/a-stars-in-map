package app;

import mapGenerator.MapGenerator;
import model.WebMap;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import searchAlgorithm.SearchAlgorithm;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.mockito.Mockito.*;


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
        app = new App(true, scanner);
        app.run();
    }

    @Test
    public void loopsTest() {
        String input = "1\n1\n1\n1\n1\n1\n1\n1\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(false, scanner);
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
        app = new App(true, scanner);
        app.setMapGenerators(mapGenerator);
        app.run();

        verify(mockGenerator, times(1)).createMap();
    }

    @Test
    public void algorithmIsCalledWhenValidMapSetTest() {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockGenerator = mock(SearchAlgorithm.class);
        var mockMap = new WebMap();
        mockMap.setMap(new int[2][2]);
        mockMap.setTileAt(new Point());
        mockMap.setTileTarget(new Point());
        algorithmMap.put("test", mockGenerator);
        String input = "2\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner);
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockGenerator, times(1)).runSearch();
    }

    @Test
    public void algorithmIsNotCalledWhenInvalidMapSetTest() {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockGenerator = mock(SearchAlgorithm.class);
        var mockMap = new WebMap();
        algorithmMap.put("test", mockGenerator);
        String input = "2\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner);
        app.setAlgorithmMap(algorithmMap);
        app.setCurrentMap(mockMap);
        app.run();

        verify(mockGenerator, never()).runSearch();
    }

    @Test
    public void algorithmNotCalledWhenMapNotSetTest() {
        var algorithmMap = new HashMap<String, SearchAlgorithm>();
        var mockGenerator = mock(SearchAlgorithm.class);
        algorithmMap.put("test", mockGenerator);
        String input = "2\ntest\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner);
        app.setAlgorithmMap(algorithmMap);
        app.run();

        verify(mockGenerator, never()).runSearch();
    }
}
