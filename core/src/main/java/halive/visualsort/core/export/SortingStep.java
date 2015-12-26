/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.export;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.awt.Color;
import java.awt.Graphics;

public class SortingStep {

    private short[] values;

    public SortingStep(DataEntry[] entries) {
        this.values = new short[entries.length];
        for (int i = 0; i < entries.length; i++) {
            values[i] = (short) entries[i].getValue();
        }
    }

    public int getLength() {
        return values.length;
    }

    public void render(Graphics g, SortingHandler h, int pos) {
        for (int i = 0; i < values.length; i++) {
            Color color = convertValueToColor(values[i], h);
            //Set The Value Color
            g.setColor(color);
            //Draw the Value
            g.fillRect(pos, i, 1, 1);
        }
    }

    public Color convertValueToColor(short val, SortingHandler handler) {
        return Color.getHSBColor(val / (float) handler.getMaxValue(), 1.0F, 1.0F);
    }
}
