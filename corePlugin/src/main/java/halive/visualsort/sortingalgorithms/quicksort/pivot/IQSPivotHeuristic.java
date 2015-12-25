/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.pivot;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

public interface IQSPivotHeuristic {

    /**
     * Returns the Position Of the Pivot Element in the Area
     *
     * @param left    the positon to start the search at
     * @param right   the Positon to end the search at
     * @param entries the Data to look for the Pivot element
     * @param h       the Sorting handler, for counting Comparisons
     * @return the Position of the Pivot element
     */
    int getPivotElementPositon(int left, int right, DataEntry[] entries, SortingHandler h);
}
