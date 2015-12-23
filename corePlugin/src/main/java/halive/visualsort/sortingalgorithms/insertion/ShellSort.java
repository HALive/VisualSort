/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.insertion;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

/**
 * This Class Implements Shell Sort, A Refined version of Insertion Sort
 */
public class ShellSort extends SortingAlgorithm {

    public ShellSort() {
        super("Shell Sort", "");
    }

    protected ShellSort(String name, String description) {
        super(name, description);
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        int[] cols = {1391376, 463792, 198768, 86961, 33936, 13776, 4592, 1968, 861, 336, 112, 48, 21, 7, 3, 1};
        shellsort(data, sortingHandler, cols);
    }

    protected void shellsort(DataEntry[] d, SortingHandler h, int[] cols) {
        for (int i = 0; i < cols.length; i++) {
            int step = cols[i];
            for (int j = 0; j < step; j++) {
                InsertionSortUtils.insertionSort(d, h, j, step);
            }
        }
        //InsertionSortUtils.insertionSort(d, h, 0, 1);
    }
}
