/*
 * Copyright (c) HALive 2016
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

public class SwappingHandler {

    private ISwapper swapper;

    private SwappingHandler(ISwapper swapper) {
        this.swapper = swapper;
    }

    public static SwappingHandler getForAlgorithm(SortingHandler h) {
        return getForAlgorithm(h.getCurrentAlgorithm());
    }

    public static SwappingHandler getForAlgorithm(SortingAlgorithm al) {
        ISwapper swapper;
        if (al instanceof SubArraySortingAlgortihm) {
            swapper = ((l, r, e, handler) -> {
                SubArraySortingAlgortihm a = (SubArraySortingAlgortihm) al;
                a.swap(e, l, r, handler);
            });
            System.out.println("Created SubArraySwapper");
        } else {
            swapper = ((l, r, e, handler) -> handler.swap(l, r));
            System.out.println("Created Standard Swapper");
        }
        return new SwappingHandler(swapper);
    }

    public void swap(int l, int r, DataEntry[] data, SortingHandler h) {
        swapper.swap(l, r, data, h);
    }

    private interface ISwapper {

        void swap(int l, int r, DataEntry[] e, SortingHandler h);
    }
}
