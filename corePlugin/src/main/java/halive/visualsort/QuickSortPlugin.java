/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort;

import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.sortingalgorithms.quicksort.ParallelQuickSort;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR1;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR2;

/**
 * This Plugin is Loaded Like the Core Plugin and contains all QuickSort based Algorithms
 * Due to the Implementation of different Heuristics for detemining the Pivot Element,
 * The Amount of Quick Sort Based algorithms will "Explode". They Are Externaized to
 * Allow the Ordering in different Heuristics
 */
public class QuickSortPlugin implements IVisualSortPlugin {

    @Override
    public String getPluginName() {
        return "QuickSort";
    }

    @Override
    public String getPluginInfoFileName() {
        return null;
    }

    @Override
    public Class[] getDataGeneratorClasses() {
        return new Class[0];
    }

    @Override
    public Class[] getSortingAlgorithmClasses() {
        return new Class[]{
                QuickSortR1.class,
                QuickSortR2.class,
                ParallelQuickSort.class
        };
    }
}
