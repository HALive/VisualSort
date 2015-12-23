/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.visualsort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.datageneration.SineGenerator;
import halive.visualsort.CorePlugin;
import halive.visualsort.core.sorting.SortingAlgorithm;
import halive.visualsort.sortingalgorithms.SlowSort;
import halive.visualsort.visualsort.util.SortingTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * This test tests if every SortingAlgorithm sorts Properly
 */
@RunWith(Parameterized.class)
public class SortingAlgorithmTest {

    private SortingAlgorithm algo;
    private SortingHandler handler;
    private DataEntry[] dataEntries = new DataEntry[1000];

    public SortingAlgorithmTest(Class<? extends SortingAlgorithm> algo)
            throws IllegalAccessException, InstantiationException {
        this.algo = algo.newInstance();
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
            dataEntries[i] = new DataEntry(1);
        }
        handler.getDataGenerator().generateData(dataEntries, DataGeneratorTest.MAX_VALUE);
        handler.setEntries(dataEntries);
    }

    @Test
    public void testSortingAlgorithms() {
        //Sort the generated Array
        handler.getCurrentAlgorithm().doSort(dataEntries, handler);
        int[] v1 = SortingTestUtils.countValues(DataGeneratorTest.MAX_VALUE, dataEntries);
        //Check if the Array is Sorted
        SortingTestUtils.isSorted(dataEntries, handler);
        SortingTestUtils.compareCountArrays(v1, SortingTestUtils.countValues(DataGeneratorTest.MAX_VALUE, dataEntries));
    }


    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Class<? extends SortingAlgorithm>> getAlgorithms() {
        return Arrays.asList(new CorePlugin().getSortingAlgorithmClasses());
    }
}
