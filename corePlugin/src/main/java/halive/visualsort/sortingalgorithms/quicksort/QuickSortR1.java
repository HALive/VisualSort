/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.sortingalgorithms.quicksort.pivot.QuickSortHeuristic;

/**
 * Implements one Implementation of Quick Sort. It uses The last Element as A privot element.
 */
public class QuickSortR1 extends QuickSortBase {

    public QuickSortR1(QuickSortHeuristic heuristic) {
        super("Quick Sort - Variant 1", " ", heuristic);
    }

    public int partitionAndGetPivot(int left, int right, DataEntry[] data, SortingHandler c) {
        int i = left;
        int j = right - 1;
        int pivotPos = getPivotPos(left, right, data, c);
        int pivot = data[pivotPos].getValue();
        while (c.compare(i <= j)) {
            if (c.compare(data[i].getValue() > pivot)) {
                c.swap(i, j);
                j--;
            } else {
                i++;
            }
        }
        c.swap(i, pivotPos);
        return i;
    }
}
