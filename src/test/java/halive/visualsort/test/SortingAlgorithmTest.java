package halive.visualsort.test;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.datageneration.RandomDataGenerator;
import halive.visualsort.core.plugins.CorePlugin;
import halive.visualsort.core.sorting.SlowSort;
import halive.visualsort.core.sorting.SortingAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class SortingAlgorithmTest {
    private SortingAlgorithm algo;
    private SortingHandler handler;
    private DataEntry[] dataEntries = new DataEntry[1000];

    public SortingAlgorithmTest(Class<? extends SortingAlgorithm> algo)
            throws IllegalAccessException, InstantiationException {
        this.algo = algo.newInstance();
        //Reduce the amount of entries for SlowSort due to its horrible performance
        if(this.algo instanceof SlowSort) {
            dataEntries = new DataEntry[100];
        }
    }

    @Before
    public void stUp() {
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
        assertTrue(handler.getCurrentAlgorithm().getName() + " Did not sort Properly", isSorted());
    }

    private boolean isSorted() {
        for (int i = 0; i < dataEntries.length - 1; i++) {
            if (dataEntries[i].getValue() > dataEntries[i + 1].getValue()) {
                return false;
            }
        }
        return true;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Class<? extends SortingAlgorithm>> getAlgorithms() {
        return Arrays.asList(new CorePlugin().getSortingAlgorithmClasses());
    }
}
