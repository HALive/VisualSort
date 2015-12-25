/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort;

import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.sortingalgorithms.quicksort.heuristics.LastElementHeuristic;
import halive.visualsort.sortingalgorithms.quicksort.heuristics.RandomElementHeuristic;

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
                LastElementHeuristic.QuickSortR1Last.class,
                LastElementHeuristic.QuickSortR2Last.class,
                LastElementHeuristic.ParallelQuickSortLast.class,
                RandomElementHeuristic.QuickSortR1Rand.class,
                RandomElementHeuristic.QuickSortR2Rand.class,
                RandomElementHeuristic.ParallelQuickSortRand.class
        };
    }
}
