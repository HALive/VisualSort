/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.insertion;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

/**
 * This Class Implements the Basic InsertionSort Algorithm
 */
public class InsertionSort extends SortingAlgorithm {

    public InsertionSort() {
        super("Insertion Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        InsertionSortUtils.insertionSort(data, sortingHandler, l, 1, r);
    }

    @Override
    public String getCategory() {
        return "InsertionSort";
    }
}
