/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.test.util;

import halive.visualsort.core.algorithms.datageneration.DataGenerator;
import halive.visualsort.core.algorithms.options.OptionDialogResult;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;
import halive.visualsort.core.interfaces.IAlgorithm;
import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.datageneration.random.RandomDistribution;
import halive.visualsort.sortingalgorithms.mergesort.CombinationMergeSort;
import halive.visualsort.sortingalgorithms.mergesort.MergeSort;
import halive.visualsort.sortingalgorithms.mergesort.MergingMethods;
import halive.visualsort.sortingalgorithms.mergesort.ParallelMergeSort;
import halive.visualsort.sortingalgorithms.slow.SlowSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceGenerator {

    public static Map<Class<? extends IAlgorithm>, IResultGenerator> resultGenerators =
            new HashMap<>();
    static {
        resultGenerators.put(RandomDistribution.class,
                InstanceGenerator::getResultsForRandomDistribution);
        resultGenerators.put(MergeSort.class, InstanceGenerator::getResultsForMergeSort);
        resultGenerators.put(ParallelMergeSort.class, InstanceGenerator::getResultsForMergeSort);
        resultGenerators.put(CombinationMergeSort.class, InstanceGenerator::getResultsForCombinationMerge);
    }
    private static List<OptionDialogResult> getResultsForCombinationMerge
            (List<DataGenerator> dataGenerators, List<SortingAlgorithm> algorithms) {
        List<OptionDialogResult> results = new ArrayList<>();
        for (MergingMethods method : MergingMethods.values()) {
            for (SortingAlgorithm algorithm : algorithms) {
                if (!isInvalidAlgorithm(algorithm, CombinationMergeSort.invalidAlgorithms)) {
                    boolean parallel = false;
                    do {
                        OptionDialogResult result = new OptionDialogResult();
                        result.getData().put(MergeSort.MERGER_KEY, method);
                        result.getData().put(CombinationMergeSort.ALGORITHM_KEY, algorithm);
                        int value = 128;
                        if (algorithm instanceof SlowSort) {
                            value = 16;
                        }
                        result.getData().put(CombinationMergeSort.SUB_ALGORITHM_TOGGLE_KEY, value);
                        result.getData().put(CombinationMergeSort.PARALLEL_KEY, parallel);
                        results.add(result);
                        parallel = !parallel;
                    } while (parallel);
                }
            }
        }
        return results;
    }

    private static boolean isInvalidAlgorithm(SortingAlgorithm s, Class[] invalids) {
        for (Class invalid : invalids) {
            if (invalid.isInstance(s)) {
                return true;
            }
        }
        return false;
    }

    private static List<OptionDialogResult> getResultsForMergeSort
            (List<DataGenerator> dataGenerators, List<SortingAlgorithm> algorithms) {
        List<OptionDialogResult> results = new ArrayList<>();
        for (MergingMethods method : MergingMethods.values()) {
            OptionDialogResult result = new OptionDialogResult();
            result.getData().put(MergeSort.MERGER_KEY, method);
            results.add(result);
        }
        return results;
    }

    private static List<OptionDialogResult> getResultsForRandomDistribution(List<DataGenerator> gens,
                                                                            List<SortingAlgorithm> algorithms) {
        ArrayList<OptionDialogResult> results = new ArrayList<>();
        gens.stream().filter(d -> !(d.hasOptionDialog())).forEach(dataGenerator -> {
            OptionDialogResult result = new OptionDialogResult();
            result.getData().put(RandomDistribution.SELECTED_ALGORITHM_KEY, dataGenerator);
            result.getData().put(RandomDistribution.SHUFFLE_CYLCES_KEY, 1);
            results.add(result);
        });
        return results;
    }

    public static List<DataGenerator> getDataGeneratorInstances()
            throws IllegalAccessException, InstantiationException {
        List<DataGenerator> dataGens = new ArrayList<>();
        List<Class<? extends DataGenerator>> optionDataGens = new ArrayList<>();
        for (Class pClass : SortingTestUtils.pluginsToTest) {
            IVisualSortPlugin plugin = (IVisualSortPlugin) pClass.newInstance();
            for (Class genClass : plugin.getDataGeneratorClasses()) {
                DataGenerator generator = (DataGenerator) genClass.newInstance();
                if (generator.hasOptionDialog()) {
                    optionDataGens.add(genClass);
                    continue;
                }
                dataGens.add(generator);
            }
        }
        List<DataGenerator> optionGenInstances = new ArrayList<>();
        for (Class<? extends DataGenerator> gClass : optionDataGens) {
            InstanceGenerator.IResultGenerator resultGenerator =
                    InstanceGenerator.resultGenerators.get(gClass);
            if (resultGenerator == null) {
                continue;
            }
            List<OptionDialogResult> results = resultGenerator.getResults(dataGens, null);
            for (OptionDialogResult result : results) {
                DataGenerator gen = gClass.newInstance();
                gen.init(result, null);
                optionGenInstances.add(gen);
            }
        }
        dataGens.addAll(optionGenInstances);
        return dataGens;
    }

    public static List<SortingAlgorithm> getSortingAlgorithmInstances()
            throws IllegalAccessException, InstantiationException {
        List<SortingAlgorithm> sortingAlgorithms = new ArrayList<>();
        List<Class<? extends SortingAlgorithm>> optSorter = new ArrayList<>();
        for (Class pClass : SortingTestUtils.pluginsToTest) {
            IVisualSortPlugin plugin = (IVisualSortPlugin) pClass.newInstance();
            for (Class genClass : plugin.getSortingAlgorithmClasses()) {
                SortingAlgorithm generator = (SortingAlgorithm) genClass.newInstance();
                if (generator.hasOptionDialog()) {
                    optSorter.add(genClass);
                    continue;
                }
                sortingAlgorithms.add(generator);
            }
        }
        List<SortingAlgorithm> optSorterInstances = new ArrayList<>();
        for (Class<? extends SortingAlgorithm> gClass : optSorter) {
            System.out.println("Iterating");
            InstanceGenerator.IResultGenerator resultGenerator =
                    InstanceGenerator.resultGenerators.get(gClass);
            if (resultGenerator == null) {
                continue;
            }
            List<OptionDialogResult> results = resultGenerator.getResults(null, sortingAlgorithms);
            for (OptionDialogResult result : results) {
                SortingAlgorithm gen = gClass.newInstance();
                gen.init(result, null);
                optSorterInstances.add(gen);
            }
        }
        sortingAlgorithms.addAll(optSorterInstances);
        return sortingAlgorithms;
    }

    public interface IResultGenerator {

        List<OptionDialogResult> getResults(List<DataGenerator> gens, List<SortingAlgorithm> algorithms);
    }
}
