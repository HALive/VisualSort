/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.util;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.awt.Color;

public class InsertionSortUtils {

    public static void insertionSort(DataEntry[] d, SortingHandler h, int l, int step) {
        for (int i = l + step; i < d.length; i = i + step) {
            Color oldColor = d[i].getRenderColor();
            int val = d[i].getValue();
            d[i].setRenderColor(Color.magenta);
            int j = i;
            while (h.compare(j > l && d[j - step].getValue() > val)) {
                h.swap(j - step, j);
                d[j].setRenderColor(Color.gray);
                j = j - step;
            }
            for (int x = j; x < i; x++) {
                d[x].setRenderColor(Color.BLACK);
            }
            d[i].setRenderColor(oldColor);
        }
    }
}
