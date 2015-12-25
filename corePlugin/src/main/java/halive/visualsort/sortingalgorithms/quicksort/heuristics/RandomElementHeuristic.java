/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.heuristics;

import halive.visualsort.sortingalgorithms.quicksort.ParallelQuickSort;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR1;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR2;
import halive.visualsort.sortingalgorithms.quicksort.pivot.QuickSortHeuristic;

public class RandomElementHeuristic {

    public static class QuickSortR1Rand extends QuickSortR1 {

        public QuickSortR1Rand() {
            super(QuickSortHeuristic.RANDOM_ELEMENT);
        }
    }

    public static class QuickSortR2Rand extends QuickSortR2 {

        public QuickSortR2Rand() {
            super(QuickSortHeuristic.RANDOM_ELEMENT);
        }
    }

    public static class ParallelQuickSortRand extends ParallelQuickSort {

        public ParallelQuickSortRand() {
            super(QuickSortHeuristic.RANDOM_ELEMENT);
        }
    }
}
