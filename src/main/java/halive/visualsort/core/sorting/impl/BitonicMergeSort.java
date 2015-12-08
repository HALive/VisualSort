package halive.visualsort.core.sorting.impl;

import halive.visualsort.core.SortingHandler;

public class BitonicMergeSort extends MergeSort {

    @Override
    public void merge(int lo, int hi, int m, int[] a, int[] b, SortingHandler h) {
        int i, j, k;

        for (i = lo; h.compare(i <= hi); i++) {
            b[i] = a[i];
        }
        i = lo;
        j = m + 1;
        k = lo;
        while (h.compare(i <= m && j <= hi)) {
            if (h.compare(b[i] <= b[j])) {
                a[k++] = b[i++];
            } else {
                a[k++] = b[j++];
            }
            h.incrementSwapsAndDelay();
        }
        while (h.compare(i <= m)) {
            a[k++] = b[i++];
            h.incrementSwapsAndDelay();
        }
    }

    @Override
    public String getName() {
        return "Bitonic Merge sort";
    }
}
