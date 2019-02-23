package model.structure.custom_structure;

import model.structure.UniqueSet;

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
