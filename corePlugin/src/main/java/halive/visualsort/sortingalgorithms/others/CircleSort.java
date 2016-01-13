/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;
import halive.visualsort.core.interfaces.IVisualSortUI;

import java.awt.Color;

/**
 * This Class Implements Circle Sort.
 * The Implementation is taken from:
 * http://rosettacode.org/wiki/Sorting_Algorithms/Circle_Sort#Java
 */
public class CircleSort extends SortingAlgorithm {

    public CircleSort() {
        super("Circle Sort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        int cycleNo = 0;
        IVisualSortUI ui = sortingHandler.getGui();
        while (circleSort(l, r - 1, 0, data, sortingHandler) != 0) {
            cycleNo++;
            if (ui != null) {
                ui.displayStatus(String.format("Cycle %d done", cycleNo));
            }
        }
    }

    private int circleSort(int l, int r, int swp, DataEntry[] entries, SortingHandler handler) {
        if (r == l) {
            return swp;
        }

        int low = l;
        int high = r;
        int mid = (r - l) / 2;

        while (l < r) {
            if (entries[l].compareTo(entries[r]) > 0) {
                handler.swap(l, r);
                swp++;
            }
            entries[l].removeTemporaryColor();
            l++;
            entries[l].setTemporaryColor(Color.CYAN);
            entries[r].removeTemporaryColor();
            r--;
            entries[r].setTemporaryColor(Color.yellow);
        }

        if (r == l && entries[l].compareTo(entries[r + 1]) > 0) {
            handler.swap(l, r + 1);
            swp++;
        }

        for (int i = low; i <= high; i++) {
            entries[i].removeTemporaryColor();
        }

        swp = circleSort(low, low + mid, swp, entries, handler);
        swp = circleSort(low + mid + 1, high, swp, entries, handler);

        return swp;
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}
