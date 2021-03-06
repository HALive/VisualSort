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
 * This Class implements the GnomeSort Algorithm
 */
public class GnomeSort extends SortingAlgorithm {

    public GnomeSort() {
        super("Gnome sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler h, int l, int r) {
        int pos = l;
        do {
            if (h.compare(data[pos].getValue() > data[pos + 1].getValue())) {
                h.swap(pos, pos + 1);
                if (h.compare(pos > l)) {
                    data[pos].setRenderColor(Color.red);
                    pos--;
                }
            } else {
                pos++;
                data[pos].setRenderColor(Color.blue);
            }
        } while (h.compare(pos < r - 1));
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}
