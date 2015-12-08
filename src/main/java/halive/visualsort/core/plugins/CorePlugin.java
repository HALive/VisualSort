package halive.visualsort.core.plugins;

import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.datageneration.impl.InvertedTriangleGenerator;
import halive.visualsort.core.datageneration.impl.LinearAscendingGenerator;
import halive.visualsort.core.datageneration.impl.LinearDescendingGenerator;
import halive.visualsort.core.datageneration.impl.NegativeParabolicGenerator;
import halive.visualsort.core.datageneration.impl.PositiveParabolicGenerator;
import halive.visualsort.core.datageneration.impl.RandomDataGenerator;
import halive.visualsort.core.datageneration.impl.SawtoothGenerator;
import halive.visualsort.core.datageneration.impl.SineGenerator;
import halive.visualsort.core.datageneration.impl.TriangleGenerator;
import halive.visualsort.core.sorting.impl.BiDiBubbleSort;
import halive.visualsort.core.sorting.impl.BiDiSelectionSort;
import halive.visualsort.core.sorting.impl.BitonicMergeSort;
import halive.visualsort.core.sorting.impl.BubbleSort;
import halive.visualsort.core.sorting.impl.CountingSort;
import halive.visualsort.core.sorting.impl.GnomeSort;
import halive.visualsort.core.sorting.impl.MergeSort;
import halive.visualsort.core.sorting.impl.QuickSortR1;
import halive.visualsort.core.sorting.impl.QuickSortR2;
import halive.visualsort.core.sorting.impl.SelectionSort;
import halive.visualsort.core.sorting.impl.SlowSort;
import halive.visualsort.core.sorting.SortingAlgorithm;

/**
 * This class Represents the CorePlugin containing the default Datagenerators and Sorting algorithms
 *
 * @author HALive
 */
public class CorePlugin implements IVisualSortPlugin {
    @Override
    public String getPluginName() {
        return "VSCore";
    }

    @Override
    public Class<? extends DataGenerator>[] getDataGeneratorClasses() {
        return new Class[] {InvertedTriangleGenerator.class,
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
    public Class<? extends SortingAlgorithm>[] getSortingAlgorithmClasses() {
        Class<? extends SortingAlgorithm>[] algorithms = new Class[] {
                QuickSortR1.class,
                QuickSortR2.class,
                MergeSort.class,
                BitonicMergeSort.class,
                GnomeSort.class,
                SelectionSort.class,
                BiDiSelectionSort.class,
                BubbleSort.class,
                BiDiBubbleSort.class,
                SlowSort.class,
                CountingSort.class};
        return algorithms;
    }
}
