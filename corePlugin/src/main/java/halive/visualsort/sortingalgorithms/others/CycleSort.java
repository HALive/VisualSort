/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class Implements CycleSort
 * The Code is Mostly taken from:
 * http://rosettacode.org/wiki/Sorting_algorithms/Cycle_sort#Java
 */
public class CycleSort extends SortingAlgorithm {

    public CycleSort() {
        super("Cycle Sort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler h) {
        for (int startPos = 0; startPos < data.length; startPos++) {
            int value = data[startPos].getValue();
            int pos = startPos;
            for (int i = startPos; i < data.length; i++) {
                data[i].setRenderColor(Color.red);
                if (h.compare(data[i].getValue() < value)) {
                    pos = incrementPos(data, pos);
                }
                if (i > 0) {
                    data[i - 1].setRenderColor(Color.blue);
                }
            }
            if (pos == startPos) {
                continue;
            }

            while (h.compare(value == data[pos].getValue())) {
                pos = incrementPos(data, pos);
            }

            int temp = data[pos].getValue();
            data[pos].setValue(value);
            data[pos].setPrimaryColor(Color.DARK_GRAY);
            value = temp;
            h.onSwapped();

            while (pos != startPos) {
                data[pos].removeTemporaryColor();
                pos = startPos;
                data[pos].setTemporaryColor(Color.MAGENTA);

                for (int i = startPos + 1; i < data.length; i++) {
                    if (h.compare(data[i].getValue() < value)) {
                        pos = incrementPos(data, pos);
                    }
                }
                while (h.compare(value == data[pos].getValue())) {
                    pos = incrementPos(data, pos);
                }

                temp = data[pos].getValue();
                data[pos].setValue(value);
                data[pos].setPrimaryColor(Color.DARK_GRAY);
                value = temp;
                h.onSwapped();
            }
        }
    }

    public int incrementPos(DataEntry[] data, int pos) {
        data[pos].removeTemporaryColor();
        pos++;
        data[pos].setTemporaryColor(Color.MAGENTA);
        return pos;
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}
