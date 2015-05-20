package halive.visualsort.gui.rendering.slick2d;

import halive.visualsort.VisualSort;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.gui.VisualSortUI;
import halive.visualsort.gui.rendering.IVisualSortRenderer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.awt.Canvas;
import java.awt.Component;

public class OpenGLRenderCanvas implements IVisualSortRenderer, Game {

    private CanvasGameContainer canvas;
    private boolean started = false;

    private SortingHandler handler;
    private VisualSortUI ui;

    public OpenGLRenderCanvas(SortingHandler handler, VisualSortUI ui) {
        this.ui = ui;
        this.handler = handler;
    }

    @Override
    public boolean allowResizeWhenRendeing() {
        return false;
    }

    @Override
    public boolean isRendering() {
        return started;
    }

    @Override
    public void setRenderPos(int renderPos) {

    }

    @Override
    public void setMaxRenderable(int maxRenderable) {

    }

    @Override
    public String getRenderFunctionName() {
        return null;
    }

    @Override
    public void start() {
        started = true;
    }

    public Canvas createCanvas() throws SlickException {
        canvas = new CanvasGameContainer(this);
        return canvas;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

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
