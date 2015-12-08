package halive.visualsort.core.datageneration.impl;

import halive.visualsort.core.datageneration.FunctionGenerator;

public class PositiveParabolicGenerator extends FunctionGenerator {
    public PositiveParabolicGenerator() {
        super("Positive parabola", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        double c = max / Math.pow(len / 2, 2.0D);
        int x1 = x - len / 2;
        return c * x1 * x1;
    }
}
