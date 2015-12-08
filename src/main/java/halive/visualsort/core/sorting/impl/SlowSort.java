package halive.visualsort.core.sorting.impl;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

public class SlowSort extends SortingAlgorithm {

    public SlowSort() {
        super("Slow Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        slowsort(data, sortingHandler, 0, data.length-1);
    }

    public void slowsort(DataEntry[] entries, SortingHandler handler, int i, int j) {
        if(i >= j) {
            return;
        }
        int middle = (i+j)/2;
        slowsort(entries, handler, i, middle);
        slowsort(entries, handler, middle+1, j);
        if(handler.compare(entries[j].getValue() < entries[middle].getValue())) {
            handler.swap(j,middle);
        }
        slowsort(entries, handler, i, j-1);
    }
}
