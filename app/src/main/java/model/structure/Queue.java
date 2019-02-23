package model.structure;

public interface Queue<T> {

    void enqueue(T element);
    T dequeue();
    boolean isEmpty();

}


