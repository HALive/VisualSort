/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.sortingalgorithms.quicksort.pivot.QuickSortHeuristic;

import java.awt.Color;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort extends QuickSortR2 {

    private ForkJoinPool svc;

    public ParallelQuickSort(QuickSortHeuristic heuristic) {
        super("Quick Sort Parallel", "", heuristic);
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        svc = new ForkJoinPool();
        super.doSort(data, sortingHandler, l, r);
        while (!svc.isQuiescent()) {
            Thread.yield();
        }
    }

    @Override
    public void quicksort(int left, int right, DataEntry[] data, SortingHandler c) {
        if (c.compare(left < right)) {
            int div = partitionAndGetPivot(left, right, data, c);
            data[div].setRenderColor(Color.green);
            int lLen = (div - 1) - left;
            //If the length of the Left Partition is bigger then the Minimum size for a new thread
            //A new thread gets created. otherwise Quicksort for the left partition is performs in this thread
            if (lLen >= (getSizeForNewThread(data.length))) {
                svc.submit(new QSTask(left, div - 1, data, c));
            } else {
                quicksort(left, div - 1, data, c);
            }
            //Perform Quicksort for The right partition in the Current thread.
            quicksort(div + 1, right, data, c);
            data[right].setRenderColor(Color.green);
            data[left].setRenderColor(Color.green);
        }
    }

    public static int getSizeForNewThread(int len) {
        if (len / 16 > 40) {
            return 40;
        }
        return len / 16;
    }

    private class QSTask implements Callable<Long> {

        private int left;
        private int right;
        private DataEntry[] data;
        private SortingHandler c;

        public QSTask(int left, int right, DataEntry[] data, SortingHandler c) {
            this.c = c;
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public Long call() throws Exception {
            quicksort(left, right, data, c);
            return System.currentTimeMillis();
        }
    }
}
