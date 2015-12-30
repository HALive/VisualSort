/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration.linear;

import halive.visualsort.core.algorithms.datageneration.FunctionGenerator;

public class LinearAscendingGenerator extends FunctionGenerator {

    public LinearAscendingGenerator() {
        super("Linear ascending", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        return x * max / len;
    }

    @Override
    public String getCategory() {
        return "Linear";
    }
}
