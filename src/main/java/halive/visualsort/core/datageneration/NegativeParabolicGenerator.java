package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;

public class NegativeParabolicGenerator extends FunctionGenerator {
    public NegativeParabolicGenerator() {
        super("Negative parabola", " ");
    }

    @Override
    public double func(int x, int max) {
        double c = SortingHandler.MAX_HEIGHT_VAL / Math.pow(max / 2, 2.0D);
        int x1 = x - max / 2;
        return -1 * (c * x1 * x1) + SortingHandler.MAX_HEIGHT_VAL;
    }
}
