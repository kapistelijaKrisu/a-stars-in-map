package model.structure.structure_interface;

/**
 * Stack interface. Implements only methods needed by search algorithms
 *
 * @param <T> type of object to contain
 */
public interface Stack<T> {

    /**
     * Adds element to stack
     *
     * @param element to add
     */
    void push(T element);

    /**
     * Deletes last added element.
     *
     * @return Deleted element or null if none
     */
    T pop();

    boolean isEmpty();

}

