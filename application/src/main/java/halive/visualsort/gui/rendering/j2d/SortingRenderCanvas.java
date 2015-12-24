/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui.rendering.j2d;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.gui.rendering.IVisualSortRenderer;

import java.awt.Container;
import java.awt.Graphics;

/**
 * Descibes the Renderer for J2D
 */
public class SortingRenderCanvas extends ActiveRenderingCanvas implements IVisualSortRenderer {

    /**
     * Reference to the SortingHandler
     */
    private SortingHandler sortingHandler;
    /**
     * The Position to Start rendering at
     */
    private int renderPos = 0;

    /**
     * Creates A new SortingRenderCanvas
     *
     * @param frame   the Parent Component
     * @param handler the SortingHandler
     */
    public SortingRenderCanvas(Container frame, SortingHandler handler) {
        super(frame);
        this.sortingHandler = handler;
    }

    @Override
    public void draw(Graphics g) {
        if (sortingHandler != null && sortingHandler.isAllowRendering()) {
            int max = sortingHandler.getEntries().length > renderPos + getMaxRenderable() ? renderPos + getMaxRenderable() : sortingHandler.getEntries().length;
            int height = this.getHeight();
            double heightScale = (double) height / SortingHandler.MAX_HEIGHT_VAL;
            for (int i = renderPos; i < max; i++) {
                DataEntry e = sortingHandler.getEntries()[i];
                g.setColor(e.getRenderColor());
                int value = (int) ((double) e.getValue() * heightScale);
                g.fillRect((i - renderPos) * e.getWidth(), height - value, e.getWidth(), value);
                if (e.getWidth() > 2) {
                    g.setColor(e.getInvertedColor());
                    g.drawRect((i - renderPos) * e.getWidth(), height - value, e.getWidth(), value);
                }
            }
        }
    }

    @Override
    public boolean isRendering() {
        return this.renderThread.isAlive();
    }

    /**
     * Sets the Current Render Position
     *
     * @param renderPos see above
     */
    public void setRenderPos(int renderPos) {
        this.renderPos = renderPos;
    }

    @Override
    public void start() {
        this.init();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    /**
     * Returns the Value the of items teh Renderer Can Render Currently
     */
    public int getMaxRenderable() {
        return this.getWidth() / sortingHandler.getRenderWidth();
    }
}
