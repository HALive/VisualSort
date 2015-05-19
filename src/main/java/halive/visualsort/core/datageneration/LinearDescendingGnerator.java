package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;
import halive.visualsort.local.LocalizationEntries;

public class LinearDescendingGnerator extends FunctionGenerator{

    public LinearDescendingGnerator() {
        super("Linear descending", " ");
    }

    @Override
    public double func(int x, int max) {
        int topVal = SortingHandler.MAX_HEIGHT_VAL;
        return topVal-x*topVal/max;
    }
}
