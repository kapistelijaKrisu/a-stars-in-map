package model.structure.pre_made_structure;

import model.structure.structure_interface.Stack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PreMadeStackTest {

    @Test
    public void nullTest() {
        Stack<String> queue = new PreMadeStack<>();
        assertNull(queue.pop());
        queue.push("plop");
        queue.pop();
        assertNull(queue.pop());
    }

    @Test
    public void isEmptyTest() {
        Stack<String> queue = new PreMadeStack<>();
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
        Stack<String> queue = new PreMadeStack<>();
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

