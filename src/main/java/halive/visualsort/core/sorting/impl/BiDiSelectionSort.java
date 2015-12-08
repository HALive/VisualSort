package halive.visualsort.core.sorting.impl;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;
import halive.visualsort.core.sorting.impl.util.SelectionSortUtils;

import java.awt.Color;

/**
 * This Class implements Bidrectional Selection sort. It works just like Selection Sort. It just Searches for the
 * then for the minium and the maximum again...
 */
public class BiDiSelectionSort extends SortingAlgorithm {
    public BiDiSelectionSort() {
        super("Bidirectional SelectionSort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        int i = 0;
        int j = data.length - 1;
        while (i < j) {
            //Minimum
            int pos = SelectionSortUtils.getMinimumPos(data, i, j, sortingHandler);
            sortingHandler.swap(i, pos);
            data[i].setRenderColor(Color.orange);
            i++;
            //Maximum:
            pos = SelectionSortUtils.getMaximumPos(data, i, j, sortingHandler);
            sortingHandler.swap(j, pos);
            data[j].setRenderColor(Color.magenta);
            j--;
        }
    }
}
