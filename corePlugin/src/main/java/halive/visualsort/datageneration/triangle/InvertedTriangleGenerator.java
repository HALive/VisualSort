/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration.triangle;

import halive.visualsort.core.algorithms.datageneration.FunctionGenerator;

public class InvertedTriangleGenerator extends FunctionGenerator {

    public InvertedTriangleGenerator() {
        super("Inverted triangle", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        double func = (Math.abs((x - len / 2) * (max / (len / 2.0))));
        return func;
    }

    @Override
    public String getCategory() {
        return "Triangle";
    }
}
