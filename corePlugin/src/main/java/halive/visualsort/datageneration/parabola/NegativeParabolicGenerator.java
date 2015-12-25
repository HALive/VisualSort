/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration.parabola;

import halive.visualsort.core.datageneration.FunctionGenerator;

public class NegativeParabolicGenerator extends FunctionGenerator {

    public NegativeParabolicGenerator() {
        super("Negative parabola", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        double c = max / Math.pow(len / 2, 2.0D);
        int x1 = x - len / 2;
        return -1 * (c * x1 * x1) + max;
    }

    @Override
    public String getCategory() {
        return "Parabola";
    }
}
