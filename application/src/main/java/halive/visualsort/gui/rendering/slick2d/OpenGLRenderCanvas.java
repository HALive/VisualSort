/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui.rendering.slick2d;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.util.VSLog;
import halive.visualsort.gui.VisualSortUI;
import halive.visualsort.gui.rendering.IVisualSortRenderer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.awt.Canvas;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * Describes The Open GL Renderer Based on the Slick2D Game Engine
 */
public class OpenGLRenderCanvas implements IVisualSortRenderer, Game {

    /**
     * Stores the Container/Canvas of the Slick Game
     */
    private CanvasGameContainer canvas;
    /**
     * True if the rendere has been Started, False otherwise
     */
    private boolean started = false;

    /**
     * Reference to the SortingHandler
     */
    private SortingHandler handler;
    /**
     * Reference to the VisualSortUI
     */
    private VisualSortUI ui;

    /**
     * Stores the Positon to Start rendering at
     */
    private int renderPos;
    /**
     * Stores the Amount of values the Renderer Can render At max
     */
    private int maxRenderable;

    /**
     * Creates a new Render Canvas
     *
     * @param handler The SortingHandler used to receive the data to render
     * @param ui      the UI Reference
     */
    public OpenGLRenderCanvas(SortingHandler handler, VisualSortUI ui) {
        this.ui = ui;
        this.handler = handler;
    }

    @Override
    public boolean isRendering() {
        return started;
    }

    @Override
    public void setRenderPos(int renderPos) {
        this.renderPos = renderPos;
    }

    @Override
    public int getMaxRenderable() {
        return maxRenderable;
    }

    @SuppressWarnings("AccessStaticViaInstance")
    @Override
    public void start() {
        started = true;
        try {
            canvas.start();
            canvas.getContainer().enableSharedContext();
        } catch (SlickException e) {
            VSLog.logger.log(Level.SEVERE, "Error Launching Slick 2d", e);
            this.ui.slickError(e);
        }
    }

    @Override
    public void stop() {
        canvas.getContainer().exit();
        Timer t = new Timer("VisualSort Shutdown Scheduler");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 250);
    }

    /**
     * Returns the Canvas of the Game / Renderer
     *
     * @return see Above
     * @throws SlickException
     */
    public Canvas createCanvas() throws SlickException {
        canvas = new CanvasGameContainer(this);
        return canvas;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setAlwaysRender(true);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (handler != null && handler.isAllowRendering()) {
            maxRenderable = canvas.getWidth() / handler.getRenderWidth();
            int max = handler.getEntries().length > renderPos + maxRenderable ?
                    renderPos + maxRenderable : handler.getEntries().length;
            int height = canvas.getHeight();
            VSLog.logger.info(String.format("Height: %d Width: %d Max: %d MaxRenderable: %d", height, canvas.getWidth(), max, maxRenderable));
            double heightScale = (double) height / SortingHandler.MAX_HEIGHT_VAL;
            for (int i = renderPos; i < max; i++) {
                DataEntry e = handler.getEntries()[i];
                int value = (int) ((double) e.getValue() * heightScale);
                g.setColor(new Color(e.getRenderColor().getRGB()));
                g.fillRect((i - renderPos) * e.getWidth(), height - value, e.getWidth(), value);
                if (e.getWidth() > 2) {
                    g.setColor(new Color(e.getInvertedColor().getRGB()));
                    g.drawRect((i - renderPos) * e.getWidth(), height - value, e.getWidth(), value);
                }
            }
        }
    }

    @Override
    public boolean closeRequested() {
        return false;
    }

    @Override
    public String getTitle() {
        return "VisualSort";
    }
}
