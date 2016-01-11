/*
 * Copyright (c) HALive 2016
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

public abstract class SubArraySortingAlgortihm extends SortingAlgorithm {

    public SubArraySortingAlgortihm(String name, String description) {
        super(name, description);
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        DataEntry[] subData = generateSubArray(data, l, r);
        sort(subData, sortingHandler);
    }

    protected DataEntry[] generateSubArray(DataEntry[] data, int l, int r) {
        DataEntry[] newData = new DataEntry[(r - l)];
        for (int i = 0; i < newData.length; i++) {
            newData[i] = data[l + i];
        }
        return newData;
    }

    protected void swap(DataEntry[] entries, int l, int r, SortingHandler h) {
        int v = entries[l].getValue();
        entries[l].setValue(entries[r].getValue());
        entries[r].setValue(v);
        h.onSwapped();
    }

    public abstract void sort(DataEntry[] entries, SortingHandler handler);
}
