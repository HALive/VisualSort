/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.pivot;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

public interface IQSPivotHeuristic {

    int getPivotElementPositon(int left, int right, DataEntry[] entries, SortingHandler h);
}
