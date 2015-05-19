package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;

public abstract class FunctionGenerator extends DataGenerator{
    public FunctionGenerator(String name, String description) {
        super(name, description);
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        for (int i = 0; i < entries.length; i++) {
            entries[i].setValue((int) func(i, entries.length));
        }
    }

    public abstract double func(int x, int max);
}
