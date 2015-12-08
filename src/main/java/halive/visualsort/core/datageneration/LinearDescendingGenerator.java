package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;

public class LinearDescendingGenerator extends FunctionGenerator {

    public LinearDescendingGenerator() {
        super("Linear descending", " ");
    }

    @Override
    public double func(int x, int max) {
        int topVal = SortingHandler.MAX_HEIGHT_VAL;
        return topVal - x * topVal / max;
    }
}
