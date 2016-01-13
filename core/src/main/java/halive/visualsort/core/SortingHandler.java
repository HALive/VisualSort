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

/**
 * This class is Responsible for Handling/invoking the DataGeneration and sorting.
 * It also counts the swaps and comparisons.
 * <p>
 * This class can be used In testing mode when the given gui is null.
 */
@SuppressWarnings({"FieldCanBeLocal", "EmptyCatchBlock", "JavaDoc"})
public class SortingHandler implements Runnable {

    /**
     * Stores the MAximum Value to Sort at max.
     * This is 1000 by default
     */
    public static final int MAX_HEIGHT_VAL = 1000;
    /**
     * Stores the Intervall of the Timer to Update the Values
     * This is 20 Milliseconds
     */
    private static final int TIMER_INTERVAL = 20;

    /**
     * This Timer is used to Update the Status Values
     */
    private Timer statusUpdater;

    /**
     * Counts the Current Swaps
     */
    private long swaps = 0;
    /**
     * Counts the Current Comparisons
     */
    private long comparisons = 0;
    /**
     * The Elapsed Time in Milliseconds. this gets Updated by the Timer
     */
    private long elapsedTime = 0;

    /**
     * Stores the Current Algorithm
     */
    private SortingAlgorithm sortingAlgorithm;
    /**
     * Stores the Options selected for the Algorithm.
     * This is null if the Algortihm does not Have any Options
     */
    private OptionDialogResult algorithmResult = null;

    /**
     * Stores the current DataGenerator
     */
    private DataGenerator dataGenerator;
    /**
     * Stores the Options selected for the Datagenerator.
     * This is null if the DataGenerator does not Have any Options
     */
    private OptionDialogResult dataGenResult = null;

    /**
     * Main Data Array. This is Generated in the Run Method (By the Handler Thread)
     */
    private DataEntry[] entries;
    /**
     * The width in Pixels with which every Value should be Rendered
     */
    private int renderWidth;

    /**
     * True if delay should be Appiled on every Swap
     */
    private boolean delayOnSwap;
    /**
     * True if delay should be Applied on every Comparison
     */
    private boolean delayOnComp;

    /**
     * The Delay in MS to Apply. No delay will be Applied if this is Zero
     */
    private int delay;

    /**
     * True if the Sorting should Pause on the next Comparison.
     * The thread will yield while this is true
     */
    private boolean stopOnNextComp = false;
    /**
     * True if the Sorting should Pause on the next Swap.
     * The thread will yield while this is true
     */
    private boolean stopOnNextSwap = false;
    /**
     * True if the Sorting is Currently Paused. This is Mainly used to
     * pause the Timer. The Sorting will not unpause if this gets set from true
     * to false
     */
    private boolean paused = false;

    /**
     * True if the data should get Rendered
     */
    private boolean allowRendering = false;

    /**
     * Reference to the Thread in which the Sorting and Datageneraton is Performer
     */
    private Thread sortingThread;

    /**
     * Reference to the GUI
     */
    private IVisualSortUI gui;

    /**
     * Stores the Amount of Entres to sort. This is used to Generate the
     * entries Array
     */
    private int amtEntries;
    /**
     * The Maximum Value of the DataEntries
     */
    private int maxValue = SortingHandler.MAX_HEIGHT_VAL;

    /**
     * Reference to the Current Exporter
     */
    private SortingExporter exporter;
    /**
     * True if the Current Sorting Progess should be Exported
     */
    private boolean export = false;

    public SortingHandler(IVisualSortUI ui) {
        this.gui = ui;
    }

    /**
     * Initializes and Starts the Sorting Thread
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

    /**
     * This is Called if a Value gets Swapped.
     * It Delays if thats set and it also Yields the thread if the Sorting should Pause
     * The Swap Counter will also be Incremented
     */
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

    /**
     * Negates the Paused Attribute
     */
    private void pauseOrUnpause() {
        paused = !paused;
    }

    /**
     * @return The Cureent Sorting Algorithm
     */
    public SortingAlgorithm getSortingAlgorithm() {
        return sortingAlgorithm;
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
     * @return the Current DataGenerator
     */
    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }

    /**
     * Sets the DataGenerator
     *
     * @param dataGenerator The Datagen to Set
     */
    public void setDataGenerator(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    /**
     * @return This Returns the maximum Value the DataEntries should have at maximum
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * @return hte Delay that will be Applied on either Swaps or Comparisons. The Value is in Milliseconds
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Sets the Delay
     *
     * @param delay the delay to Set
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * @return the Current Sorting Exporter
     */
    public SortingExporter getSortingExporter() {
        return exporter;
    }

    /**
     * Sets the Sorting Exporter. The Handler is Also told to Start exporting Values
     *
     * @param exporter the exporter to set to
     */
    public void setSortingExporter(SortingExporter exporter) {
        export = true;
        this.exporter = exporter;
    }

    /**
     * Updates the Status Label on the UI. If there is no ui (unit Tests) its printed to stdout
     *
     * @param msg the messagt to set to
     */
    public void updateStatus(String msg) {
        if (gui != null) {
            gui.displayStatus(msg);
        } else {
            System.out.println(msg);
        }
    }

    /**
     * @return The counted Swaps done while Sorting
     */
    public long getSwaps() {
        return swaps;
    }

    /**
     * @return the Counted comparisons done while Sorting
     */
    public long getComparisons() {
        return comparisons;
    }

    /**
     * @return the Gui in its InterFace form. this has to be cast if you want to use the Something from VisualSortUi
     */
    public IVisualSortUI getGui() {
        return gui;
    }

    /**
     * Sets the Result of the OptionDialog for the SortingAlgorithm
     *
     * @param algorithmResult
     */
    public void setAlgorithmResult(OptionDialogResult algorithmResult) {
        this.algorithmResult = algorithmResult;
    }

    /**
     * Sets the Result of the OptionDialog for the DataGenerator
     *
     * @param dataGenResult
     */
    public void setDataGenResult(OptionDialogResult dataGenResult) {
        this.dataGenResult = dataGenResult;
    }

    /**
     * Timer Task to Update the Swaps, Comparions and time Values on the UI
     */
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
