/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui.rendering;

/**
 * Describes a Renderer Used to Render the Tiles
 */
public interface IVisualSortRenderer {

    /**
     * Returns true if the renderer is Currently rendering
     *
     * @return see Above
     */
    boolean isRendering();

    /**
     * Sets The Current Render Pos ( The Starting Position to Start Rendering at
     *
     * @param renderPos see above
     */
    void setRenderPos(int renderPos);

    /**
     * returns the Maximum amout of entries the Renderer can Currently render on the Window
     *
     * @return see Above
     */
    int getMaxRenderable();

    /**
     * Starts the Renderer
     */
    void start();

    /**
     * Stops the Renderer and exits the Program
     */
    void stop();
}
