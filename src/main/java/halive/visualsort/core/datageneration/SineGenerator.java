package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.local.LocalizationEntries;

public class SineGenerator extends DataGenerator{
    public SineGenerator() {
        super("Sine", " ");
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        double amplitude = (double) SortingHandler.MAX_HEIGHT_VAL/ 2.0E00;
        for (int i = 0; i < entries.length; i++) {
            double value = amplitude + Math.sin(0.01*i)*(amplitude-10);
            entries[i].setValue((int) value);
        }
    }
}
