package halive.visualsort.gui.rendering;

public interface IVisualSortRenderer {

    boolean allowResizeWhenRendeing();

    boolean isRendering();

    void setRenderPos(int renderPos);

    void setMaxRenderable(int maxRenderable);

    String getRenderFunctionName();

    int getMaxRenderable();

    void start();

    void stop();
}
