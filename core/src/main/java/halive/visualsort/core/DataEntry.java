/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core;

import java.awt.Color;

public class DataEntry implements Comparable<DataEntry> {

    private int value;
    private int width;

    private Color renderColor;
    private Color invertedColor;

    private Color oldColor;

    private SortingHandler handler;

    public DataEntry(int width, SortingHandler handler) {
        this.value = 0;
        this.width = width;
        setRenderColor(Color.blue);
        this.handler = handler;
    }

    public DataEntry(int value, int width, SortingHandler sortingHandler) {
        this.value = value;
        this.width = width;
        this.handler = sortingHandler;
    }

    public void setRenderColor(Color renderColor) {
        this.renderColor = renderColor;
        int r = (0xFFFFFF - renderColor.getRGB()) | 0xFF000000;
        this.invertedColor = new Color(r);
    }

    public Color getInvertedColor() {
        return invertedColor;
    }

    public Color getRenderColor() {
        return renderColor;
    }

    public void setTemporaryColor(Color c) {
        if (oldColor == null) {
            oldColor = renderColor;
        }
        setRenderColor(c);
    }

    public void removeTemporaryColor() {
        if (oldColor == null) {
            return;
        }
        setRenderColor(oldColor);
        oldColor = null;
    }

    public void setPrimaryColor(Color c) {
        if (oldColor != null) {
            oldColor = c;
        } else {
            setRenderColor(c);
        }
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }

    public synchronized int getValue() {
        return value;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int compareTo(DataEntry o) {
        handler.onCompared();
        return (int) Math.signum(this.value - o.value);
    }
}
