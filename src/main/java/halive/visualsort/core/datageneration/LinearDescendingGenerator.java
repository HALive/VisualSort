package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;

public class LinearDescendingGenerator extends FunctionGenerator {

    public LinearDescendingGenerator() {
        super("Linear descending", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        return max - x * max / len;
    }
}
