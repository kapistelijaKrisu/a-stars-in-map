package mapGenerator;

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
        var tiles = generator.createMap();

        assertEquals(new Point(1,2), tiles.getTileAt());
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
