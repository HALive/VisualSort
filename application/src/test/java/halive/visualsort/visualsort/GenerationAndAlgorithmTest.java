/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.visualsort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.datageneration.DataGenerator;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;
import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.sortingalgorithms.slow.SlowSort;
import halive.visualsort.visualsort.util.SortingTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Combined Test, this Test tests every Sorting Algorithm wth every DataGenerator
 */
@RunWith(Parameterized.class)
public class GenerationAndAlgorithmTest {

    private SortingHandler handler;
    private Combination<DataGenerator, SortingAlgorithm> comb;
    private DataEntry[] entries;

    public GenerationAndAlgorithmTest(Combination<DataGenerator, SortingAlgorithm> comb) {
        this.comb = comb;
    }

    @Before
    public void setUp() throws Exception {
        handler = new SortingHandler(null);
        handler.setSortingAlgorithm(comb.b);
        handler.setDataGenerator(comb.a);
        int amtEntries = comb.b instanceof SlowSort ? 100 : 1000;
        this.entries = new DataEntry[amtEntries];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new DataEntry(1, handler);
        }
        handler.setEntries(entries);
    }

    @Test(timeout = 60000)
    public void testGeneratorAndSorter() throws Exception {
        System.out.println(comb + ": Generating Data");
        comb.a.generateData(entries, DataGeneratorTest.MAX_VALUE);
        int[] v1 = SortingTestUtils.countValues(DataGeneratorTest.MAX_VALUE, entries);
        System.out.println(comb + ": Validating gernerated Data");
        assertTrue("The Generated data is Invalid.", DataGeneratorTest.isDataValid(entries));
        System.out.println(comb + ": Sorting");
        comb.b.doSort(entries, handler);
        System.out.println(comb + ": Validating Sorted Data");
        SortingTestUtils.isSorted(entries, handler);
        SortingTestUtils.compareCountArrays(v1,
                SortingTestUtils.countValues(DataGeneratorTest.MAX_VALUE, entries));
        System.out.println(comb + ": Test Successful");
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Combination<DataGenerator, SortingAlgorithm>> data()
            throws IllegalAccessException, InstantiationException {
        ArrayList<Combination<DataGenerator, SortingAlgorithm>> combinations = new ArrayList<>();
        ArrayList<SortingAlgorithm> sortingAlgorithms = new ArrayList<>();
        ArrayList<DataGenerator> dataGenerators = new ArrayList<>();
        for (int i = 0; i < SortingTestUtils.pluginsToTest.length; i++) {
            IVisualSortPlugin plugin = (IVisualSortPlugin) SortingTestUtils.pluginsToTest[i].newInstance();
            for (Class c : plugin.getDataGeneratorClasses()) {
                DataGenerator gen = (DataGenerator) c.newInstance();
                dataGenerators.add(gen);
            }
            for (Class a : plugin.getSortingAlgorithmClasses()) {
                SortingAlgorithm alg = (SortingAlgorithm) a.newInstance();
                sortingAlgorithms.add(alg);
            }
        }
        dataGenerators.forEach(dataGenerator -> {
            sortingAlgorithms.forEach(sortingAlgorithm -> {
                Combination<DataGenerator, SortingAlgorithm> combination =
                        new Combination<>(dataGenerator, sortingAlgorithm);
                combinations.add(combination);
            });
        });
        return combinations;
    }

    private static class Combination<A, B> {

        private A a;
        private B b;

        public Combination(A a, B b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return a + " - " + b;
        }
    }
}
