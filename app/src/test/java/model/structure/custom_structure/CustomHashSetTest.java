package model.structure.custom_structure;

import model.structure.structure_interface.UniqueSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomHashSetTest {

    @Test
    public void notFoundTest() {
        UniqueSet<Integer> set = new CustomHashSet<>(2);
        assertFalse(set.contains(0));
        set.add(1);
        assertFalse(set.contains(0));

        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);

        assertFalse(set.contains(0));
    }

    @Test
    public void containsWithCollisionsTest() {
        UniqueSet<Integer> set = new CustomHashSet<>(2);
        set.add(0);
        assertTrue(set.contains(0));

        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);

        assertTrue(set.contains(1));
    }

    @Test
    public void containsWithoutCollisionsTest() {
        UniqueSet<Integer> set = new CustomHashSet<>(16);
        set.add(0);
        assertTrue(set.contains(0));

        set.add(1);

        assertTrue(set.contains(1));
    }

    @Test
    public void throwsErrorWithIllegalConstructorTest() {

        Throwable exceptionZero = assertThrows(IllegalArgumentException.class, () -> new CustomHashSet<>(0));
        assertEquals("capacity should be over 0", exceptionZero.getMessage());

        Throwable exceptionNegative = assertThrows(IllegalArgumentException.class, () -> new CustomHashSet<>(-1));
        assertEquals("capacity should be over 0", exceptionNegative.getMessage());
    }
}
