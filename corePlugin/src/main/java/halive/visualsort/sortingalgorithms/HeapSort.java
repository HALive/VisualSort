/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

/**
 * This Class Implements the HeapSort Algorithm.
 * This needs some more work in terms of visualisation of the Heap
 */
public class HeapSort extends SortingAlgorithm {

    public HeapSort() {
        super("HeapSort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        createHeap(data, sortingHandler);
        for (int i = data.length - 1; i > 0; i--) {
            sortingHandler.swap(0, i);
            createHeapK(data, 0, i - 1, sortingHandler);
        }
        //For Some Reason the First 2 values and the last 2 might be in the wrong
        //Order this is to prevent taht.
        if (data[0].getValue() > data[1].getValue()) {
            sortingHandler.swap(0, 1);
        }
        if (data[data.length - 2].getValue() > data[data.length - 1].getValue()) {
            sortingHandler.swap(data.length - 2, data.length - 1);
        }
    }

    public void createHeap(DataEntry[] data, SortingHandler a) {
        for (int i = (data.length / 2); i >= 0; i--) {
            createHeapK(data, i, data.length - 1, a);
        }
    }

    public void createHeapK(DataEntry[] data, int knot, int size, SortingHandler a) {
        int left = (knot * 2) + 1;
        int right = left + 1;
        int middle;
        if (left <= size && right > size) {
            if (data[left].getValue() < data[knot].getValue()) {
                a.swap(left, knot);
            }
        } else if (right <= size) {
            middle = a.compare(data[left].getValue() > data[right].getValue()) ? left : right;
            if (a.compare(data[middle].getValue() > data[knot].getValue())) {
                a.swap(middle, knot);
                createHeapK(data, middle, size, a);
            }
        }
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}
