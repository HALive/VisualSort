/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.heuristics;

import halive.visualsort.sortingalgorithms.quicksort.ParallelQuickSort;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR2;
import halive.visualsort.sortingalgorithms.quicksort.pivot.QuickSortHeuristic;

public class MedianOf3Heuristic {

    public static class QuickSortR2Med3 extends QuickSortR2 {

        public QuickSortR2Med3() {
            super(QuickSortHeuristic.MEDIAN_OF_THREE);
        }
    }

    public static class ParallelQuickSortMed3 extends ParallelQuickSort {

        public ParallelQuickSortMed3() {
            super(QuickSortHeuristic.MEDIAN_OF_THREE);
        }
    }
}
