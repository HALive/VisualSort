package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;

public class LinearAscendingGenerator extends FunctionGenerator {

    public LinearAscendingGenerator() {
        super("Linear ascending", " ");
    }

    @Override
    public double func(int x, int max) {
        int topVal = SortingHandler.MAX_HEIGHT_VAL;
        return x * topVal / max;
    }
}
