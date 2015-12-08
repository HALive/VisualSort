package halive.visualsort.core.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.awt.Color;

public class CountingSort extends SortingAlgorithm {

    public CountingSort() {
        super("Counting Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        int[] valueCount = new int[sortingHandler.getMaxValue()];
        //count the Values
        for (int i = 0; i < data.length; i++) {
            data[i].setRenderColor(Color.blue);
            int val = data[i].getValue();
            if(sortingHandler.compare(val > sortingHandler.getMaxValue())) {
                continue;
            }
            if(i > 0 && i < data.length-1) {
                data[i-1].setRenderColor(Color.BLACK);
            }
            try {
                Thread.sleep(sortingHandler.getDelay());
            } catch (InterruptedException e) {}
            valueCount[val]++;
        }
        //Recreate the array
        int ptr = 0;
        for (int i = 0; i < valueCount.length; i++) {
            int v = valueCount[i];
            for (int j = 0; j < v; j++) {
                data[ptr].setValue(i);
                data[ptr].setRenderColor(Color.green);
                try {
                    Thread.sleep(sortingHandler.getDelay());
                } catch (InterruptedException e) {}
                ptr++;
            }
        }
        if(ptr < data.length) {
            for (int i = ptr; i < data.length; i++) {
                data[i].setValue(0);
                data[i].setRenderColor(Color.red);
            }
        }
    }
}
