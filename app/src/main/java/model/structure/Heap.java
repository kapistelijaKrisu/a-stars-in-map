package model.structure;

public interface Heap <T>{

    T next();
    void insert(T element);
    boolean isEmpty();
    boolean contains(T element);

}

