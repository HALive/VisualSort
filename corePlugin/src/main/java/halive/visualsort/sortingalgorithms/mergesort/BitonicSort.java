/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SubArraySortingAlgortihm;

/**
 * This CLass implements Bitonic Merge Sort.
 * The Implementation has been Taken from:
 * http://www.iti.fh-flensburg.de/lang/algorithmen/sortieren/bitonic/oddn.htm
 */
public class BitonicSort extends SubArraySortingAlgortihm {

    //Direction Constants
    private static boolean ASC = true;
    private static boolean DESC = false;

    public BitonicSort() {
        super("Bitonic Sort", "");
    }

    @Override
    public void sort(DataEntry[] entries, SortingHandler handler) {
        bitonicSort(0, entries.length, ASC, entries, handler);
    }

    private void bitonicSort(int l, int r, boolean direction, DataEntry[] entries, SortingHandler handler) {
        if (r > 1) {
            int middle = r / 2;
            bitonicSort(l, middle, !direction, entries, handler);
            bitonicSort(l + middle, r - middle, direction, entries, handler);
            merge(l, r, direction, entries, handler);
        }
    }

    private void merge(int l, int r, boolean direction, DataEntry[] entries, SortingHandler handler) {
        if (r > 1) {
            int middle = determineMiddleForMerge(r);
            for (int i = l; i < (l + r) - middle; i++) {
                if (handler.compare(direction == (entries[i].getValue() > entries[i + middle].getValue()))) {
                    swap(entries, i, i + middle, handler);
                }
            }
            merge(l, middle, direction, entries, handler);
            merge(l + middle, r - middle, direction, entries, handler);
        }
    }

    public int determineMiddleForMerge(int r) {
        int i = 1;
        while (i < r) {
            i = i << 1;
        }
        return i >> 1;
    }

    @Override
    public String getCategory() {
        return "MergeSort";
    }

    @Override
    public String getName() {
        return name;
    }
}
