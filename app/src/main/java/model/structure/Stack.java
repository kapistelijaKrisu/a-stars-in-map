package model.structure;

public interface Stack<T> {

    void push(T element);
    T pop();
    boolean isEmpty();

}

