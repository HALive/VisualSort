package halive.visualsort.core;

import java.awt.*;

public class DataEntry {
    private int value;
    private int width;

    private Color renderColor;
    private Color invertedColor;

    public DataEntry(int width) {
        this.value = 0;
        this.width = width;
        setRenderColor(Color.blue);
    }

    public DataEntry(int value, int width) {
        this.value = value;
        this.width = width;
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

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }
}
