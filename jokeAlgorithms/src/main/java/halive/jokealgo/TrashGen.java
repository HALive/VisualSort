/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.jokealgo;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.algorithms.datageneration.DataGenerator;

public class TrashGen extends DataGenerator {

    public TrashGen() {
        super("Trash Gen", "");
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        for (int i = 0; i < entries.length; i++) {
            entries[i].setValue((i % 2) * maxvalue);
        }
    }

    @Override
    public String getCategory() {
        return null;
    }
}
