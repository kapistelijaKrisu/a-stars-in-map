package model.structure.pre_made_structure;

import model.structure.Heap;

import java.util.PriorityQueue;

/**
 * Encapsulates PriorityQueue of java exposing only heap methods needed by search algorithms
 * @param <T> Type of element to hold
 */

public class PreMadeHeap<T> implements Heap<T> {
    private PriorityQueue<T> heap;

    /**
     * Creates PriorityQueue
     */
    public PreMadeHeap() {
        this.heap = new PriorityQueue<>();
    }

    /**
     *
     * @return PriorityQueue.poll result
     */
    @Override
    public T next() {
        return heap.poll();
    }

    /**
     * Calls PriorityQueue.add
     * @param element to add
     */
    @Override
    public void insert(T element) {
        heap.add(element);
    }

    /**
     *
     * @return PriorityQueue.isEmpty result
     */
    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     *
     * @param element that is checked with equals if exists in heap
     * @return PriorityQueue.contains result with param
     */
    @Override
    public boolean contains(T element) {
        return heap.contains(element);
    }
}
