/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

public class InPlaceMergeSort extends SortingAlgorithm {

    public InPlaceMergeSort() {
        super("In-Place MergeSort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        mergeSort(l, r - 1, data, sortingHandler);
    }

    public void mergeSort(int l, int r, DataEntry[] data, SortingHandler h) {
        int diff = r - l;
        if (diff == 0) {
            return;
        } else if (diff == 1) {
            if (h.compare(data[l].getValue() > data[r].getValue())) {
                h.swap(l, r);
            }
        } else {
            int m = ((r - l) / 2) + l;
            mergeSort(l, m, data, h);
            mergeSort(m + 1, r, data, h);
            MergeUtils.mergeInPlace(l, r, data, h);
        }
    }

    @Override
    public String getCategory() {
        return "MergeSort";
    }
}
