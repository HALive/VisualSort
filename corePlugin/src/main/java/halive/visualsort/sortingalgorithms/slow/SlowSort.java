/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.slow;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class Implements the Very Slow Sorting Algorihm Slow Sort.
 */
public class SlowSort extends SortingAlgorithm {

    public SlowSort() {
        super("Slow Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        slowsort(data, sortingHandler, 0, data.length - 1);
    }

    public void slowsort(DataEntry[] entries, SortingHandler handler, int i, int j) {
        if (i >= j) {
            return;
        }
        int middle = (i + j) / 2;
        slowsort(entries, handler, i, middle);
        slowsort(entries, handler, middle + 1, j);
        if (handler.compare(entries[j].getValue() < entries[middle].getValue())) {
            handler.swap(j, middle);
            entries[j].setPrimaryColor(Color.black);
            entries[middle].setPrimaryColor(Color.black);
        }
        slowsort(entries, handler, i, j - 1);
    }

    @Override
    public String getCategory() {
        return "Slow Algorithms";
    }
}
