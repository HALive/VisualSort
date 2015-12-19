/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.util;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

public abstract class QuickSortBase extends SortingAlgorithm {

    public QuickSortBase(String name, String description) {
        super(name, description);
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        quicksort(0, data.length - 1, data, sortingHandler);
    }

    public void quicksort(int left, int right, DataEntry[] data, SortingHandler c) {
        if (c.compare(left < right)) {
            int div = getPivotPos(left, right, data, c);
            data[div].setRenderColor(Color.green);
            quicksort(left, div - 1, data, c);
            quicksort(div + 1, right, data, c);
            data[right].setRenderColor(Color.green);
            data[left].setRenderColor(Color.green);
        }
    }

    public abstract int getPivotPos(int left, int right, DataEntry[] data, SortingHandler c);
}
