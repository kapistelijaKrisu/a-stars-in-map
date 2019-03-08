package model.structure.structure_interface;

/**
 * Set interface that contains only one element with same equals result. Implements only methods needed by search algorithms.
 *
 * @param <T> type of object to contain
 */
public interface UniqueSet<T> {

    /**
     * Adds t to list if such element does not already exist inside set
     *
     * @param t to add
     */
    void add(T t);

    /**
     * Checks if such element exists by equals method
     *
     * @param t to check if exists in set or not
     * @return true if exists. false otherwise.
     */
    boolean contains(T t);
}
