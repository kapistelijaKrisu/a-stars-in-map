package model.structure.custom_structure;

import model.structure.Queue;

/**
 * First-in-forst-out queue
 * Classis queue implementation. Only head and tail is known and nodes are connected by reference.
 * @param <T> Type of object to contain
 */
public class FIFOQueue<T> implements Queue<T> {

    private Node<T> head;
    private Node<T> tail;

    //container for held value
    private static class Node<T> {
        private final T value;
        private Node<T> before;
        private Node<T> after;


        private Node(T value) {
            this.value = value;
            this.before = null;
            this.after = null;

        }

        private T getValue() {
            return value;
        }

        private void setAfter(Node<T> next) {
            this.after = next;
        }

        private Node<T> getAfter() {
            return after;
        }
    }

    /**
     *
     * @param value object to enqueue to the tail of the queue
     */
    @Override
    public void enqueue(T value) {
        Node<T> oldLast = tail;
        tail = new Node<>(value);
        if (isEmpty()) head = tail;
        else oldLast.setAfter(tail);
    }

    /**
     * Deletes head (oldest) object
     * @return deleted head (oldest object in list)
     */
    @Override
    public T dequeue() {
        if (isEmpty()) return null;
        T next = head.getValue();

        head = head.getAfter();

        if (isEmpty()) tail = null;
        return next;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
