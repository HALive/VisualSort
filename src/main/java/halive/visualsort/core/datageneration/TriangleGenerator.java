package halive.visualsort.core.datageneration;

public class TriangleGenerator extends FunctionGenerator {
    public TriangleGenerator() {
        super("Triangle", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        double func = max - (Math.abs((x-len/2)*(max/(len/2.0))));
        return func;
    }
}
