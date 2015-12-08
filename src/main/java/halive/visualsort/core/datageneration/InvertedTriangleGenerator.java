package halive.visualsort.core.datageneration;

public class InvertedTriangleGenerator extends FunctionGenerator {
    public InvertedTriangleGenerator() {
        super("Inverted triangle", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        double func = (Math.abs((x-len/2)*(max/(len/2.0))));
        return func;
    }
}
