package model.structure.custom_structure;

import model.structure.structure_interface.UniqueSet;

/**
 * Set implementation. Only methods needed by search algorithms are implemented.
 * Classic set implementation. Holds an array of lists that holds objects. array is set size.
 *
 * @param <T> Type of object to contain
 */
public class CustomHashSet<T> implements UniqueSet<T> {

    private Node<T>[] hashCodeQueues;

    private static class Node<T> {
        private final T value;
        private Node<T> after;

        private Node(T value) {
            this.value = value;
            this.after = null;
        }
    }

    /**
     * @param capacity size of the table where objects will be stored
     * @throws IllegalArgumentException if capacity is 0 or below
     */
    public CustomHashSet(final int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity should be over 0");
        }
        this.hashCodeQueues = new Node[capacity];
    }

    /**
     * Adds param to set if doesn't exist.
     *
     * @param t to add to heap
     */
    public void add(T t) {
        int index = t.hashCode() % hashCodeQueues.length;

        Node hashCodeCell = hashCodeQueues[index];

        Node<T> newNode = new Node<>(t);

        if (hashCodeCell == null) {
            hashCodeQueues[index] = newNode;
            return;
        }

        while (hashCodeCell != null) {
            if (hashCodeCell.value.equals(t)) {
                return;
            } else if (hashCodeCell.after == null) {
                hashCodeCell.after = newNode;
            }
            hashCodeCell = hashCodeCell.after;
        }
    }

    /**
     * @param t to check if exists in set or not
     * @return true if t exists in set
     */
    public boolean contains(T t) {
        int index = t.hashCode() % hashCodeQueues.length;
        Node hashCodeCell = hashCodeQueues[index];

        if (hashCodeCell == null) {
            return false;
        }

        while (hashCodeCell != null) {
            if (hashCodeCell.value.equals(t)) {
                return true;
            }
            hashCodeCell = hashCodeCell.after;
        }
        return false;
    }
}
