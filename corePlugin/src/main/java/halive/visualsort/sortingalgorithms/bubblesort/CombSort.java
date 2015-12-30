/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.bubblesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.sorting.SortingAlgorithm;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This Class implements the CombSort Algorithm
 */
public class CombSort extends SortingAlgorithm {

    public CombSort() {
        super("Comb Sort", "");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        //Get all Primenumbers in 2 to data.length
        Deque<Integer> deque = getPrimes(data.length);
        final int[] cnt = {1};
        //Remove a Prime if the count of it is dividable by 2
        // (in other words, remove every second Prime
        deque.removeIf(integer -> {
            boolean b = cnt[0] % 2 == 0;
            //System.out.printf("Count: %d Value: %d Removed: %d\n", cnt[0], integer, b ? 1 : 0);
            cnt[0]++;
            return b;
        });
        //Add the 1 at the beginning of the array
        deque.push(1);
        Integer[] cols = {1};
        //Copy the data in the array
        cols = deque.toArray(cols);
        //Sort
        combsort(data, sortingHandler, cols);
    }

    public void combsort(DataEntry[] data, SortingHandler sortingHandler, Integer[] cols) {
        int i = 0;
        while (i < cols.length && data.length - 1 > cols[i]) {
            i++;
        }
        if (i == cols.length) {
            i = cols.length - 1;
        }
        int step;
        do {
            step = cols[i];
            if (i > 0) {
                i--;
            }
        } while (sortingHandler.compare(comb(step, data, sortingHandler) || i > 0));
    }

    private boolean comb(int step, DataEntry[] d, SortingHandler h) {
        boolean swapped = false;
        for (int i = 0; i < d.length - step; i++) {
            d[i].setRenderColor(Color.green);
            d[i + step].setRenderColor(Color.red);
            if (h.compare(d[i].getValue() > d[i + step].getValue())) {
                h.swap(i, i + step);
                swapped = true;
            }
            d[i].setRenderColor(Color.blue);
            d[i + step].setRenderColor(Color.blue);
        }
        return swapped;
    }

    public Deque<Integer> getPrimes(int max) {
        boolean[] pn = new boolean[max + 1];
        pn[0] = true;
        pn[1] = true;
        for (int i = 2; i < pn.length; i++) {
            if (!pn[i]) {
                for (int j = 2 * i; j < pn.length; j = j + i) {
                    pn[j] = true;
                }
            }
        }
        ArrayDeque<Integer> ints = new ArrayDeque<>();
        for (int i = 0; i < pn.length; i++) {
            if (!pn[i]) {
                ints.add(i);
            }
        }
        return ints;
    }

    @Override
    public String getCategory() {
        return "BubbleSort";
    }
}
