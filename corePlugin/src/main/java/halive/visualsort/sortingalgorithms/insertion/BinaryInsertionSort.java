/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.insertion;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class Implements Binary Insertion Sort, It uses Binary Search to determine the Insertion Positon
 */
public class BinaryInsertionSort extends SortingAlgorithm {

    public BinaryInsertionSort() {
        super("Binary Insertion Sort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        for (int i = l; i < r; i++) {
            int number = data[i].getValue();
            data[i].setRenderColor(Color.red);
            int insPos = getBinaryInsertionPos(l, i, number, data, sortingHandler);
            data[insPos].setRenderColor(Color.green);
            for (int j = i; j > insPos; j--) {
                sortingHandler.swap(j, j - 1);
            }
            data[insPos].setValue(number);

            data[i].setRenderColor(Color.blue);
            data[insPos].setRenderColor(Color.blue);
        }
    }

    public static int getBinaryInsertionPos(int l, int r, int val, DataEntry[] d, SortingHandler h) {
        if (l > r) {
            return l;
        }
        int diff = r - l;
        int center = l + (diff / 2);
        if (h.compare(d[center].getValue() == val)) {
            return center;
        } else if (h.compare(diff <= 1 && d[center].getValue() > val)) {
            return l;
        } else if (h.compare(diff <= 1 && d[center].getValue() < val)) {
            return r;
        } else if (h.compare(d[center].getValue() < val)) {
            return getBinaryInsertionPos(center, r, val, d, h);
        } else if (h.compare(d[center].getValue() > val)) {
            return getBinaryInsertionPos(l, center, val, d, h);
        }
        return -1;
    }

    @Override
    public String getCategory() {
        return "InsertionSort";
    }
}
