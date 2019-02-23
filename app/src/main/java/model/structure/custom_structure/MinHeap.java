package model.structure.custom_structure;

import model.structure.Heap;

import java.util.Arrays;

/**
 * Classic min-heap implementation. Holds values in a heaped array.
 * @param <T> type of objects to hold in ascending order.
 */
public class MinHeap<T extends Comparable<T>> implements Heap<T> {

    private Comparable[] heap;
    private int size;

    /**
     * Creates a new Heap with a size of 15. index 0 is not used to be able to get left and right children with multiplication.
     */
    public MinHeap() {
        size = 1;
        heap = new Comparable[16];
    }

    @Override
    public boolean isEmpty() {
        return size == 1;
    }

    /**
     * Deletes the smallest object by compareTo method. if array size drops below half while half is over 16 it will create new array half the size of previous one.
     * @return the deleted object
     */
    @Override
    public T next() {
        if (size == 1) return null;
        T popped = (T) heap[1];
        heap[1] = heap[size - 1];
        size--;
        minheapify(1);
        if (maxSize() / 2 > Math.max(size, 16)) heap = Arrays.copyOf(heap, heap.length / 2);
        return popped;
    }

    /**
     * Adds given element.
     * If it won't fit in array then creates new array double the size of previous one.
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
     * @param element searched element
     * @return true if equals method of objects return true. false if such object not found in store.
     */

    @Override
    public boolean contains(T element) {
        for (int i = 1; i < size; i++) {
            if (heap[i].equals(element)) return true;
        }
        return false;
    }

    private void minheapify(int pos) {

        var leftPos = leftChild(pos);
        var rightPos = rightChild(pos);

        if (rightPos <= size) {

            var smallestPos = rightPos;
            if (heap[leftPos].compareTo(heap[rightPos]) < 0) smallestPos = leftPos;

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
