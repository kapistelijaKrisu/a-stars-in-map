package model.structure.premade_structure;

import model.structure.Stack;

import java.util.ArrayDeque;

public class PreMadeStack<T> implements Stack<T> {
    private ArrayDeque<T> stack;

    public PreMadeStack() {
        stack = new ArrayDeque<>();
    }

    @Override
    public void push(T element) {
        stack.add(element);
    }

    @Override
    public T pop() {
        return stack.pollLast();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

