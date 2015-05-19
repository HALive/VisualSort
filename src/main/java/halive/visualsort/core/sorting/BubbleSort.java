package halive.visualsort.core.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.local.LocalizationEntries;

import java.awt.Color;

public class BubbleSort extends SortingAlgorithm{

    public BubbleSort() {
        super("Bubble Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        for (int i = 0;sortingHandler.compare( i < data.length - 1); i++) {
            for (int j = 0; sortingHandler.compare(j < data.length - (1 + i)); j++) {
                if (sortingHandler.compare(data[j].getValue() > data[j + 1].getValue())) {
                    sortingHandler.swap(j, j + 1);
                }
            }
            data[data.length - (1 + i)].setRenderColor(Color.red);
        }
    }
}
