/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core;

import java.awt.Color;

/**
 * This Class represents the Values that get
 * Sorted with the Implemented Sorting Algortihms
 */
public class DataEntry implements Comparable<DataEntry> {

    /**
     * Stores the Current value of the DataEntry
     */
    private int value;
    /**
     * Stores the Width of the bar.
     */
    private int width;

    /**
     * This stores the color with which the bar should be rendered currently
     */
    private Color renderColor;
    /**
     * This always is the Inverse Color of RenderColor.
     * This is used to render the Borders if the BarWiths is bigger tan 4
     */
    private Color invertedColor;

    /**
     * The current render Color gets Stored Here if you set a Temporary Color
     */
    private Color oldColor;

    /**
     * Reference to the sorting Handler
     */
    private SortingHandler handler;

    /**
     * Initializes a Data Entry with the value of Zero
     *
     * @param width   the renderWidth (Current)
     * @param handler reference to teh SoringHandler
     */
    public DataEntry(int width, SortingHandler handler) {
        this.value = 0;
        this.width = width;
        setRenderColor(Color.blue);
        this.handler = handler;
    }

    /**
     * Initializes the data Entry with a Value
     *
     * @param value          the value of the entry
     * @param width          the render width of the bar (current)
     * @param sortingHandler refernce to the Sorting Handler
     */
    public DataEntry(int value, int width, SortingHandler sortingHandler) {
        this(width, sortingHandler);
        this.value = value;
    }

    /**
     * Sets the value of the render color to the given color.
     * It also calculates the inverse and stores it in inverse color
     *
     * @param renderColor the color to set to
     */
    public void setRenderColor(Color renderColor) {
        this.renderColor = renderColor;
        int r = (0xFFFFFF - renderColor.getRGB()) | 0xFF000000;
        this.invertedColor = new Color(r);
    }

    /**
     * Returns the Inverted Color
     *
     * @return the invertedColor Attributes
     */
    public Color getInvertedColor() {
        return invertedColor;
    }

    /**
     * Returns the Render Color attribute (the current Color of the Value)
     *
     * @return the render Color Attribute
     */
    public Color getCurrentColor() {
        return renderColor;
    }

    /**
     * Sets the Current Render Color to the given color.
     * However the oldColor gets stored if the oldColor
     * is null (No other TemporaryColor is Set).
     * After that it Calls the setRenderColor Method to
     * Calculate and set The Inverse Color
     *
     * @param c the Color to set As Temp Color
     */
    public void setTemporaryColor(Color c) {
        if (oldColor == null) {
            oldColor = renderColor;
        }
        setRenderColor(c);
    }

    /**
     * Removes the Temporary Color (if there is one)
     * it sets the Render color to the OldColor Attribute
     * and nulls the oldColor attribute.
     */
    public void removeTemporaryColor() {
        if (oldColor == null) {
            return;
        }
        setRenderColor(oldColor);
        oldColor = null;
    }

    /**
     * Setts the Primary Color the color that it gets restored
     * to if there is no Temporary Color set.
     *
     * @param c the Color to Set the PrimaryColor to
     */
    public void setPrimaryColor(Color c) {
        if (oldColor != null) {
            oldColor = c;
        } else {
            setRenderColor(c);
        }
    }

    /**
     * Sets the Value Attribute to the given Value
     *
     * @param value the Value to set
     */
    public synchronized void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns the Value Attribute
     *
     * @return the value Attribute
     */
    public synchronized int getValue() {
        return value;
    }

    /**
     * Sets thw width attribut
     *
     * @param width the width attribute
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the Width attribute
     *
     * @return the width attribute
     */
    public int getWidth() {
        return width;
    }

    @Override
    public int compareTo(DataEntry o) {
        handler.onCompared();
        return (int) Math.signum(this.value - o.value);
    }
}
