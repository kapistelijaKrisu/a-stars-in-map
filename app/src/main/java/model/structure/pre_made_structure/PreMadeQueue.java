package model.structure.pre_made_structure;

import model.structure.structure_interface.Queue;

import java.util.ArrayDeque;

/**
 * Encapsulates ArrayDeque of java exposing only queue methods needed by search algorithms
 *
 * @param <T> Type of element to hold
 */

public class PreMadeQueue<T> implements Queue<T> {
    private ArrayDeque<T> queue;

    public PreMadeQueue() {
        queue = new ArrayDeque<>();
    }

    @Override
    public void enqueue(T element) {
        queue.add(element);
    }

    /**
     * ArrayDeque.pollFirst or null if empty
     *
     * @return polled element
     */
    @Override
    public T dequeue() {
        return queue.isEmpty() ? null : queue.pollFirst();
    }

    /**
     * @return ArrayDeque.isEmpty
     */
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
