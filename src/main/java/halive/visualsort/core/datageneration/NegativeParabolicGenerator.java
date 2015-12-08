package halive.visualsort.core.datageneration;

public class NegativeParabolicGenerator extends FunctionGenerator {
    public NegativeParabolicGenerator() {
        super("Negative parabola", " ");
    }

    @Override
    public double func(int x, int len, int max) {
        double c = max / Math.pow(len / 2, 2.0D);
        int x1 = x - len / 2;
        return -1 * (c * x1 * x1) + max;
    }
}
