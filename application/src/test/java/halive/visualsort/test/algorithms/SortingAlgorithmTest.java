/*
 * Copyright (c) HALive 2016
 * See LICENCE For Licence information.
 */

package halive.visualsort.test.algorithms;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;
import halive.visualsort.datageneration.misc.SineGenerator;
import halive.visualsort.sortingalgorithms.slow.SlowSort;
import halive.visualsort.test.algorithms.util.InstanceGenerator;
import halive.visualsort.test.algorithms.util.SortingTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

/**
 * This test tests if every SortingAlgorithm sorts Properly
 */
@RunWith(Parameterized.class)
public class SortingAlgorithmTest {

    private SortingAlgorithm algo;
    private SortingHandler handler;
    private DataEntry[] dataEntries = new DataEntry[1000];

    public SortingAlgorithmTest(SortingAlgorithm algo) {
        this.algo = algo;
        //Reduce the amount of entries for SlowSort due to its horrible performance
        if (this.algo instanceof SlowSort) {
            dataEntries = new DataEntry[100];
        }
    }

    @Before
    public void setUp() {
        handler = new SortingHandler(null);
        handler.setDelay(0);
        handler.setDataGenerator(new SineGenerator());
        handler.setSortingAlgorithm(algo);
        for (int i = 0; i < dataEntries.length; i++) {
            dataEntries[i] = new DataEntry(1, handler);
        }
        handler.getDataGenerator().generateData(dataEntries, DataGeneratorTest.MAX_VALUE);
        handler.setEntries(dataEntries);
    }

    @Test(timeout = 60000)
    public void testSortingAlgorithms() {
        //Sort the generated Array
        int[] v1 = SortingTestUtils.countValues(DataGeneratorTest.MAX_VALUE, dataEntries);
        System.out.println(algo + ": Sorting");
        handler.getSortingAlgorithm().doSort(dataEntries, handler, 0, dataEntries.length);
        //Check if the Array is Sorted
        System.out.println(algo + ": Validating");
        SortingTestUtils.isSorted(dataEntries, handler);
        SortingTestUtils.compareCountArrays(v1, SortingTestUtils.countValues(DataGeneratorTest.MAX_VALUE, dataEntries));
        System.out.println(algo + ": Passed!");
    }


    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<SortingAlgorithm> getAlgorithms() throws IllegalAccessException, InstantiationException {
        return InstanceGenerator.getSortingAlgorithmInstances();
    }
}
