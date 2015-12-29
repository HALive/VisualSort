/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.insertion;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.awt.Color;

public class InsertionSortUtils {

    public static void insertionSort(DataEntry[] d, SortingHandler h, int l, int step) {
        for (int i = l + step; i < d.length; i = i + step) {
            d[i].setPrimaryColor(Color.BLACK);
            int val = d[i].getValue();
            d[i].setTemporaryColor(Color.magenta);
            int j = i;
            while (h.compare(j > l && d[j - step].getValue() > val)) {
                h.swap(j - step, j);
                d[j].setTemporaryColor(Color.gray);
                j = j - step;
            }
            for (DataEntry aD : d) {
                aD.removeTemporaryColor();
            }
        }
    }
}
