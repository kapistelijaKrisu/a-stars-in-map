package searchAlgorithm;

import model.WebMap;

public abstract class SearchAlgorithm {

    protected abstract void searchAlgorithm(WebMap map);

    //todo write to a file, add filepath to constructor
    // todo announce completion details...where results written etc
    public void runSearch(WebMap map) {
        searchAlgorithm(map);
    }
}
