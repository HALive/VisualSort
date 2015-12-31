/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class Implements Odd Even Sort. It basically is a Improvement of BubbleSort
 */
public class OddEvenSort extends SortingAlgorithm {

    public OddEvenSort() {
        super("Odd Even Sort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = l + 1; i < r - 1; i += 2) {
                data[i - 1].setRenderColor(Color.blue);
                if (sortingHandler.compare(data[i].getValue() > data[i + 1].getValue())) {
                    sortingHandler.swap(i, i + 1);
                    sorted = false;
                }
                data[i].setRenderColor(Color.DARK_GRAY);
            }
            for (int i = l; i < r - 1; i += 2) {
                if (i > 0) {
                    data[i - 1].setRenderColor(Color.blue);
                }
                if (sortingHandler.compare(data[i].getValue() > data[i + 1].getValue())) {
                    sortingHandler.swap(i, i + 1);
                    sorted = false;
                }
                data[i].setRenderColor(Color.ORANGE);
            }
        }
        for (int i = l; i < r; i++) {
            data[i].setRenderColor(Color.darkGray);
        }
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}
