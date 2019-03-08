package model.structure.pre_made_structure;

import model.structure.structure_interface.UniqueSet;

import java.util.HashSet;

/**
 * Encapsulates HashSet of java exposing only methods needed by search algorithms
 *
 * @param <T> Type of element to hold
 */

public class PreMadeUniqueSet<T> implements UniqueSet<T> {
    private HashSet<T> hashSet;

    /**
     * creates new HashSet
     */
    public PreMadeUniqueSet() {
        hashSet = new HashSet<>();
    }

    /**
     * Same as HashSet add
     *
     * @param t to add
     */
    @Override
    public void add(T t) {
        hashSet.add(t);
    }

    /**
     * @param t to check if exists in set or not
     * @return true if hashSet contains returns true
     */
    @Override
    public boolean contains(T t) {
        return hashSet.contains(t);
    }
}
