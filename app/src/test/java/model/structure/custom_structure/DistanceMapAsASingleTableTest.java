package model.structure.custom_structure;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DistanceMapAsASingleTableTest {

    @Test
    public void constructorTest() {
        int height = 5;
        int weight = 6;
        DistanceMapAsASingleTable table = new DistanceMapAsASingleTable(5, 6);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                assertEquals((double) Integer.MAX_VALUE, table.getDistance(new Point(j, i)));
            }
        }
    }

    @Test
    public void setterAndGetterTest() {
        DistanceMapAsASingleTable table = new DistanceMapAsASingleTable(5, 6);

        table.setDistance(new Point(2, 1), 2);
        assertEquals(2, table.getDistance(new Point(2, 1 )));

        table.setDistance(new Point(2, 1), 4);
        assertEquals(4, table.getDistance(new Point(2, 1 )));

        // only checks if throws error, in case p.y * width + p.x is positive of negative and positive value it won't be checked
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> table.setDistance(new Point(22, 33), 2));

    }
}
