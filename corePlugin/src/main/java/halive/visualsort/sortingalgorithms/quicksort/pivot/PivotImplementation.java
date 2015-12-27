/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.pivot;

import halive.utils.GenericsUtils;
import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

@SuppressWarnings("UnusedParameters")
class PivotImplementation {

    public static int getLastElementPivot(int left, int right, DataEntry[] entries, SortingHandler h) {
        return right;
    }

    public static int getRandomPivot(int left, int right, DataEntry[] entries, SortingHandler h) {
        int len = right - left;
        return (int) (left + (Math.random() * (len + 1)));
    }

    @SuppressWarnings("ConstantConditions")
    public static int getMedianOfThreePivot(int l, int r, DataEntry[] dataEntries, SortingHandler handler) {
        int m = l + (r / 2);
        DataVal[] vals = {new DataVal(dataEntries[l], l),
                new DataVal(dataEntries[m], m),
                new DataVal(dataEntries[r], r)};
        return GenericsUtils.center(vals).pos;
    }

    private static class DataVal implements Comparable<DataVal> {

        private int pos;
        private DataEntry entry;

        public DataVal(DataEntry entry, int pos) {
            this.entry = entry;
            this.pos = pos;
        }

        @Override
        public int compareTo(DataVal o) {
            return this.entry.compareTo(o.entry);
        }
    }
}
