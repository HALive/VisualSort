/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.insertion;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * This Class Implements A Paralell Version of Shell Sort.
 * Currently it seems a Bit Buggy due to a Delay when Rendering.
 */
public class ShellSortParallel extends ShellSort {

    public ShellSortParallel() {
        super("Shell Sort Parallel", "");
    }

    @Override
    protected void shellsort(DataEntry[] d, SortingHandler h, int[] cols) {
        ForkJoinPool pool = new ForkJoinPool();
        Deque<ForkJoinTask<Integer>> tasks = new ArrayDeque<>();
        for (int i = 0; i < cols.length - 1; i++) {
            for (int j = 0; j < cols[i]; j++) {
                ForkJoinTask<Integer> task = pool.submit(new ShellSortingHandler(d, h, j, cols[i]), cols[i]);
                tasks.add(task);
            }
            while (!pool.isQuiescent()) {
                Thread.yield();
            }
        }
        InsertionSortUtils.insertionSort(d, h, 0, 1);
    }

    private class ShellSortingHandler implements Runnable {

        private SortingHandler h;
        private DataEntry[] d;
        private int step;
        private int l;

        public ShellSortingHandler(DataEntry[] d, SortingHandler h, int l, int step) {
            this.d = d;
            this.h = h;
            this.step = step;
            this.l = l;
        }

        @Override
        public void run() {
            InsertionSortUtils.insertionSort(d, h, l, step);
        }
    }
}
