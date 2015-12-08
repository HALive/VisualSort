package halive.visualsort.core.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.awt.Color;

public class SelectionSort extends SortingAlgorithm {
    public SelectionSort() {
        super("Selection sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler h) {
        for (int i = 0; h.compare(i < data.length); i++) {
            int mpos = getMinimumPos(data, i, data.length - 1, h);
            h.swap(i, mpos);
            data[i].setRenderColor(Color.green);
        }
    }

    private int getMinimumPos(DataEntry[] d, int startPos, int endPos, SortingHandler h) {
        int minp = startPos;
        for (int i = startPos; h.compare(i <= endPos); i++) {
            //d[i].setRenderColor(Color.cyan);
            if (h.compare(i > 0)) {
                //d[i-1].setRenderColor(Color.blue);
            }
            if (h.compare(d[i].getValue() < d[minp].getValue())) {
                minp = i;
            }
        }
        return minp;
    }

}
