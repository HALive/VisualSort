/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelMergeSort extends MergeSort {

    private ExecutorService pool;

    public ParallelMergeSort() {
        super("Merge Sort Parallel");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        pool = Executors.newCachedThreadPool();

        super.doSort(data, sortingHandler, l, r);
    }

    @Override
    public void mergeSort(int l, int r, DataEntry[] data, SortingHandler h) {
        int diff = r - l;
        if (diff == 0) {
            return;
        } else if (diff == 1) {
            if (h.compare(data[l].getValue() > data[r].getValue())) {
                h.swap(l, r);
            }
        } else {
            int m = ((r - l) / 2) + l;
            Future task = null;
            if ((r - m) >= 40) {
                task = pool.submit(new MergeTask(data, h, l, m), System.currentTimeMillis());
            } else {
                mergeSort(l, m, data, h);
            }
            mergeSort(m + 1, r, data, h);
            if (task != null) {
                try {
                    task.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            merger.getMergeMethod().merge(l, r, data, h);
        }
    }

    private class MergeTask implements Runnable {

        private int l;
        private int r;
        private DataEntry[] data;
        private SortingHandler handler;

        public MergeTask(DataEntry[] data, SortingHandler handler, int l, int r) {
            this.data = data;
            this.handler = handler;
            this.l = l;
            this.r = r;
        }

        @Override
        public void run() {
            mergeSort(l, r, data, handler);
        }
    }
}
