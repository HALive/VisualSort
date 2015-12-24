/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.bubblesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class implements Bubble sort.
 */
public class BubbleSort extends SortingAlgorithm {

    public BubbleSort() {
        super("Bubble Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        for (int i = 0; sortingHandler.compare(i < data.length - 1); i++) {
            for (int j = 0; sortingHandler.compare(j < data.length - (1 + i)); j++) {
                if (sortingHandler.compare(data[j].getValue() > data[j + 1].getValue())) {
                    sortingHandler.swap(j, j + 1);
                }
            }
            data[data.length - (1 + i)].setRenderColor(Color.red);
        }
    }
}
