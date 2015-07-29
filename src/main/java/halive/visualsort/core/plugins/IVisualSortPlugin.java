package halive.visualsort.core.plugins;

import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.sorting.SortingAlgorithm;

public interface IVisualSortPlugin {
    String getPluginName();
    Class<? extends DataGenerator>[] getDataGeneratorClasses();
    Class<? extends SortingAlgorithm>[] getSortingAlgorithmClasses();
}
