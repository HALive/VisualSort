package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.plugins.CorePlugin;
import halive.visualsort.core.sorting.SortingAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class DataGeneratorTest {

    private static final int MAX_VALUE = 1000;

    private DataGenerator dataGen;
    private DataEntry[] dataEntries = new DataEntry[1000];

    public DataGeneratorTest(Class<? extends DataGenerator> gen)
            throws IllegalAccessException, InstantiationException {
        dataGen = gen.newInstance();
    }

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < dataEntries.length; i++) {
            dataEntries[i] = new DataEntry(1);
        }
    }

    @Test
    public void testDataGenerators() throws Exception {
        dataGen.generateData(dataEntries, MAX_VALUE);
        assertTrue(dataGen.getName()+" Did not generate valid data!", isDataValid());
    }

    public boolean isDataValid() {
        for (int i = 0; i < dataEntries.length; i++) {
            DataEntry e = dataEntries[i];
            if(e.getValue() > MAX_VALUE || e.getValue() < 0) {
                return false;
            }
        }
        return true;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Class<? extends DataGenerator>> getAlgorithms() {
        return Arrays.asList(new CorePlugin().getDataGeneratorClasses());
    }
}