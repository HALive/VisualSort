/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.datageneration.DataGenerator;
import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.OptionDialogResult;

import javax.swing.JFrame;
import java.awt.Frame;

public class RandomDistribution extends DataGenerator {

    private DataGenerator dataGenerator;

    public RandomDistribution() {
        super("Random Distribution", "");
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {

    }

    @Override
    public String getCategory() {
        return "Random";
    }

    @Override
    public OptionDialog getOptionDialog(SortingHandler handler) {
        Frame parent = null;
        if (handler.getGui() != null && handler.getGui() instanceof JFrame) {
            parent = (Frame) handler.getGui();
        }
        OptionDialog dialog = new OptionDialog(parent, "Select Options for " + getName());

        return dialog;
    }

    @Override
    public void init(OptionDialogResult result, SortingHandler handler) {

    }
}
