package model.structure.premade_structure;

import model.structure.Heap;

import java.util.PriorityQueue;

public class PremadeHeap<T> implements Heap<T> {
    private PriorityQueue<T> heap;

    public PremadeHeap() {
        this.heap = new PriorityQueue<>();
    }

    @Override
    public T next() {
        return heap.poll();
    }

    @Override
    public void insert(T element) {
        heap.add(element);
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public boolean contains(T element) {
        return heap.contains(element);
    }
}
