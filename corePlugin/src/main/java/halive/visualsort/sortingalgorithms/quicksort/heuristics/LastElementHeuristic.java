/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.heuristics;

import halive.visualsort.sortingalgorithms.quicksort.ParallelQuickSort;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR1;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR2;
import halive.visualsort.sortingalgorithms.quicksort.pivot.QuickSortHeuristic;

public class LastElementHeuristic {

    public static class QuickSortR1Last extends QuickSortR1 {

        public QuickSortR1Last() {
            super(QuickSortHeuristic.LAST_ELEMENT);
        }
    }

    public static class QuickSortR2Last extends QuickSortR2 {

        public QuickSortR2Last() {
            super(QuickSortHeuristic.LAST_ELEMENT);
        }
    }

    public static class ParallelQuickSortLast extends ParallelQuickSort {

        public ParallelQuickSortLast() {
            super(QuickSortHeuristic.LAST_ELEMENT);
        }
    }
}
