package model.structure.structure_interface;

/**
 * Heap interface. Implements only methods needed by search algorithms
 *
 * @param <T> type of object to contain
 */
public interface Heap<T> {

    /**
     * Deletes the 1st element
     *
     * @return Deleted element or null if none
     */
    T next();

    /**
     * Adds Element
     *
     * @param element to add
     */
    void insert(T element);

    /**
     * @return whether heap is empty or not
     */
    boolean isEmpty();

    /**
     * @param element that is checked with equals if exists in heap
     * @return true if exists in heap and false otherwise
     */
    boolean contains(T element);

}

