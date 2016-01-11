/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SubArraySortingAlgortihm;

/**
 * This Class Implements the HeapSort Algorithm.
 * This needs some more work in terms of visualisation of the Heap
 */
public class HeapSort extends SubArraySortingAlgortihm {

    public HeapSort() {
        super("HeapSort", " ");
    }


    @Override
    public void sort(DataEntry[] data, SortingHandler sortingHandler) {
        createHeap(data, sortingHandler, 0, data.length);
        for (int i = data.length - 1; i > 0; i--) {
            swap(data, 0, i, sortingHandler);
            createHeapK(data, 0, i - 1, sortingHandler);
        }
        //For Some Reason the First 2 values and the last 2 might be in the wrong
        //Order this is to prevent taht.
        if (data[0].getValue() > data[1].getValue()) {
            swap(data, 0, 1, sortingHandler);
        }
        if (data[data.length - 2].getValue() > data[data.length - 1].getValue()) {
            swap(data, data.length - 2, data.length - 1, sortingHandler);
        }
    }

    public void createHeap(DataEntry[] data, SortingHandler a, int l, int r) {
        for (int i = (r / 2); i >= l; i--) {
            createHeapK(data, i, r - 1, a);
        }
    }

    public void createHeapK(DataEntry[] data, int knot, int size, SortingHandler a) {
        int left = (knot * 2) + 1;
        int right = left + 1;
        int middle;
        if (left <= size && right > size) {
            if (a.compare(data[left].getValue() < data[knot].getValue())) {
                swap(data, left, knot, a);
            }
        } else if (right <= size) {
            middle = a.compare(data[left].getValue() > data[right].getValue()) ? left : right;
            if (a.compare(data[middle].getValue() > data[knot].getValue())) {
                swap(data, middle, knot, a);
                createHeapK(data, middle, size, a);
            }
        }
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}
