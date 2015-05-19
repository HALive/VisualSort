package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;

public class TriangleGenerator extends FunctionGenerator{
    public TriangleGenerator() {
        super("Triangle", " ");
    }

    @Override
    public double func(int x, int max) {
        int topVal = SortingHandler.MAX_HEIGHT_VAL;
        int func = topVal-Math.abs(-1*(x-300)*topVal/(max/2));
        return func;
    }
}
