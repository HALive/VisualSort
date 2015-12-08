package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;

public class LinearAscendingGenerator extends FunctionGenerator {

    public LinearAscendingGenerator() {
        super("Linear ascending", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        return x * max / len;
    }
}
