/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui.rendering.j2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Describes A Canvas that Uses Active Rendering, that means the canvas
 * Autmatically gets redrawn 200 Times every Second
 */
public abstract class ActiveRenderingCanvas extends Canvas implements Runnable {

    /**
     * Stores the Paren Container
     */
    protected Container parent;
    /**
     * Stores a Reference to the RenderThread
     */
    protected Thread renderThread = new Thread(this, "Rendering Canvas");

    /**
     * True if the Renderer is Currently rendering
     */
    private boolean running = true;
    /**
     * True if a FPS Monitor should be Drawn
     */
    private boolean debug = false;

    /**
     * Stores the Last time the rendering was called.
     * Used to Calculate the Frames Per second
     */
    private long lastTime = 0;

    /**
     * Stores the Current Background Color
     */
    private Color bgColor = Color.white;

    /**
     * Creates a new ActiveRenderingCanvas
     *
     * @param frame the Parent Component
     */
    public ActiveRenderingCanvas(Container frame) {
        this.parent = frame;
        renderThread.setDaemon(true);
        this.parent.setIgnoreRepaint(true);
        Container j = this.parent;
        while (j != null) {
            j.setIgnoreRepaint(true);
            j = j.getParent();
        }
    }

    /**
     * RenderLoop renders the Canvas this does not have get called by the user.
     */
    @Override
    public final void run() {
        BufferStrategy buf = this.getBufferStrategy();
        while (running) {
            Graphics g = buf.getDrawGraphics();
            g.setColor(bgColor);
            g.fillRect(0, 0, getSize().width, getSize().height);
            draw(g);
            if (debug) {
                double fps = 1 / ((System.currentTimeMillis() - lastTime) / 1000D);
                String fpsDisp = (Math.round(fps * 100) / 100D) + " FPS";
                int textW = g.getFontMetrics(new Font("Arial", Font.BOLD, 12)).stringWidth(fpsDisp);
                g.setColor(Color.BLACK);
                g.drawString(fpsDisp, getWidth() - (textW + 30), 20);
                lastTime = System.currentTimeMillis();
            }
            if (!buf.contentsLost()) {
                buf.show();
            }
            Thread.yield();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Initializes the Renderer
     */
    public void init() {
        running = true;
        this.createBufferStrategy(2);
        this.renderThread.start();
        lastTime = System.currentTimeMillis();
    }

    /**
     * Toggles a Shutdown of a Renderer
     */
    public void toggleShutdown() {
        running = false;
    }

    /**
     * Enables/Disables the FPS Dispaly
     *
     * @param debug true to enable, false to Disable
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * This Method is Called to Render the Stage. simmliar to the Paint()
     * method of a Component
     *
     * @param g the Graphics Object to draw With
     */
    public abstract void draw(Graphics g);
}