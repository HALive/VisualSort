/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.datageneration.impl;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.datageneration.DataGenerator;

public class SawtoothGenerator extends DataGenerator {

    public SawtoothGenerator() {
        super("Sawtooth", " ");
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        double sawLength = entries.length / 20.0D;
        double stepHeight = SortingHandler.MAX_HEIGHT_VAL / sawLength;
        double lastPos = 0;
        for (int i = 0; i < entries.length; i++) {
            entries[i].setValue((int) lastPos);
            lastPos = (i % sawLength) * stepHeight;
        }
    }
}
