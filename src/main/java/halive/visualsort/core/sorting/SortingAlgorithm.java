package halive.visualsort.core.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.INamable;
import halive.visualsort.core.SortingHandler;

public abstract class SortingAlgorithm implements INamable{

    public static SortingAlgorithm[] ALGORTIHMS;

    private String name;
    private String description;

    public SortingAlgorithm(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void doSort(DataEntry[] data, SortingHandler sortingHandler);

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public void addToName(String s) {
        name+=" ("+s+")";
    }

    @Override
    public String toString() {
        return getName();
    }
}
