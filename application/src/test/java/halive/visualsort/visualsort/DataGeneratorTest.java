/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.visualsort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.datageneration.DataGenerator;
import halive.visualsort.visualsort.util.InstanceGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * This test is ued to test he Validity of a data Generator
 */
@RunWith(Parameterized.class)
public class DataGeneratorTest {

    public static final int MAX_VALUE = 1000;

    private DataGenerator dataGen;
    private DataEntry[] dataEntries = new DataEntry[1000];

    public DataGeneratorTest(DataGenerator gen)
            throws IllegalAccessException, InstantiationException {
        dataGen = gen;
    }

    @Before
    public void setUp() throws Exception {
        SortingHandler handler = new SortingHandler(null);
        for (int i = 0; i < dataEntries.length; i++) {
            dataEntries[i] = new DataEntry(1, handler);
        }
    }

    @Test
    public void testDataGenerators() throws Exception {
        System.out.println(dataGen.getName() + " Generating Data");
        dataGen.generateData(dataEntries, MAX_VALUE);
        assertTrue(dataGen.getName() + " Did not generate valid data!", isDataValid(dataEntries));
        System.out.println("Test Sucessful!");
    }

    public static boolean isDataValid(DataEntry[] dataEntries) {
        for (int i = 0; i < dataEntries.length; i++) {
            DataEntry e = dataEntries[i];
            if (e.getValue() > MAX_VALUE || e.getValue() < 0) {
                return false;
            }
        }
        return true;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<DataGenerator> getAlgorithms()
            throws IllegalAccessException, InstantiationException {
        return InstanceGenerator.getDataGeneratorInstances();
    }
}