package model.structure.custom_structure;

import model.structure.Heap;

import java.util.Arrays;

/**
 * Classic min-heap implementation. Holds values in a heaped array.
 *
 * @param <T> type of objects to hold in ascending order.
 */
public class MinHeap<T extends Comparable<T>> implements Heap<T> {

    private static final int MIN_ARRAY_SIZE = 16;
    private static final int HEAP_ZERO = 1;
    private Comparable[] heap;
    private int size;

    /**
     * Creates a new Heap with a size of 15. index 0 is not used to be able to get left and right children with multiplication.
     */
    public MinHeap() {
        size = HEAP_ZERO;
        heap = new Comparable[MIN_ARRAY_SIZE];
    }

    @Override
    public boolean isEmpty() {
        return size == HEAP_ZERO;
    }

    /**
     * Deletes the smallest object by compareTo method. if array size drops below half while half is over MIN_ARRAY_SIZE it will create new array half the size of previous one.
     *
     * @return the deleted object
     */
    @Override
    public T next() {
        if (size == HEAP_ZERO) return null;
        T popped = (T) heap[HEAP_ZERO];
        heap[1] = heap[size - 1];
        size--;
        minheapify(HEAP_ZERO);
        if (maxSize() / 2 > Math.max(size, MIN_ARRAY_SIZE)) heap = Arrays.copyOf(heap, heap.length / 2);
        return popped;
    }

    /**
     * Adds given element.
     * If it won't fit in array then creates new array double the size of previous one.
     *
     * @param element object to store that implements Comparable to itself
     */

    @Override
    public void insert(T element) {
        if (size == maxSize()) heap = Arrays.copyOf(heap, heap.length * 2);
        heap[size] = element;
        int current = size;

        while (heap[current].compareTo(heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
        size++;
    }

    /**
     * Loops over array searching for the element.
     *
     * @param element searched element
     * @return true if equals method of objects return true. false if such object not found in store.
     */

    @Override
    public boolean contains(T element) {
        for (int i = HEAP_ZERO; i < size; i++) {
            if (heap[i].equals(element)) return true;
        }
        return false;
    }

    private void minheapify(int pos) {

        var leftPos = leftChild(pos);
        var rightPos = rightChild(pos);

        if (rightPos <= size) {

            var smallestPos = leftPos;
            if (heap[rightPos].compareTo(heap[leftPos]) < 0) smallestPos = rightPos;

            if (heap[pos].compareTo(heap[smallestPos]) > 0) {
                swap(pos, smallestPos);
                minheapify(smallestPos);
            }
        } else if (leftPos == size && heap[pos].compareTo(heap[leftPos]) > 0) {
            swap(pos, leftPos);
        }
    }

    private void swap(int position, int otherPosition) {
        Comparable tmp = heap[position];
        heap[position] = heap[otherPosition];
        heap[otherPosition] = tmp;
    }

    private int maxSize() {
        return heap.length - 2;
    }

    // accessing heap nodes
    private int parent(int pos) {
        return Math.max(1, pos / 2);
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }


}
