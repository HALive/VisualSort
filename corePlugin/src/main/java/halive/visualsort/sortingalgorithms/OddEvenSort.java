/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

public class OddEvenSort extends SortingAlgorithm {

    public OddEvenSort() {
        super("Odd Even Sort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 1; i < data.length - 1; i += 2) {
                data[i - 1].setRenderColor(Color.blue);
                if (sortingHandler.compare(data[i].getValue() > data[i + 1].getValue())) {
                    sortingHandler.swap(i, i + 1);
                    sorted = false;
                }
                data[i].setRenderColor(Color.DARK_GRAY);
            }
            for (int i = 0; i < data.length - 1; i += 2) {
                if (i > 0) {
                    data[i - 1].setRenderColor(Color.blue);
                }
                if (sortingHandler.compare(data[i].getValue() > data[i + 1].getValue())) {
                    sortingHandler.swap(i, i + 1);
                    sorted = false;
                }
                data[i].setRenderColor(Color.ORANGE);
            }
        }
        for (int i = 0; i < data.length; i++) {
            data[i].setRenderColor(Color.darkGray);
        }
    }
}
