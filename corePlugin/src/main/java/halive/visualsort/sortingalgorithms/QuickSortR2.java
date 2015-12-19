/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.sortingalgorithms.util.QuickSortBase;

public class QuickSortR2 extends QuickSortBase {

    public QuickSortR2() {
        super("Quick Sort - Variant 2", " ");
    }

    @Override
    public int getPivotPos(int left, int right, DataEntry[] data, SortingHandler c) {
        int i = left;
        int j = right - 1;
        int pivot = data[right].getValue();
        do {
            while (c.compare(pivot >= data[i].getValue() && i < right)) {
                i++;
            }
            while (c.compare(pivot <= data[j].getValue() && j > left)) {
                j--;
            }
            if (c.compare(i < j)) {
                c.swap(i, j);
            }
        } while (c.compare(i < j));
        c.swap(i, right);
        return i;
    }
}
