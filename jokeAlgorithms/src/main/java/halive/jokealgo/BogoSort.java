/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.jokealgo;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;
import java.util.Random;

public class BogoSort extends SortingAlgorithm {

    public BogoSort() {
        super("BogoSort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        Random rnd = new Random();
        int diff = r - l;
        do {
            int pos1 = l + rnd.nextInt(diff);
            int pos2 = l + rnd.nextInt(diff);
            Color color = new Color(rnd.nextInt() & 0xFFFFFF);
            data[pos1].setRenderColor(color);
            data[pos2].setRenderColor(color);
            sortingHandler.swap(pos1, pos2);
        } while (!isSorted(data, sortingHandler, l, r));
    }

    public boolean isSorted(DataEntry[] d, SortingHandler h, int l, int r) {
        for (int i = l; i < d.length - 1; i++) {
            if (h.compare(d[i].getValue() > d[i + 1].getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean allowExport() {
        return false;
    }

    @Override
    public String getCategory() {
        return null;
    }
}
