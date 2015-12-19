/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.datageneration.impl;

import halive.visualsort.core.datageneration.FunctionGenerator;

public class LinearDescendingGenerator extends FunctionGenerator {

    public LinearDescendingGenerator() {
        super("Linear descending", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        return max - x * max / len;
    }
}
