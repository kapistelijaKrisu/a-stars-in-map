package model.structure.custom_structure;

import model.structure.UniqueSet;

/**
 * Set implementation. Only methods needed by search algorithms are implemented.
 * Classic set implementation. Holds an array of lists that holds objects.
 * Size of array will adjust with amount of objects it holds
 *
 * @param <T> Type of object to contain
 */
public class CustomHashSetDynamicSize<T> implements UniqueSet<T> {

    private static final Integer INITIAL_CAPACITY = 16;
    private static final Double INITIAL_USED_CELL_PERCENTAGE_MAX_LIMIT = 0.7;

    private Node<T>[] hashCodeQueues;

    private int cellsInUse;
    private final double maxFillPercentage;

    private static class Node<T> {
        private final T value;
        private Node<T> after;


        private Node(T value) {
            this.value = value;
            this.after = null;

        }
    }

    /**
     *
     * @param capacity percentage between to aim the array size at to handle how big collision chains can get
     */
    public CustomHashSetDynamicSize(final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity should be over 0");
        }
        this.hashCodeQueues = new Node[capacity];
        this.cellsInUse = 0;
        this.maxFillPercentage = INITIAL_USED_CELL_PERCENTAGE_MAX_LIMIT;
    }

    public CustomHashSetDynamicSize() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Adds param to set if doesn't exist.
     * If threshold of lists having a non-null object in array hits value that was given in constructor then it will double the array size.
     * @param t to add to heap
     */
    public void add(T t) {
        if ((double) cellsInUse / hashCodeQueues.length > maxFillPercentage) {
            expandSetArray();
        }
        int index = t.hashCode() % hashCodeQueues.length;

        Node hashCodeCell = hashCodeQueues[index];

        Node<T> newNode = new Node<>(t);

        if (hashCodeCell == null) {
            hashCodeQueues[index] = newNode;
            cellsInUse++;
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

    private void expandSetArray() {
        CustomHashSetDynamicSize<T> newHashSet = new CustomHashSetDynamicSize<T>(hashCodeQueues.length * 2);

        for (Node<T> listEntry : hashCodeQueues) {
            Node<T> listHead = listEntry;
            while (listHead != null) {
                newHashSet.add(listHead.value);
                listHead = listHead.after;
            }
        }

        this.hashCodeQueues = newHashSet.hashCodeQueues;
        this.cellsInUse = newHashSet.cellsInUse;
    }

    /**
     *
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
