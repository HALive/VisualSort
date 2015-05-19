package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.local.LocalizationEntries;

public class SawtoohGenerator extends DataGenerator{
    public SawtoohGenerator() {
        super("Sawtooth", " ");
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        double sawLength = entries.length/20.0D;
        double stepHeight = SortingHandler.MAX_HEIGHT_VAL/sawLength;
        double lastPos = 0;
        for (int i = 0; i < entries.length; i++) {
            entries[i].setValue((int) lastPos);
            lastPos = (i%sawLength)*stepHeight;
        }
    }
}
