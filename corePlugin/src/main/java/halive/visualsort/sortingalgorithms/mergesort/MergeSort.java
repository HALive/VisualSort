/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class Implements Merge Sort in the Basic Implementation
 */
@SuppressWarnings("EmptyCatchBlock")
public class MergeSort extends SortingAlgorithm {

    public MergeSort() {
        super("Merge sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        int[] a = new int[data.length];
        int[] b = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            data[i].setRenderColor(Color.black);
            int val = data[i].getValue();
            a[i] = val;
            b[i] = val;
        }
        ValueUpdater updater = new ValueUpdater(data, a);
        Thread t = new Thread(updater, "VisualSort Value Updater");
        t.start();
        mergesort(0, data.length - 1, a, b, sortingHandler);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
        t.interrupt();
    }

    public void mergesort(int l, int r, int[] a, int[] b, SortingHandler h) {
        if (h.compare(l < r)) {
            int q = (l + r) / 2;
            mergesort(l, q, a, b, h);
            mergesort(q + 1, r, a, b, h);
            merge(l, r, q, a, b, h);
        }
    }


    public void merge(int lo, int hi, int m, int[] a, int[] b, SortingHandler h) {
        int i, j, k;

        for (i = lo; i <= hi; i++) {
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

    private static class ValueUpdater implements Runnable {

        private DataEntry[] toUpdate;
        private int[] updateFrom;

        private ValueUpdater(DataEntry[] toUpdate, int[] updateFrom) {
            this.toUpdate = toUpdate;
            this.updateFrom = updateFrom;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < updateFrom.length; i++) {
                    toUpdate[i].setValue(updateFrom[i]);
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @Override
    public String getCategory() {
        return "MergeSort";
    }
}
