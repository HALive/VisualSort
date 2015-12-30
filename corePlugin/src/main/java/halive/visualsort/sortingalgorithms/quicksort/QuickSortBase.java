/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;
import halive.visualsort.sortingalgorithms.quicksort.pivot.IQSPivotHeuristic;
import halive.visualsort.sortingalgorithms.quicksort.pivot.QuickSortHeuristic;

import java.awt.Color;

public abstract class QuickSortBase extends SortingAlgorithm {

    protected IQSPivotHeuristic pivotHeuristic;
    protected QuickSortHeuristic heuristic;

    public QuickSortBase(String name, String description, QuickSortHeuristic heuristic) {
        super(name, description);
        this.pivotHeuristic = heuristic.getHeuristic();
        this.heuristic = heuristic;
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        quicksort(l, r - 1, data, sortingHandler);
    }

    public void quicksort(int left, int right, DataEntry[] data, SortingHandler c) {
        if (c.compare(left < right)) {
            int div = partitionAndGetPivot(left, right, data, c);
            data[div].setPrimaryColor(Color.green);
            quicksort(left, div - 1, data, c);
            quicksort(div + 1, right, data, c);
            data[right].setTemporaryColor(Color.green);
            data[left].setTemporaryColor(Color.green);
        }
    }

    @Override
    public String getCategory() {
        return heuristic.getCategory();
    }

    protected int getPivotPos(int left, int right, DataEntry[] entries, SortingHandler h) {
        return pivotHeuristic.getPivotElementPositon(left, right, entries, h);
    }

    public abstract int partitionAndGetPivot(int left, int right, DataEntry[] data, SortingHandler c);
}
