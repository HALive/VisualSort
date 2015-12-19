/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.jokealgo;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;
import java.util.Random;

public class BogoSort extends SortingAlgorithm {

    public BogoSort() {
        super("BogoSort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        Random rnd = new Random();
        do {
            int pos1 = rnd.nextInt(data.length);
            int pos2 = rnd.nextInt(data.length);
            Color color = new Color(rnd.nextInt() & 0xFFFFFF);
            data[pos1].setRenderColor(color);
            data[pos2].setRenderColor(color);
            sortingHandler.swap(pos1, pos2);
        } while (!isSorted(data, sortingHandler));
    }

    public boolean isSorted(DataEntry[] d, SortingHandler h) {
        for (int i = 0; i < d.length - 1; i++) {
            if (h.compare(d[i].getValue() > d[i + 1].getValue())) {
                return false;
            }
        }
        return true;
    }
}
