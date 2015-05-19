package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;
import halive.visualsort.local.LocalizationEntries;

public class PositiveParabolicGenerator extends FunctionGenerator{
    public PositiveParabolicGenerator() {
        super("Positive parabola", " ");
    }

    @Override
    public double func(int x, int max) {
        double c = SortingHandler.MAX_HEIGHT_VAL/Math.pow(max / 2, 2.0D);
        int x1 = x - max/2;
        return c*x1*x1;
    }
}
