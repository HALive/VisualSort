package halive.visualsort.core.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

public abstract class SortingAlgorithm {

    public final static SortingAlgorithm[] ALGORTIHMS = {new BubbleSort(), new BiDiBubbleSort(), new GnomeSort(),
            new SelectionSort(), new QuickSortR1(),
            new QuickSortR2(), new MergeSort(), new BitonicMergeSort()};

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
    public String toString() {
        return getName();
    }
}
