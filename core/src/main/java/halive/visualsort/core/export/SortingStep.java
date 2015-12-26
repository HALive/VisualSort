/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.export;

import halive.visualsort.core.DataEntry;

public class SortingStep {

    private short[] values;
    private int position;

    public SortingStep(DataEntry[] entries, int pos) {
        this.values = new short[entries.length];
        this.position = pos;
        for (int i = 0; i < entries.length; i++) {
            values[i] = (short) entries[i].getValue();
        }
    }
}
