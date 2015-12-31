/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.datageneration.random;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.datageneration.DataGenerator;
import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.OptionDialogResult;
import halive.visualsort.core.algorithms.options.components.DialogAlgorithmList;
import halive.visualsort.core.algorithms.options.components.DialogSpinner;
import halive.visualsort.core.plugins.PluginHandler;

import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;
import java.awt.Frame;
import java.util.Random;

public class RandomDistribution extends DataGenerator {

    private static final String SELECTED_ALGORITHM_KEY = "algorithm";
    private static final String SHUFFLE_CYLCES_KEY = "shuffles";

    private DataGenerator dataGenerator;
    private int shuffleCount;

    public RandomDistribution() {
        super("Random Distribution", "");
    }

    @Override
    public void generateData(DataEntry[] entries, int maxvalue) {
        dataGenerator.generateData(entries, maxvalue);
        Random rnd = new Random();
        for (int i = 0; i < shuffleCount; i++) {
            for (int j = 0; j < entries.length; j++) {
                int rndPos = rnd.nextInt(entries.length);
                int tmp = entries[j].getValue();
                entries[j].setValue(entries[rndPos].getValue());
                entries[rndPos].setValue(tmp);
            }
        }
    }

    @Override
    public String getCategory() {
        return "Random";
    }

    @Override
    public String getName() {
        if (dataGenerator != null) {
            return super.getName() + " (" + dataGenerator.getName() + ", Shuffles: " + shuffleCount + ")";
        } else {
            return super.getName();
        }
    }

    @Override
    public OptionDialog getOptionDialog(SortingHandler handler, JFrame parent) {
        OptionDialog dialog = new OptionDialog(parent, "Select Options for " + getName());
        PluginHandler pluginHandler = handler.getGui().getPluginHandler();
        DialogAlgorithmList<DataGenerator> dGenList = null;
        if (pluginHandler != null) {
            dGenList = new DialogAlgorithmList<>(SELECTED_ALGORITHM_KEY,
                    pluginHandler.getDataGeneratorsAsList(),
                    new Class[]{this.getClass()}, true);
        }
        SpinnerNumberModel numberModel = new SpinnerNumberModel(1, 1, 100, 1);
        DialogSpinner shuffleSpinner = new DialogSpinner(numberModel, SHUFFLE_CYLCES_KEY);
        dialog.addComponentPair("Select the DataGenerator\n" +
                "to distribute Randomly", dGenList.getInScrollPane());
        dialog.addComponentPair("Shuffle Cycles: ", shuffleSpinner);
        return dialog;
    }

    @Override
    public void init(OptionDialogResult result, SortingHandler handler) {
        dataGenerator = (DataGenerator) result.getResultForKey(SELECTED_ALGORITHM_KEY);
        shuffleCount = (int) result.getResultForKey(SHUFFLE_CYLCES_KEY);
    }

    @Override
    public boolean hasOptionDialog() {
        return true;
    }

    @Override
    public void clearOptions() {
        shuffleCount = 0;
        dataGenerator = null;
    }
}
