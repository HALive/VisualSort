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
 * This Class Implements Bidirectonal BubbleSort also known as CocktailShakerSort
 */
public class BiDiBubbleSort extends SortingAlgorithm {

    public BiDiBubbleSort() {
        super("Shaker sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler h, int l, int r) {
        boolean swapped;
        int start = l, stop = r - 1;
        do {
            swapped = false;
            for (int i = start; h.compare(i < stop); i++) {
                if (h.compare(data[i].getValue() > data[i + 1].getValue())) {
                    h.swap(i, i + 1);
                    swapped = true;
                }
            }
            data[stop].setRenderColor(Color.CYAN);
            if (h.compare(!swapped)) {
                break;
            }
            stop--;
            for (int i = stop; h.compare(i > start); i--) {
                if (h.compare(data[i].getValue() < data[i - 1].getValue())) {
                    h.swap(i, i - 1);
                    swapped = true;
                }
            }
            data[start].setRenderColor(Color.green);
            start++;
        } while (h.compare(swapped));
    }

    @Override
    public String getCategory() {
        return "BubbleSort";
    }
}
