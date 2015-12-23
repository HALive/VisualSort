/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.jokealgo;

import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.core.sorting.SortingAlgorithm;

public class JokeAlgorithms implements IVisualSortPlugin {

    @Override
    public String getPluginName() {
        return "Joke Algorithms";
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
    public Class<? extends SortingAlgorithm>[] getSortingAlgorithmClasses() {
        return new Class[]{
                BogoSort.class
        };
    }
}
