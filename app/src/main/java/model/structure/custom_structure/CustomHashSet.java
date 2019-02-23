package model.structure.custom_structure;

import model.structure.UniqueSet;

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

    public CustomHashSet(final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity should be over 0");
        }
        this.hashCodeQueues = new Node[capacity];
    }

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
