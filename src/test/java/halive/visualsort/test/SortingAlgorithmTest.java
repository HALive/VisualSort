package halive.visualsort.test;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.datageneration.impl.RandomDataGenerator;
import halive.visualsort.core.plugins.CorePlugin;
import halive.visualsort.core.sorting.SortingAlgorithm;
import halive.visualsort.core.sorting.impl.SlowSort;
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
        handler.setDataGenerator(new RandomDataGenerator());
        handler.setSortingAlgorithm(algo);
        for (int i = 0; i < dataEntries.length; i++) {
            dataEntries[i] = new DataEntry(1);
        }
        handler.getDataGenerator().generateData(dataEntries, 1000);
        handler.setEntries(dataEntries);
    }

    @Test
    public void testSortingAlgorithms() {
        //Sort the generated Array
        handler.getCurrentAlgorithm().doSort(dataEntries, handler);
        //Check if the Array is Sorted
        isSorted(dataEntries, handler);
    }

    public static void isSorted(DataEntry[] dataEntries, SortingHandler handler) {
        for (int i = 0; i < dataEntries.length - 1; i++) {
            assertTrue(handler.getCurrentAlgorithm().getName() + ": Has not sorted Properly" +
                            "at Index " + i,
                    !(dataEntries[i].getValue() > dataEntries[i + 1].getValue()));
        }
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Class<? extends SortingAlgorithm>> getAlgorithms() {
        return Arrays.asList(new CorePlugin().getSortingAlgorithmClasses());
    }
}
