package model.structure.premade_structure;

import model.structure.UniqueSet;

import java.util.HashSet;

public class PremadeUniqueSet<T> implements UniqueSet<T> {
    private HashSet<T> hashSet;

    public PremadeUniqueSet() {
        hashSet = new HashSet<>();
    }
    @Override
    public void add(T t) {
    hashSet.add(t);
    }

    @Override
    public boolean contains(T t) {
        return hashSet.contains(t);
    }
}
