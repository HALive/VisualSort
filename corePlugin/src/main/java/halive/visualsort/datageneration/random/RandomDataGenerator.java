/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration.random;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.algorithms.datageneration.DataGenerator;

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

    @Override
    public String getCategory() {
        return "Random";
    }
}
