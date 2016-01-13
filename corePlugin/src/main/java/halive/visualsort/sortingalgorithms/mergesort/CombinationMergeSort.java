/*
 * Copyright (c) HALive 2016
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.OptionDialogResult;
import halive.visualsort.core.algorithms.options.components.DialogAlgorithmList;
import halive.visualsort.core.algorithms.options.components.DialogCheckBox;
import halive.visualsort.core.algorithms.options.components.DialogSpinner;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;
import halive.visualsort.sortingalgorithms.api.APIParallelSort;
import halive.visualsort.sortingalgorithms.api.APISort;
import halive.visualsort.sortingalgorithms.insertion.ShellSortParallel;
import halive.visualsort.sortingalgorithms.quicksort.heuristics.LastElementHeuristic;
import halive.visualsort.sortingalgorithms.quicksort.heuristics.MedianOf3Heuristic;
import halive.visualsort.sortingalgorithms.quicksort.heuristics.RandomElementHeuristic;

import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CombinationMergeSort extends ParallelMergeSort {

    public static final String ALGORITHM_KEY = "algorithm";
    public static final String PARALLEL_KEY = "parallel";
    public static final String SUB_ALGORITHM_TOGGLE_KEY = "minLen";

    public static final Class[] invalidAlgorithms = {
            MergeSort.class,
            ParallelMergeSort.class,
            MedianOf3Heuristic.ParallelQuickSortMed3.class,
            LastElementHeuristic.ParallelQuickSortLast.class,
            RandomElementHeuristic.ParallelQuickSortRand.class,
            ShellSortParallel.class,
            //ShellSort.class,
            APIParallelSort.class,
            APISort.class
    };

    private boolean parallel = false;
    private int toggleLen = -1;
    private SortingAlgorithm subAlgorithm;

    private ExecutorService pool;

    public CombinationMergeSort() {
        super("Divide and Merge");
    }

    @Override
    public OptionDialog getOptionDialog(SortingHandler handler, JFrame parent) {
        OptionDialog dialog = super.getOptionDialog(handler, parent);
        if (handler.getGui() != null && handler.getGui().getPluginHandler() != null) {
            DialogAlgorithmList<SortingAlgorithm> algorithmList =
                    new DialogAlgorithmList<>(ALGORITHM_KEY,
                            handler.getGui().getPluginHandler().getSortingAlgorithmsAsList(),
                            invalidAlgorithms, true);
            dialog.addComponentPair("Select the Sub-Algorithm", algorithmList.getInScrollPane());
        }
        SpinnerNumberModel numberModel = new SpinnerNumberModel(64, 32, 512, 16);
        dialog.addComponentPair("Length to use Sub-Algorithm:", new DialogSpinner(numberModel,
                SUB_ALGORITHM_TOGGLE_KEY));
        dialog.addComponentPair("Run in Parallel: ", new DialogCheckBox("Parallel", PARALLEL_KEY));
        return dialog;
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        pool = Executors.newCachedThreadPool();
        super.doSort(data, sortingHandler, l, r);
    }

    @Override
    public void mergeSort(int l, int r, DataEntry[] data, SortingHandler h) {
        int diff = r - l;
        if (diff == 0) {
            return;
        } else if (diff <= toggleLen) {
            subAlgorithm.doSort(data, h, l, r + 1);
        } else {
            int m = ((r - l) / 2) + l;
            Future task = null;
            if ((r - m) >= (toggleLen / 2) && parallel) {
                task = pool.submit(new MergeTask(data, h, l, m), System.currentTimeMillis());
            } else {
                mergeSort(l, m, data, h);
            }
            mergeSort(m + 1, r, data, h);
            if (task != null) {
                try {
                    task.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            merger.getMergeMethod().merge(l, r, data, h);
        }
    }

    @Override
    public void init(OptionDialogResult result, SortingHandler handler) {
        super.init(result, handler);
        subAlgorithm = (SortingAlgorithm) result.getResultForKey(ALGORITHM_KEY);
        parallel = (boolean) result.getResultForKey(PARALLEL_KEY);
        toggleLen = (int) result.getResultForKey(SUB_ALGORITHM_TOGGLE_KEY);
    }

    @Override
    public void clearOptions() {
        super.clearOptions();
        subAlgorithm = null;
        parallel = false;
        toggleLen = -1;
    }

    @Override
    public String getName() {
        if (subAlgorithm == null) {
            return name;
        }
        return name + " (Subalgorithm: " + subAlgorithm.getName() + " Merger: " + merger
                + " " + (parallel ? "Parallel " : "") + "SubToggle Length: " + toggleLen + ")";
    }
}
