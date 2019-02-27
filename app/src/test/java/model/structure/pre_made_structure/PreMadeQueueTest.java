package model.structure.pre_made_structure;


import model.structure.Queue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PreMadeQueueTest {

    @Test
    public void nullTest() {
        Queue<String> queue = new PreMadeQueue<>();
        assertNull(queue.dequeue());
        queue.enqueue("plop");
        queue.dequeue();
        assertNull(queue.dequeue());
    }

    @Test
    public void isEmptyTest() {
        Queue<String> queue = new PreMadeQueue<>();
        assertTrue(queue.isEmpty());
        queue.enqueue("plop");
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
        queue.enqueue("plop");
        queue.enqueue("plop2");
        assertFalse(queue.isEmpty());
    }

    @Test
    public void queueTest() {
        Queue<String> queue = new PreMadeQueue<>();
        assertNull(queue.dequeue());

        queue.enqueue("plop");
        assertEquals("plop", queue.dequeue());

        assertNull(queue.dequeue());

        queue.enqueue("plop1");
        queue.enqueue("plop2");
        assertEquals("plop1", queue.dequeue());
        queue.enqueue("plop3");
        assertEquals("plop2", queue.dequeue());
        assertEquals("plop3", queue.dequeue());
        assertNull(queue.dequeue());
    }
}
