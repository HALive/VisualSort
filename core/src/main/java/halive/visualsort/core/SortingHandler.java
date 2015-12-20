/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core;

import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.sorting.SortingAlgorithm;

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
public class SortingHandler implements Runnable {

    private static final int TIMER_INTERVAL = 20;
    public static final int MAX_HEIGHT_VAL = 1000;

    private Timer statusUpdater;

    private long swaps = 0;
    private long comparisons = 0;
    private long elapsedTime = 0;

    private SortingAlgorithm currentAlgorithm;
    private DataGenerator dataGenerator;

    private DataEntry[] entries;
    private int renderWidth;
    private int delay;

    private boolean delayOnSwap;
    private boolean delayOnComp;

    private boolean stopOnNextComp = false;
    private boolean stopOnNextSwap = false;

    private boolean allowRendering = false;

    private Thread sortingThread;

    private IVisualSortUI gui;
    private int amtEntries;
    private int maxValue = SortingHandler.MAX_HEIGHT_VAL;

    public SortingHandler(IVisualSortUI ui) {
        this.gui = ui;
    }

    public void init() {
        statusUpdater = new Timer("Status Updater");
        sortingThread = new Thread(this, "Sorting Handler");
        sortingThread.start();

        gui.displayStatus("Generating Data");
    }

    @Override
    public void run() {
        swaps = 0;
        comparisons = 0;
        elapsedTime = -TIMER_INTERVAL;
        allowRendering = false;
        entries = null;
        entries = new DataEntry[amtEntries];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new DataEntry(this.renderWidth);
        }

        gui.displayStatus("Created array with " + entries.length + " Entries");
        dataGenerator.generateData(entries, MAX_HEIGHT_VAL);

        gui.displayStatus("Data generated");
        this.allowRendering = true;

        gui.enableStopButtons(true);
        statusUpdater.schedule(new StatusUpdater(this, gui), 0, TIMER_INTERVAL);
        gui.displayStatus("Sorting");

        currentAlgorithm.doSort(entries, this);

        statusUpdater.cancel();
        gui.displayStatus("Done");
        this.manualDataUptdate();
        gui.getStartButton().setEnabled(true);
        gui.enableAlgorithmSelection(true);
        gui.enableStopButtons(false);

        currentAlgorithm = null;
        dataGenerator = null;
    }

    public void swap(int p1, int p2) {
        int v1 = entries[p1].getValue();
        entries[p1].setValue(entries[p2].getValue());
        entries[p2].setValue(v1);
        incrementSwapsAndDelay();
    }

    public boolean compare(boolean b) {
        comparisons++;
        if (delayOnComp) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
        while (stopOnNextComp) {
            Thread.yield();
        }
        return b;
    }

    public DataEntry[] getEntries() {
        return entries;
    }

    public void setRenderWidth(int renderWidth) {
        this.renderWidth = renderWidth;
        if (entries != null) {
            for (int i = 0; i < entries.length; i++) {
                entries[i].setWidth(renderWidth);
            }
        }
    }

    public void setDataGenerator(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public void setSortingAlgorithm(SortingAlgorithm currentAlgorithm) {
        this.currentAlgorithm = currentAlgorithm;
    }

    public boolean hasDataGenerator() {
        return dataGenerator != null;
    }

    public boolean hasSortingAlgorithm() {
        return currentAlgorithm != null;
    }

    public void setDelayOnComp(boolean delayOnComp) {
        this.delayOnComp = delayOnComp;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setDelayOnSwap(boolean delayOnSwap) {
        this.delayOnSwap = delayOnSwap;
    }

    public void setStopOnNextComp(boolean stopOnNextComp) {
        this.stopOnNextComp = stopOnNextComp;
    }

    public void setStopOnNextSwap(boolean stopOnNextSwap) {
        this.stopOnNextSwap = stopOnNextSwap;
    }

    public boolean isAllowRendering() {
        return allowRendering;
    }

    public int getRenderWidth() {
        return renderWidth;
    }

    public void setAmtEntries(int amtEntries) {
        this.amtEntries = amtEntries;
    }

    public int getAmtEntries() {
        return amtEntries;
    }

    public void clearPause() {
        stopOnNextComp = false;
        stopOnNextSwap = false;
    }

    private void manualDataUptdate() {
        gui.updateStatusLabels(comparisons, swaps, null);
    }

    public boolean isRunning() {
        return !(stopOnNextComp || stopOnNextSwap);
    }

    public void incrementSwapsAndDelay() {
        swaps++;
        if (delayOnSwap) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
        while (stopOnNextSwap) {
            Thread.yield();
        }
    }

    public SortingAlgorithm getCurrentAlgorithm() {
        return currentAlgorithm;
    }

    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }

    public void setEntries(DataEntry[] entries) {
        this.entries = entries;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getDelay() {
        return delay;
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
            } else {
                Thread.yield();
            }
        }
    }
}