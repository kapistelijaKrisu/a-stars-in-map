package model.structure.custom_structure;

import model.structure.Stack;

/**
 * Last-in-first-out classic Stack implementation.
 * Only head is known and nodes are connected by reference.
 *
 * @param <T> object type to hold.
 */
public class LIFOStack<T> implements Stack<T> {

    private Node<T> head;

    //container for held value
    private static class Node<T> {
        private final T value;
        private Node<T> next;

        private Node(T val) {
            this.value = val;
            this.next = null;
        }

        private T getValue() {
            return value;
        }

        private Node<T> getNext() {
            return next;
        }

        private void setNext(Node<T> nextNode) {
            this.next = nextNode;
        }
    }

    /**
     * Adds given object as a head to stack and connects it with previous head by reference.
     *
     * @param value object to hold (new head of the stack)
     */
    @Override
    public void push(T value) {
        Node<T> newHead = new Node<>(value);
        newHead.setNext(head);
        head = newHead;
    }

    /**
     * Removes current head of the stack and returns it.
     *
     * @return deleted head
     */
    @Override
    public T pop() {
        if (isEmpty()) return null;
        T polledValue = head.getValue();
        head = head.getNext();
        return polledValue;
    }

    /**
     * Returns whether the stack contains any nodes or not
     *
     * @return if head is null true else false
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }


}
