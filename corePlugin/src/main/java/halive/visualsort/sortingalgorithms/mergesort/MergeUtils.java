/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

public class MergeUtils {

    public static void mergeInPlace(int l, int r, DataEntry[] entries, SortingHandler h) {
        int m = ((r - l) / 2) + l;
        int pos = l;
        while (pos <= m) {
            if (h.compare(entries[pos].getValue() > entries[m + 1].getValue())) {
                h.swap(pos, m + 1);
                for (int i = m + 1; i < r; i++) {
                    if (h.compare(entries[i].getValue() > entries[i + 1].getValue())) {
                        h.swap(i, i + 1);
                    }
                }
            }
            pos++;
        }
    }
}
