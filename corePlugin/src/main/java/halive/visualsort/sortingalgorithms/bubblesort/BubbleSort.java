/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.bubblesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class implements Bubble sort.
 */
public class BubbleSort extends SortingAlgorithm {

    public BubbleSort() {
        super("Bubble Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler h, int l, int r) {
        int end = r - 1;
        boolean swapped = false;
        do {
            swapped = false;
            for (int i = l; h.compare(i < end); i++) {
                data[i].setTemporaryColor(Color.RED);
                if (h.compare(data[i].getValue() > data[i + 1].getValue())) {
                    h.swap(i, i + 1);
                    swapped = true;
                }
                data[i].removeTemporaryColor();
            }
            data[end].setPrimaryColor(Color.DARK_GRAY);
            end--;
        } while (swapped);
    }

    @Override
    public String getCategory() {
        return "BubbleSort";
    }
}
