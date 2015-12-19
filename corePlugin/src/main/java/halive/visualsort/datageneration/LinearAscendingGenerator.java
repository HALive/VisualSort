/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration;

import halive.visualsort.core.datageneration.FunctionGenerator;

public class LinearAscendingGenerator extends FunctionGenerator {

    public LinearAscendingGenerator() {
        super("Linear ascending", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        return x * max / len;
    }
}
