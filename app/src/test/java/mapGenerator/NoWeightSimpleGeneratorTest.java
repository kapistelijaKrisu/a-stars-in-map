package mapGenerator;

import model.WebMap;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoWeightSimpleGeneratorTest {

    private NoWeightSimpleGenerator generator;
    private Scanner scanner;

    @Test
    public void generateMapTest() {
        String input = "9\n8\n1\n2\n6\n7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner =  new Scanner(System.in);
        generator = new NoWeightSimpleGenerator(scanner);
        assertValues(generator.createMap());
    }

    @Test
    public void generateMapHandlesStartXFailTest() {
        String input = "9\n8\n9\n2\n6\n7\n9\n8\n1\n2\n6\n7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner =  new Scanner(System.in);
        generator = new NoWeightSimpleGenerator(scanner);
        assertValues(generator.createMap());
    }

    @Test
    public void generateMapHandlesStartYFailTest() {
        String input = "9\n8\n1\n8\n6\n7\n9\n8\n1\n2\n6\n7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner =  new Scanner(System.in);
        generator = new NoWeightSimpleGenerator(scanner);
        assertValues(generator.createMap());
    }

    @Test
    public void generateMapHandlesTargetXFailTest() {
        String input = "9\n8\n1\n2\n9\n7\n9\n8\n1\n2\n6\n7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner =  new Scanner(System.in);
        generator = new NoWeightSimpleGenerator(scanner);
        assertValues(generator.createMap());
    }

    @Test
    public void generateMapHandlesTargetYFailTest() {
        String input = "9\n8\n1\n2\n6\n8\n9\n8\n1\n2\n6\n7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner =  new Scanner(System.in);
        generator = new NoWeightSimpleGenerator(scanner);
        assertValues(generator.createMap());
    }

    @Test
    public void generateMapHandlesZeroFailTest() {
        String input = "0\n0\n0\n0\n0\n0\n9\n8\n1\n2\n6\n7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner =  new Scanner(System.in);
        generator = new NoWeightSimpleGenerator(scanner);
        assertValues(generator.createMap());
    }
    @Test
    public void generateMapHandlesNegativeValuesFailTest() {
        String input = "11\n11\n-1\n-1\n-1\n-1\n9\n8\n1\n2\n6\n7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner =  new Scanner(System.in);
        generator = new NoWeightSimpleGenerator(scanner);
        assertValues(generator.createMap());
    }

    private void assertValues(WebMap tiles) {
        assertEquals(new Point(1,2), tiles.getTileStart());
        assertEquals(new Point(6,7), tiles.getTileTarget());
        assertEquals(9, tiles.getMap()[0].length);
        assertEquals(8, tiles.getMap().length);

        for (int i = 0; i < tiles.getMap().length; i++) {
            for (int j = 0; j < tiles.getMap()[0].length; j++) {
                assertEquals(1, tiles.getMap()[i][j]);
            }
        }
    }
}
