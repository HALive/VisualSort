package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;
import halive.visualsort.local.LocalizationEntries;

import java.util.Random;

public class RandomDataGenerator extends DataGenerator {

    public RandomDataGenerator() {
        super("Random", " ");
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        Random rnd = new Random();
        for (int i = 0; i < entries.length; i++) {
            entries[i].setValue(rnd.nextInt(maxvalue));
        }
    }
}
