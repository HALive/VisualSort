/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.insertion;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SubArraySortingAlgortihm;
import halive.visualsort.core.algorithms.sorting.SwappingHandler;

/**
 * This Class Implements Shell Sort, A Refined version of Insertion Sort
 */
public class ShellSort extends SubArraySortingAlgortihm {

    public ShellSort() {
        super("Shell Sort", "");
    }

    protected SwappingHandler swapper = SwappingHandler.getForAlgorithm(this);

    protected ShellSort(String name, String description) {
        super(name, description);
    }

    @Override
    public void sort(DataEntry[] data, SortingHandler sortingHandler) {
        int[] cols = {1391376, 463792, 198768, 86961, 33936, 13776, 4592, 1968, 861, 336, 112, 48, 21, 7, 3, 1};
        shellsort(data, sortingHandler, cols, 0, data.length);
    }

    protected void shellsort(DataEntry[] d, SortingHandler h, int[] cols, int l, int r) {
        for (int i = 0; i < cols.length; i++) {
            int step = cols[i];
            for (int j = l; j < step; j++) {
                InsertionSortUtils.insertionSort(d, h, swapper, j, step, r);
            }
        }
        //InsertionSortUtils.insertionSort(d, h, 0, 1);
    }

    @Override
    public String getCategory() {
        return "InsertionSort";
    }
}
