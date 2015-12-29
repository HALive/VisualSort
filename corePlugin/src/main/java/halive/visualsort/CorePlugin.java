/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort;

import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.core.sorting.SortingAlgorithm;
import halive.visualsort.datageneration.triangle.InvertedTriangleGenerator;
import halive.visualsort.datageneration.linear.LinearAscendingGenerator;
import halive.visualsort.datageneration.linear.LinearDescendingGenerator;
import halive.visualsort.datageneration.parabola.NegativeParabolicGenerator;
import halive.visualsort.datageneration.parabola.PositiveParabolicGenerator;
import halive.visualsort.datageneration.random.RandomDataGenerator;
import halive.visualsort.datageneration.misc.SawtoothGenerator;
import halive.visualsort.datageneration.misc.SineGenerator;
import halive.visualsort.datageneration.triangle.TriangleGenerator;
import halive.visualsort.sortingalgorithms.api.APIParallelSort;
import halive.visualsort.sortingalgorithms.api.APISort;
import halive.visualsort.sortingalgorithms.bubblesort.BiDiBubbleSort;
import halive.visualsort.sortingalgorithms.others.CircleSort;
import halive.visualsort.sortingalgorithms.others.CycleSort;
import halive.visualsort.sortingalgorithms.others.PancakeSort;
import halive.visualsort.sortingalgorithms.selectionsort.BiDiSelectionSort;
import halive.visualsort.sortingalgorithms.others.BinaryTreeSort;
import halive.visualsort.sortingalgorithms.bubblesort.BubbleSort;
import halive.visualsort.sortingalgorithms.others.CountingSort;
import halive.visualsort.sortingalgorithms.others.GnomeSort;
import halive.visualsort.sortingalgorithms.others.HeapSort;
import halive.visualsort.sortingalgorithms.others.OddEvenSort;
import halive.visualsort.sortingalgorithms.selectionsort.SelectionSort;
import halive.visualsort.sortingalgorithms.slow.SlowSort;
import halive.visualsort.sortingalgorithms.slow.StoogeSort;
import halive.visualsort.sortingalgorithms.bubblesort.CombSort;
import halive.visualsort.sortingalgorithms.insertion.BinaryInsertionSort;
import halive.visualsort.sortingalgorithms.insertion.InsertionSort;
import halive.visualsort.sortingalgorithms.insertion.ShellSort;
import halive.visualsort.sortingalgorithms.insertion.ShellSortParallel;
import halive.visualsort.sortingalgorithms.mergesort.BitonicMergeSort;
import halive.visualsort.sortingalgorithms.mergesort.MergeSort;
import halive.visualsort.sortingalgorithms.quicksort.ParallelQuickSort;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR1;
import halive.visualsort.sortingalgorithms.quicksort.QuickSortR2;

/**
 * This class Represents the CorePlugin containing the default Datagenerators and Sorting algorithms
 *
 * @author HALive
 */
@SuppressWarnings("unchecked")
public class CorePlugin implements IVisualSortPlugin {

    @Override
    public String getPluginName() {
        return "Core";
    }

    @Override
    public String getPluginInfoFileName() {
        return null;
    }

    @Override
    public Class[] getDataGeneratorClasses() {
        return new Class[]{InvertedTriangleGenerator.class,
                LinearAscendingGenerator.class,
                LinearDescendingGenerator.class,
                NegativeParabolicGenerator.class,
                PositiveParabolicGenerator.class,
                RandomDataGenerator.class,
                SawtoothGenerator.class,
                SineGenerator.class,
                TriangleGenerator.class};
    }

    @Override
    public Class[] getSortingAlgorithmClasses() {
        return new Class[]{
                MergeSort.class,
                BitonicMergeSort.class,
                HeapSort.class,
                OddEvenSort.class,
                BinaryTreeSort.class,
                GnomeSort.class,
                InsertionSort.class,
                ShellSort.class,
                ShellSortParallel.class,
                BinaryInsertionSort.class,
                SelectionSort.class,
                BiDiSelectionSort.class,
                BubbleSort.class,
                BiDiBubbleSort.class,
                CombSort.class,
                SlowSort.class,
                StoogeSort.class,
                CountingSort.class,
                APISort.class,
                APIParallelSort.class,
                PancakeSort.class,
                CircleSort.class,
                CycleSort.class
        };
    }
}
