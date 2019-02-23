package model.structure.premade_structure;

import model.structure.Queue;

import java.util.ArrayDeque;

public class PremadeQueue<T> implements Queue<T> {
    private ArrayDeque<T> queue;

    public PremadeQueue() {
        queue = new ArrayDeque<>();
    }

    @Override
    public void enqueue(T element) {
        queue.add(element);
    }

    @Override
    public T dequeue() {
        return queue.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
