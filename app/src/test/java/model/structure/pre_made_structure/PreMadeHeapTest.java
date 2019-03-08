package model.structure.pre_made_structure;

import model.structure.structure_interface.Heap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PreMadeHeapTest {
    @Test
    public void nullTest() {
        Heap<Integer> heap = new PreMadeHeap<>();
        assertNull(heap.next());
        heap.insert(3);
        heap.next();
        assertNull(heap.next());
    }

    @Test
    public void containsTest() {
        Heap<Integer> heap = new PreMadeHeap<>();
        for (int i = 0; i < 4; i++) {
            heap.insert(i * 2);

        }
        heap.insert(0);
        assertTrue(heap.contains(0));
        assertTrue(heap.contains(2));
        assertTrue(heap.contains(4));
        assertTrue(heap.contains(6));

        assertFalse(heap.contains(-1));
        assertFalse(heap.contains(1));
        assertFalse(heap.contains(3));
        assertFalse(heap.contains(5));
        assertFalse(heap.contains(7));
        assertFalse(heap.contains(8));

        heap.next();
        assertTrue(heap.contains(0));
        heap.next();
        assertFalse(heap.contains(0));

    }

    @Test
    public void heapTest() {
        Heap<Integer> heap = new PreMadeHeap<>();
        for (int i = 0; i < 4; i++) {
            heap.insert(i * 2);
        }

        assertEquals(0, heap.next().intValue());
        assertEquals(2, heap.next().intValue());
        assertEquals(4, heap.next().intValue());
        heap.insert(2);
        assertEquals(2, heap.next().intValue());
    }

    @Test
    public void heapLargeTest() {
        Heap<Integer> heap = new PreMadeHeap<>();
        for (int i = 999; i > 0; i--) {
            heap.insert(i);
            assertFalse(heap.isEmpty());
        }

        for (int i = 1; i < 1000; i++) {
            assertEquals(i, heap.next().intValue());
        }
        assertTrue(heap.isEmpty());
    }
}

