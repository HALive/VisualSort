/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.datageneration;

import halive.visualsort.core.DataEntry;

/**
 * This Abstract Subclass Generates Data from a Mathematical Function like f(x) = x
 *
 * @author HALive
 */
public abstract class FunctionGenerator extends DataGenerator {

    /**
     * See DataGenerator
     *
     * @param name        See Data Generator
     * @param description See Data Generator
     */
    public FunctionGenerator(String name, String description) {
        super(name, description);
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        for (int i = 0; i < entries.length; i++) {
            entries[i].setValue((int) func(i, entries.length, maxvalue));
        }
    }

    /**
     * This method returns a doubleValue for a specific x > 0.
     * the Returned value can be anything as long as it is greater or equal to zero and is not larger then max
     *
     * @param x         the current x to calculate from
     * @param len       the Length of the array
     * @param maxHeight the Maximum Value of the element
     * @return the Calculated value
     */
    public abstract double func(int x, int len, int maxHeight);
}
