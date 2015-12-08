package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;

public class InvertedTriangleGenerator extends FunctionGenerator {
    public InvertedTriangleGenerator() {
        super("Inverted triangle", " ");
    }

    @Override
    public double func(int x, int max) {
        int topVal = SortingHandler.MAX_HEIGHT_VAL;
        int func = Math.abs((x - 300) * topVal / (max / 2));
        return func;
    }
}
