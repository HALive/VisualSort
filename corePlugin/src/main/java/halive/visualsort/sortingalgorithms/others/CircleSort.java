/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.interfaces.IVisualSortUI;
import halive.visualsort.core.sorting.SortingAlgorithm;

/**
 * This Class Implements Circle Sort.
 * The Implementation is taken from:
 * http://rosettacode.org/wiki/Sorting_Algorithms/Circle_Sort#Java
 */
public class CircleSort extends SortingAlgorithm {

    public CircleSort() {
        super("CircleSort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        int cycleNo = 0;
        IVisualSortUI ui = sortingHandler.getGui();
        while (circleSort(0, data.length - 1, 0, data, sortingHandler) != 0) {
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
            l++;
            r--;
        }

        if (r == l && entries[l].compareTo(entries[r + 1]) > 0) {
            handler.swap(l, r + 1);
            swp++;
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
