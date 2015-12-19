/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

public class InsertionSort extends SortingAlgorithm {

    public InsertionSort() {
        super("Insertion Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        for (int i = 1; i < data.length; i++) {
            Color oldColor = data[i].getRenderColor();
            int val = data[i].getValue();
            data[i].setRenderColor(Color.magenta);
            int j = i;
            while (sortingHandler.compare(j > 0 && data[j - 1].getValue() > val)) {
                data[j].setValue(data[j - 1].getValue());
                data[j].setRenderColor(Color.gray);
                j--;
                sortingHandler.incrementSwapsAndDelay();
            }
            data[j].setValue(val);
            sortingHandler.incrementSwapsAndDelay();
            for (int x = j; x < i; x++) {
                data[x].setRenderColor(Color.BLACK);
            }
            data[i].setRenderColor(oldColor);
        }
    }
}
