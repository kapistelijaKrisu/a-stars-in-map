package model.structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LIFOStackTest {

    @Test
    public void nullTest() {
        LIFOStack<String> queue = new LIFOStack<>();
        assertNull(queue.pop());
        queue.push("plop");
        queue.pop();
        assertNull(queue.pop());
    }

    @Test
    public void isEmptyTest() {
        LIFOStack<String> queue = new LIFOStack<>();
        assertTrue(queue.isEmpty());
        queue.push("plop");
        assertFalse(queue.isEmpty());
        queue.pop();
        assertTrue(queue.isEmpty());
        queue.push("plop");
        queue.push("plop2");
        assertFalse(queue.isEmpty());
    }

    @Test
    public void stackTest() {
        LIFOStack<String> queue = new LIFOStack<>();
        assertNull(queue.pop());

        queue.push("plop");
        assertEquals("plop", queue.pop());

        assertNull(queue.pop());

        queue.push("plop1");
        queue.push("plop2");
        assertEquals("plop2", queue.pop());
        queue.push("plop3");
        assertEquals("plop3", queue.pop());
        assertEquals("plop1", queue.pop());
        assertNull(queue.pop());
    }
}

