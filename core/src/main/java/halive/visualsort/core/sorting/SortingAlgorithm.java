/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.sorting;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.INamable;
import halive.visualsort.core.SortingHandler;

/**
 * This Abstract class Allows you to define A Sorting Algortithm used by the VisualSort Programm
 * <p>
 * Just like the DataGenerator subclasses of this class need a default Constructor to be used as SortingAlgortihms
 * in VisualSort
 *
 * @author HALive
 */
public abstract class SortingAlgorithm implements INamable {

    public static SortingAlgorithm[] ALGORTIHMS;

    protected String name;
    protected String description;

    /**
     * Creates a new SortingAlgorithm
     *
     * @param name        The name of the Algorithm
     * @param description A Brief description of the Algorithm (Has no use atm.)
     */
    public SortingAlgorithm(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * This is where you implement the sorting algortihm. better said.
     * This Method is called once Sorting should get Started.
     *
     * @param data           The data to Sort
     * @param sortingHandler The sorting handler (use sortingHandler.swap() to swap items and sH.compare to tunnel
     *                       a boolean. this allows the programm to count swaps and comparisons. it also allows the
     *                       programm to pause the algoritm at a specific point.
     */
    public abstract void doSort(DataEntry[] data, SortingHandler sortingHandler);

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public void addToName(String s) {
        name += " (" + s + ")";
    }

    @Override
    public String toString() {
        return getName();
    }
}
