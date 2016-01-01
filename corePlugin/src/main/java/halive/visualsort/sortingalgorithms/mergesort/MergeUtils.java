/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.awt.Color;

@SuppressWarnings("Duplicates")
public class MergeUtils {

    public static void mergeInPlace(int l, int r, DataEntry[] entries, SortingHandler h) {
        int m = ((r - l) / 2) + l;
        int pos = l;
        while (pos <= m) {
            entries[pos].setTemporaryColor(Color.RED);
            entries[pos].setPrimaryColor(Color.black);
            if (h.compare(entries[pos].getValue() > entries[m + 1].getValue())) {
                h.swap(pos, m + 1);
                for (int i = m + 1; i < r; i++) {
                    entries[i].setTemporaryColor(Color.MAGENTA);
                    if (h.compare(entries[i].getValue() > entries[i + 1].getValue())) {
                        h.swap(i, i + 1);
                    }
                    entries[i].removeTemporaryColor();
                }
            }
            entries[pos].removeTemporaryColor();
            pos++;
        }
    }

    public static void mergeOPEfficient(int l, int r, DataEntry[] entries, SortingHandler h) {
        int m = ((r - l) / 2) + l;
        int[] d = new int[r - l];

        int i = 0;
        int j = l;
        while (j <= m) {
            entries[j].removeTemporaryColor();
            d[i++] = entries[j++].getValue();
            entries[j].setTemporaryColor(Color.MAGENTA);
            h.onSwapped();
        }

        entries[j].removeTemporaryColor();

        i = 0;
        int k = l;
        while (k < j && j <= r) {
            entries[k].setPrimaryColor(Color.black);
            entries[k].setTemporaryColor(Color.ORANGE);
            if (h.compare(d[i] <= entries[j].getValue())) {
                entries[k++].setValue(d[i++]);
            } else {
                entries[k++].setValue(entries[j++].getValue());
            }
            h.onSwapped();
            entries[k - 1].removeTemporaryColor();
        }

        while (k < j) {
            entries[k].setPrimaryColor(Color.black);
            entries[k].setTemporaryColor(Color.ORANGE);
            entries[k++].setValue(d[i++]);
            h.onSwapped();
            entries[k - 1].removeTemporaryColor();
        }
    }

    public static void mergeBitonic(int l, int r, DataEntry[] entries, SortingHandler handler) {
        int m = ((r - l) / 2) + l;

        int i = l;
        int j = r;
        int k = 0;
        int[] b = new int[(r - l) + 1];

        while (i <= m) {
            entries[i].removeTemporaryColor();
            b[k++] = entries[i++].getValue();
            entries[i].setTemporaryColor(Color.MAGENTA);
            handler.onSwapped();
        }

        entries[i].removeTemporaryColor();

        while (j > m) {
            entries[j].removeTemporaryColor();
            b[k++] = entries[j--].getValue();
            entries[j].setTemporaryColor(Color.MAGENTA);
            handler.onSwapped();
        }

        entries[j].removeTemporaryColor();

        i = 0;
        j = r - l;
        k = l;

        while (i <= j) {
            entries[k].setPrimaryColor(Color.black);
            entries[k].setTemporaryColor(Color.ORANGE);
            if (handler.compare(b[i] <= b[j])) {
                entries[k++].setValue(b[i++]);
            } else {
                entries[k++].setValue(b[j--]);
            }
            handler.onSwapped();
            entries[k - 1].removeTemporaryColor();
        }
    }

    public interface IMerge {

        void merge(int l, int r, DataEntry[] entries, SortingHandler h);
    }
}
