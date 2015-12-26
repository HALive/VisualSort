/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.export;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingExporter {

    private SortingHandler handler;
    private List<SortingStep> steps;
    private File outputFile;

    public SortingExporter(SortingHandler handler, File outputFile) {
        this.handler = handler;
        this.outputFile = outputFile;
        this.steps = Collections.synchronizedList(new ArrayList<>());
    }

    public void addStep(DataEntry[] entries) {
        steps.add(new SortingStep(entries));
    }

    public void export() {
        //Initialize
        handler.getGui().displayStatus("Exporting....");
        BufferedImage image = new BufferedImage(steps.size(), steps.get(0).getLength() + 50,
                BufferedImage.TYPE_INT_ARGB);
        //Render the Background
        Graphics g = image.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        //Render Every Step
        for (int i = 0; i < steps.size(); i++) {
            handler.getGui().displayStatus("Rendering Step " + (i + 1) + "/" + steps.size());
            SortingStep step = steps.get(i);
            if (step != null) {
                step.render(g, handler, i);
            }
        }
        //Write The Image
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            handler.getGui().displayStatus("IO-Error during Export");
            e.printStackTrace();
        }
    }
}
