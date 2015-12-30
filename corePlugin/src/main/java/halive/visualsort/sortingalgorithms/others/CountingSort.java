/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This class implements CouningSort
 */
public class CountingSort extends SortingAlgorithm {

    public CountingSort() {
        super("Counting Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        int[] valueCount = new int[sortingHandler.getMaxValue() + 1];
        //count the Values
        for (int i = 0; i < data.length; i++) {
            int val = data[i].getValue();
            if (sortingHandler.compare(val > sortingHandler.getMaxValue())) {
                continue;
            }
            if (i > 0 && i < data.length - 1) {
                data[i - 1].setTemporaryColor(Color.BLACK);
            }
            sortingHandler.onSwapped();
            valueCount[val]++;
        }
        //Recreate the array
        int ptr = 0;
        for (int i = 0; i < valueCount.length; i++) {
            int v = valueCount[i];
            for (int j = 0; j < v; j++) {
                data[ptr].setValue(i);
                data[ptr].removeTemporaryColor();
                data[ptr].setPrimaryColor(Color.green);
                sortingHandler.onSwapped();
                ptr++;
            }
        }
        //Create Red Items if not everythin was counted
        if (ptr < data.length) {
            for (int i = ptr; i < data.length; i++) {
                data[i].setValue(sortingHandler.getMaxValue());
                data[i].setPrimaryColor(Color.red);
            }
        }
    }

    @Override
    public boolean allowExport() {
        return false;
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}
