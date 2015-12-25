/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.slow;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * Implements StoogeSort
 */
public class StoogeSort extends SortingAlgorithm {

    public StoogeSort() {
        super("Stooge Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        stoogesort(data, sortingHandler, 0, data.length);
    }

    public void stoogesort(DataEntry[] d, SortingHandler h, int i, int j) {
        if (h.compare(d[i].getValue() > d[j - 1].getValue())) {
            d[i].setRenderColor(Color.DARK_GRAY);
            h.swap(i, j - 1);
        }
        int len = j - i;
        if (len > 2) {
            int k = len / 3;
            //Sort the first two thirds
            stoogesort(d, h, i, j - k);
            //Sort the Last two Thirds
            stoogesort(d, h, i + k, j);
            //Sort the first two thirds
            stoogesort(d, h, i, j - k);
        }
    }

    @Override
    public String getCategory() {
        return "Slow Algorithms";
    }
}
