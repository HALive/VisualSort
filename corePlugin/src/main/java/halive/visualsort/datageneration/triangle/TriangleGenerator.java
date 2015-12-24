/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration.triangle;

import halive.visualsort.core.datageneration.FunctionGenerator;

public class TriangleGenerator extends FunctionGenerator {

    public TriangleGenerator() {
        super("Triangle", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        double func = max - (Math.abs((x - len / 2) * (max / (len / 2.0))));
        return func;
    }

    @Override
    public String getCategory() {
        return "Triangle";
    }
}
