/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SubArraySortingAlgortihm;

import java.awt.Color;

/**
 * This Class Implements Pancake Sort
 * The implementation is Mostly taken from:
 * http://rosettacode.org/wiki/Sorting_algorithms/Pancake_sort
 */
public class PancakeSort extends SubArraySortingAlgortihm {

    public PancakeSort() {
        super("Pancake Sort", "");
    }

    @Override
    public void sort(DataEntry[] data, SortingHandler sortingHandler) {
        pancakeSort(0, data.length, 1, data, sortingHandler);
    }

    private void pancakeSort(int l, int n, int d, DataEntry[] entries, SortingHandler h) {
        if (n == l) {
            return;
        }
        MinMax minMax = getMinAndMax(n, l, entries, h);
        int[] minMaxArr = minMax.toIntArray();
        int bestX = minMaxArr[d];
        int alternateX = minMaxArr[1 - d];
        boolean flipped = false;
        if (bestX == (n - 1)) {
            n--;
        } else if (bestX == 0) {
            flip(l, n - 1, entries, h);
            n--;
        } else if (alternateX == (n - 1)) {
            d = 1 - d;
            n--;
            flipped = true;
        } else {
            flip(l, bestX, entries, h);
        }
        pancakeSort(l, n, d, entries, h);
        if (flipped) {
            flip(l, n, entries, h);
        }
    }

    private void flip(int l, int n, DataEntry[] data, SortingHandler h) {
        for (int i = l; i < (n + 1) / 2; i++) {
            data[i].setTemporaryColor(Color.red);
            data[n - 1].setTemporaryColor(Color.green);
            swap(data, i, n - i, h);
            data[i].removeTemporaryColor();
            data[n - 1].removeTemporaryColor();
        }
    }

    private MinMax getMinAndMax(int n, int l, DataEntry[] data, SortingHandler h) {
        MinMax minMax = new MinMax();
        for (int i = l; i < n; i++) {
            data[i].setRenderColor(Color.magenta);
            if (h.compare(data[minMax.min].getValue() > data[i].getValue())) {
                minMax.min = i;
            } else if (h.compare(data[minMax.max].getValue() < data[i].getValue())) {
                minMax.max = i;
            }
            data[i].setRenderColor(Color.blue);
        }
        return minMax;
    }

    @Override
    public String getCategory() {
        return "Others";
    }

    private static class MinMax {

        private int min = 0;
        private int max = 0;

        private int[] toIntArray() {
            return new int[]{min, max};
        }
    }
}
