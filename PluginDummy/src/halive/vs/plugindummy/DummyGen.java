package halive.vs.plugindummy;

import halive.visualsort.core.datageneration.FunctionGenerator;

public class DummyGen extends FunctionGenerator {
    public DummyGen() {
        super("DummyGen", "");
    }

    @Override
    public double func(int i, int i1) {
        return ((double) i/i1);
    }
}
