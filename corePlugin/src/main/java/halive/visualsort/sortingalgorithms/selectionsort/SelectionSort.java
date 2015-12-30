/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.selectionsort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class Implements Selection Sort
 */
public class SelectionSort extends SortingAlgorithm {

    public SelectionSort() {
        super("Selection sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler h) {
        for (int i = 0; h.compare(i < data.length); i++) {
            int mpos = SelectionSortUtils.getMinimumPos(data, i, data.length - 1, h);
            h.swap(i, mpos);
            data[i].setRenderColor(Color.green);
        }
    }

    @Override
    public String getCategory() {
        return "SelectionSort";
    }
}
