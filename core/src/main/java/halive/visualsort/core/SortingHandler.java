/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */
//
package halive.visualsort.core;

import halive.visualsort.core.algorithms.datageneration.DataGenerator;
import halive.visualsort.core.algorithms.options.OptionDialogResult;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;
import halive.visualsort.core.export.SortingExporter;
import halive.visualsort.core.interfaces.IVisualSortUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
//TODO Document SortingHandler

/**
 * This class is Responsible for Handling/invoking the DataGeneration and sorting.
 * It also counts the swaps and comparisons.
 * <p>
 * This class can be used In testing mode when the given gui is null.
 */
@SuppressWarnings({"FieldCanBeLocal", "EmptyCatchBlock"})
public class SortingHandler implements Runnable {

    public static final int MAX_HEIGHT_VAL = 1000;
    private static final int TIMER_INTERVAL = 20;
    private Timer statusUpdater;

    private long swaps = 0;
    private long comparisons = 0;
    private long elapsedTime = 0;

    private SortingAlgorithm sortingAlgorithm;
    private OptionDialogResult algorithmResult = null;
    private DataGenerator dataGenerator;
    private OptionDialogResult dataGenResult = null;

    private DataEntry[] entries;
    private int renderWidth;
    private int delay;

    private boolean delayOnSwap;
    private boolean delayOnComp;

    private boolean stopOnNextComp = false;
    private boolean stopOnNextSwap = false;
    private boolean paused = false;

    private boolean allowRendering = false;

    private Thread sortingThread;

    private IVisualSortUI gui;
    private int amtEntries;
    private int maxValue = SortingHandler.MAX_HEIGHT_VAL;

    private SortingExporter exporter;
    private boolean export = false;

    public SortingHandler(IVisualSortUI ui) {
        this.gui = ui;
    }

    /**
     * Initializes the Sorting Thread
     */
    public void init() {
        statusUpdater = new Timer("Status Updater");
        sortingThread = new Thread(this, "Sorting Handler");
        sortingThread.start();

        gui.displayStatus("Generating Data");
    }

    /**
     * The SortingThreads Main Routine, Sorting and Datageneration is
     * Invoked and Mostly Perfomed by this Method
     */
    @Override
    public void run() {
        swaps = 0;
        comparisons = 0;
        elapsedTime = -TIMER_INTERVAL;
        allowRendering = false;
        entries = null;

        dataGenerator.init(dataGenResult, this);
        sortingAlgorithm.init(algorithmResult, this);

        entries = new DataEntry[amtEntries];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new DataEntry(this.renderWidth, this);
        }

        gui.displayStatus("Created array with " + entries.length + " Entries");
        dataGenerator.generateData(entries, MAX_HEIGHT_VAL);

        gui.displayStatus("Data generated");
        this.allowRendering = true;

        gui.enableStopButtons(true);
        statusUpdater.schedule(new StatusUpdater(this, gui), 0, TIMER_INTERVAL);
        gui.displayStatus("Sorting");

        logStep();
        sortingAlgorithm.doSort(entries, this, 0, entries.length);

        statusUpdater.cancel();

        if (export) {
            exporter.export();
        }

        gui.displayStatus("Done");
        this.manualDataUptdate();
        gui.getStartButton().setEnabled(true);
        gui.enableAlgorithmSelection(true);
        gui.enableStopButtons(false);

        sortingAlgorithm.clearOptions();
        dataGenerator.clearOptions();

        sortingAlgorithm = null;
        dataGenerator = null;
        algorithmResult = null;
        dataGenResult = null;
        export = false;
        exporter = null;
    }

    /**
     * Adds A New Export Step. This is Invoked whenever A Item Gest Swapped.
     * It is Invoked by the onSwapped() Method
     */
    public void logStep() {
        if (export) {
            exporter.addStep(entries);
        }
    }

    /**
     * Swaps the Values of the 2 Entries. The objets do not get swapped using this method
     *
     * @param p1 position 1
     * @param p2 position 2
     */
    public void swap(int p1, int p2) {
        int v1 = entries[p1].getValue();
        entries[p1].setValue(entries[p2].getValue());
        entries[p2].setValue(v1);
        onSwapped();
    }

    /**
     * Swaps the Objects at the Given Postions
     *
     * @param p1 pos1 Postion 1
     * @param p2 pos2 Postion 2
     */
    public void swapObject(int p1, int p2) {
        DataEntry entry = entries[p1];
        entries[p1] = entries[p2];
        entries[p2] = entry;
        onSwapped();
    }

    /**
     * This Increments the Comparisons and it delays if the delay for COmparisons is active.
     *
     * @param b the value to return
     * @return returns b (unmodified)
     */
    public boolean compare(boolean b) {
        onCompared();
        return b;
    }

    /**
     * Does the same as compare() is just does not return the value
     */
    @SuppressWarnings("Duplicates")
    public void onCompared() {
        comparisons++;
        if (delayOnComp) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
        if (stopOnNextComp) {
            pauseOrUnpause();
            while (stopOnNextComp) {
                Thread.yield();
            }
            pauseOrUnpause();
        }
    }

    /**
     * @return The Data Entry Array that gets Sorted
     */
    public DataEntry[] getEntries() {
        return entries;
    }

    /**
     * Sets the entries Array to the Given Parameter
     *
     * @param entries see Above
     */
    public void setEntries(DataEntry[] entries) {
        this.entries = entries;
    }

    /**
     * Sets the Current SortingAlgorithm
     *
     * @param currentAlgorithm the Sorting algortihm to set To
     */
    public void setSortingAlgorithm(SortingAlgorithm currentAlgorithm) {
        this.sortingAlgorithm = currentAlgorithm;
    }

    /**
     * @return true this sorting Handler Has a DataGenerator set
     */
    public boolean hasDataGenerator() {
        return dataGenerator != null;
    }

    /**
     * @return true this sorting Handler Has a SortingAlgorithm set
     */
    public boolean hasSortingAlgorithm() {
        return sortingAlgorithm != null;
    }

    /**
     * @param delayOnComp If this is true the Sorting will Delay on the next Comparison.
     *                    If its False the Sorting will not delay
     */
    public void setDelayOnComp(boolean delayOnComp) {
        this.delayOnComp = delayOnComp;
    }

    /**
     * @param delayOnSwap If this is true the Sorting will Delay on the next swap.
     *                    If its False the Sorting will not delay
     */
    public void setDelayOnSwap(boolean delayOnSwap) {
        this.delayOnSwap = delayOnSwap;
    }

    /**
     * @param stopOnNextComp If this is true the Sorting will Pause on the next Comparison.
     *                       If its False the Sorting will Continue (if it was Paused before)
     */
    public void setStopOnNextComp(boolean stopOnNextComp) {
        this.stopOnNextComp = stopOnNextComp;
    }

    /**
     * @param stopOnNextSwap If this is true the Sorting will Pause on the next swap.
     *                       If its False the Sorting will Continue (if it was Paused before)
     */
    public void setStopOnNextSwap(boolean stopOnNextSwap) {
        this.stopOnNextSwap = stopOnNextSwap;
    }

    /**
     * @return true if the Data Contained By the Handler is allowed to Be rendered.
     * In other words this returns true if the data should get Rendered
     */
    public boolean isAllowRendering() {
        return allowRendering;
    }

    /**
     * @return the renderWidth for every Value (Bar)
     */
    public int getRenderWidth() {
        return renderWidth;
    }

    /**
     * Sets the renderWitdh of the bar of every Vlaues
     *
     * @param renderWidth see above
     */
    public void setRenderWidth(int renderWidth) {
        this.renderWidth = renderWidth;
        if (entries != null) {
            for (DataEntry entry : entries) {
                entry.setWidth(renderWidth);
            }
        }
    }

    /**
     * @return The Amount of entries to Sort. this si Going to be the Length of the DataEntries Array
     */
    public int getAmtEntries() {
        return amtEntries;
    }

    /**
     * Sets the Amount of entries the Soring Handler should have
     *
     * @param amtEntries see Above
     */
    public void setAmtEntries(int amtEntries) {
        this.amtEntries = amtEntries;
    }

    /**
     * If the Sorting Was Paused in any way this will unpause Sorting
     * It does nothing if the Soring isnt paused.
     * or triggered to be Paused on the Next Swap/Comparison
     */
    public void clearPause() {
        stopOnNextComp = false;
        stopOnNextSwap = false;
    }

    /**
     * Tells the Gui to Manually update the Data
     */
    private void manualDataUptdate() {
        gui.updateStatusLabels(comparisons, swaps, null);
    }

    /**
     * @return true if the Sorting is not Paused. This is also true if the Handler has not been started
     */
    public boolean isRunning() {
        return !paused;
    }


    @SuppressWarnings("Duplicates")
    public void onSwapped() {
        swaps++;
        logStep();
        if (delayOnSwap) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
        if (stopOnNextSwap) {
            pauseOrUnpause();
            while (stopOnNextSwap) {
                Thread.yield();
            }
            pauseOrUnpause();
        }
    }

    private void pauseOrUnpause() {
        paused = !paused;
    }

    public SortingAlgorithm getSortingAlgorithm() {
        return sortingAlgorithm;
    }

    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }

    public void setDataGenerator(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public SortingExporter getSortingExporter() {
        return exporter;
    }

    public void updateStatus(String msg) {
        if (gui != null) {
            gui.displayStatus(msg);
        } else {
            System.out.println(msg);
        }
    }

    public void setSortingExporter(SortingExporter exporter) {
        export = true;
        this.exporter = exporter;
    }

    public long getSwaps() {
        return swaps;
    }

    public long getComparisons() {
        return comparisons;
    }

    public IVisualSortUI getGui() {
        return gui;
    }

    public void setAlgorithmResult(OptionDialogResult algorithmResult) {
        this.algorithmResult = algorithmResult;
    }

    public void setDataGenResult(OptionDialogResult dataGenResult) {
        this.dataGenResult = dataGenResult;
    }

    private static class StatusUpdater extends TimerTask {

        private SortingHandler handler;
        private IVisualSortUI ui;

        private StatusUpdater(SortingHandler handler, IVisualSortUI ui) {
            this.handler = handler;
            this.ui = ui;
        }

        @Override
        public void run() {
            if (handler.isRunning()) {
                handler.elapsedTime += TIMER_INTERVAL;
                DateFormat outFormat = new SimpleDateFormat("HH:mm:ss.SS");
                outFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                Date d = new Date(handler.elapsedTime);
                String result = outFormat.format(d);
                ui.updateStatusLabels(handler.comparisons, handler.swaps, result);
            }
        }
    }
}
