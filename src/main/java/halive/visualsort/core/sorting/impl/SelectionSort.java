package halive.visualsort.core.sorting.impl;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;
import halive.visualsort.core.sorting.impl.util.SelectionSortUtils;

import java.awt.Color;

public class SelectionSort extends SortingAlgorithm {
    public SelectionSort() {
        super("Selection sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler h) {
        for (int i = 0; h.compare(i < data.length); i++) {
            int mpos = SelectionSortUtils.getMinimumPos(data, i, data.length - 1, h);
            h.swap(i, mpos);
            data[i].setRenderColor(Color.green);
        }
    }


}
