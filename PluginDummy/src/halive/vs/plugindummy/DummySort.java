package halive.vs.plugindummy;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

public class DummySort extends SortingAlgorithm {
    public DummySort() {
        super("DummySort", "");
    }

    @Override
    public void doSort(DataEntry[] dataEntries, SortingHandler sortingHandler) {
        for (int i = 0; sortingHandler.compare(i < 1000); i++) {
            for (int j = 0; j < dataEntries.length; j++) {
                for (int k = dataEntries.length-1; k > 0; k--) {
                    sortingHandler.swap(j,k);
                }
            }
        }
    }
}
