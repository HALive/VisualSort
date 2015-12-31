/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.OptionDialogResult;
import halive.visualsort.core.algorithms.options.components.DialogList;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import javax.swing.JFrame;

/**
 * This Class Implements Merge Sort in the Basic Implementation
 */
@SuppressWarnings("EmptyCatchBlock")
public class MergeSort extends SortingAlgorithm {

    public static String MERGER_KEY = "merger";

    protected MergingMethods merger;

    public MergeSort() {
        super("Merge Sort", "");
    }

    public MergeSort(String name) {
        this();
        this.name = name;
    }

    @Override
    public String getCategory() {
        return "MergeSort";
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler, int l, int r) {
        mergeSort(l, r - 1, data, sortingHandler);
    }

    public void mergeSort(int l, int r, DataEntry[] data, SortingHandler h) {
        int diff = r - l;
        if (diff == 0) {
            return;
        } else if (diff == 1) {
            if (h.compare(data[l].getValue() > data[r].getValue())) {
                h.swap(l, r);
            }
        } else {
            int m = ((r - l) / 2) + l;
            mergeSort(l, m, data, h);
            mergeSort(m + 1, r, data, h);
            merger.getMergeMethod().merge(l, r, data, h);
        }
    }

    @Override
    public void init(OptionDialogResult result, SortingHandler handler) {
        merger = (MergingMethods) result.getResultForKey(MERGER_KEY);
    }

    @Override
    public String getName() {
        if (merger != null) {
            return super.getName() + " (" + merger + ") ";
        }
        return super.getName();
    }

    @Override
    public boolean hasOptionDialog() {
        return true;
    }

    @Override
    public OptionDialog getOptionDialog(SortingHandler handler, JFrame parent) {
        OptionDialog dialog = new OptionDialog(parent, "Select Merging Method");
        DialogList<MergingMethods> methodList = new DialogList<>(MergingMethods.values(), MERGER_KEY);
        dialog.addComponentPair("Select Merge Method:", methodList.getInScrollPane());
        return dialog;
    }

    @Override
    public void clearOptions() {
        merger = null;
    }
}
