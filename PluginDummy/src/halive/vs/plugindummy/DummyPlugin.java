package halive.vs.plugindummy;

import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.core.sorting.SortingAlgorithm;

public class DummyPlugin implements IVisualSortPlugin {
    @Override
    public String getPluginName() {
        return "Dummy Plugin";
    }

    @Override
    public Class<? extends DataGenerator>[] getDataGeneratorClasses() {
        return new Class[] {DummyGen.class};
    }

    @Override
    public Class<? extends SortingAlgorithm>[] getSortingAlgorithmClasses() {
        return new Class[] {DummySort.class};
    }
}
