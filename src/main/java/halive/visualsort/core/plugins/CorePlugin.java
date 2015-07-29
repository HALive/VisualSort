package halive.visualsort.core.plugins;

import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.datageneration.InvertedTriangleGenerator;
import halive.visualsort.core.datageneration.LinearAscendingGenerator;
import halive.visualsort.core.datageneration.LinearDescendingGenerator;
import halive.visualsort.core.datageneration.NegativeParabolicGenerator;
import halive.visualsort.core.datageneration.PositiveParabolicGenerator;
import halive.visualsort.core.datageneration.RandomDataGenerator;
import halive.visualsort.core.datageneration.SawtoohGenerator;
import halive.visualsort.core.datageneration.SineGenerator;
import halive.visualsort.core.datageneration.TriangleGenerator;
import halive.visualsort.core.sorting.BiDiBubbleSort;
import halive.visualsort.core.sorting.BitonicMergeSort;
import halive.visualsort.core.sorting.BubbleSort;
import halive.visualsort.core.sorting.GnomeSort;
import halive.visualsort.core.sorting.MergeSort;
import halive.visualsort.core.sorting.QuickSortR1;
import halive.visualsort.core.sorting.QuickSortR2;
import halive.visualsort.core.sorting.SelectionSort;
import halive.visualsort.core.sorting.SortingAlgorithm;

public class CorePlugin implements IVisualSortPlugin {
    @Override
    public String getPluginName() {
        return "VSCore";
    }

    @Override
    public Class<? extends DataGenerator>[] getDataGeneratorClasses() {
        return new Class[] {InvertedTriangleGenerator.class, LinearAscendingGenerator.class,
                LinearDescendingGenerator.class, NegativeParabolicGenerator.class, PositiveParabolicGenerator.class,
                RandomDataGenerator.class, SawtoohGenerator.class, SineGenerator.class, TriangleGenerator.class};
    }

    @Override
    public Class<? extends SortingAlgorithm>[] getSortingAlgorithmClasses() {
        return new Class[] {BubbleSort.class, QuickSortR1.class, QuickSortR2.class,
                BiDiBubbleSort.class, BitonicMergeSort.class, GnomeSort.class, MergeSort.class,
                SelectionSort.class};
    }
}
