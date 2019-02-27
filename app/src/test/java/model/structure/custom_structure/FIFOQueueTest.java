package model.structure.custom_structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FIFOQueueTest {

    @Test
    public void nullTest() {
        FIFOQueue<String> queue = new FIFOQueue<>();
        assertNull(queue.dequeue());
        queue.enqueue("plop");
        queue.dequeue();
        assertNull(queue.dequeue());
    }

    @Test
    public void isEmptyTest() {
        FIFOQueue<String> queue = new FIFOQueue<>();
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
        FIFOQueue<String> queue = new FIFOQueue<>();
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
