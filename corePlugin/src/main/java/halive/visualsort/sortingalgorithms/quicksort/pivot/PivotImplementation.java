/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.pivot;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

class PivotImplementation {

    public static int getLastElementPivot(int left, int right, DataEntry[] entries, SortingHandler h) {
        return right;
    }

    public static int getRandomPivot(int left, int right, DataEntry[] entries, SortingHandler h) {
        int len = right - left;
        return (int) (left + (Math.random() * (len + 1)));
    }
}
