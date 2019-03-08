package model.structure.structure_interface;

/**
 * Queue interface. Implements only methods needed by search algorithms
 *
 * @param <T> type of object to contain
 */
public interface Queue<T> {

    /**
     * Adds element to queue
     *
     * @param element to add
     */
    void enqueue(T element);

    /**
     * Deletes oldest element in list
     *
     * @return Deleted element or null if none
     */
    T dequeue();

    /**
     * @return whether heap is empty or not
     */
    boolean isEmpty();

}


