package halive.visualsort.core.datageneration;

import halive.visualsort.core.SortingHandler;
import halive.visualsort.local.LocalizationEntries;

public class LinearAscendingGnerator extends FunctionGenerator{

    public LinearAscendingGnerator() {
        super("Linear ascending" , " ");
    }

    @Override
    public double func(int x, int max) {
        int topVal = SortingHandler.MAX_HEIGHT_VAL;
        return x*topVal/max;
    }
}
