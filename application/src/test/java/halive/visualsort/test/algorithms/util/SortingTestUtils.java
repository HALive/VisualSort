/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.test.algorithms.util;

import halive.visualsort.CorePlugin;
import halive.visualsort.QuickSortPlugin;
import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import static org.junit.Assert.assertTrue;

public class SortingTestUtils {

    public static final Class[] pluginsToTest = new Class[]{
            CorePlugin.class,
            QuickSortPlugin.class
    };

    public static void isSorted(DataEntry[] dataEntries, SortingHandler handler) {
        for (int i = 0; i < dataEntries.length - 1; i++) {
            assertTrue(handler.getSortingAlgorithm().getName() + ": Has not sorted Properly" +
                            "at Index " + i,
                    !(dataEntries[i].getValue() > dataEntries[i + 1].getValue()));
        }
    }

    public static int[] countValues(int max, DataEntry[] data) {
        int[] contents = new int[max + 1];
        for (DataEntry entry : data) {
            contents[entry.getValue()]++;
        }
        return contents;
    }

    public static void compareCountArrays(int[] a1, int[] a2) {
        assertTrue("Invalid Length", a1.length == a2.length);
        for (int i = 0; i < a1.length; i++) {
            assertTrue(String.format("Position %d is invalid", i), a1[i] == a2[i]);
        }
    }
}
