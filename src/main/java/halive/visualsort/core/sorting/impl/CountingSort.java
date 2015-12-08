package halive.visualsort.core.sorting.impl;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

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
        int[] valueCount = new int[sortingHandler.getMaxValue()+1];
        //count the Values
        for (int i = 0; i < data.length; i++) {
            data[i].setRenderColor(Color.blue);
            int val = data[i].getValue();
            if(sortingHandler.compare(val > sortingHandler.getMaxValue())) {
                continue;
            }
            if(i > 0 && i < data.length-1) {
                data[i-1].setRenderColor(Color.BLACK);
            }
            sortingHandler.incrementSwapsAndDelay();
            valueCount[val]++;
        }
        //Recreate the array
        int ptr = 0;
        for (int i = 0; i < valueCount.length; i++) {
            int v = valueCount[i];
            for (int j = 0; j < v; j++) {
                data[ptr].setValue(i);
                data[ptr].setRenderColor(Color.green);
                sortingHandler.incrementSwapsAndDelay();
                ptr++;
            }
        }
        if(ptr < data.length) {
            for (int i = ptr; i < data.length; i++) {
                data[i].setValue(0);
                data[i].setRenderColor(Color.red);
            }
        }
    }
}
