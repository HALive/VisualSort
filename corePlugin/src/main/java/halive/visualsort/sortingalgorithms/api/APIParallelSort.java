/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.api;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.util.Arrays;

/**
 * This Class Sorts using the Arrays.ParallelSort Method
 */
public class APIParallelSort extends APISort {

    public APIParallelSort() {
        this.name = "API Parallel Sort";
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        Arrays.parallelSort(data, this);
    }
}
