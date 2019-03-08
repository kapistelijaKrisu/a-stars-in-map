package model.structure.pre_made_structure;

import model.structure.structure_interface.Stack;

import java.util.ArrayDeque;

/**
 * Encapsulates ArrayDeque of java exposing only stack methods needed by search algorithms
 *
 * @param <T> Type of element to hold
 */

public class PreMadeStack<T> implements Stack<T> {
    private ArrayDeque<T> stack;

    /**
     * Creates ArrayDeque
     */
    public PreMadeStack() {
        stack = new ArrayDeque<>();
    }

    /**
     * Calls ArrayDeque.add
     *
     * @param element to add
     */
    @Override
    public void push(T element) {
        stack.add(element);
    }

    /**
     * ArrayDeque.pollLast or null if empty
     *
     * @return polled element
     */
    @Override
    public T pop() {
        return stack.isEmpty() ? null : stack.pollLast();
    }

    /**
     * @return ArrayDeque.isEmpty
     */
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

