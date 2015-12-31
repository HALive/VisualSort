/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.interfaces;

import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.OptionDialogResult;

public interface IAlgorithm {

    /**
     * Returns the name Of the Algorithm
     *
     * @return see above
     */
    String getName();

    /**
     * Returns the Category of the Algorithm
     *
     * @return see above
     */
    default String getCategory() {
        return "Default";
    }

    /**
     * Constructs the Option dialog to be used To collct the Options
     * Specific to the Algorithm. if this returns null no OptionDialog
     * will be shown.
     *
     * @param handler the Sorting Handler of the Applicaton
     * @return the created Option dialog, null if no dialog should be shown
     */
    default OptionDialog getOptionDialog(SortingHandler handler) {
        return null;
    }

    /**
     * Initializes the Algorithms with the Parameters gathered by the OptionDialog
     * Obviously the Implementation of this method is only necesary if the algorithm
     * only has a Option Dialog. It is also not called if there is no OptionDialog beeing
     * Returned by the getOptionDialog
     *
     * @param result  the Result from the dialog
     * @param handler reference of the SortingHandler
     */
    default void init(OptionDialogResult result, SortingHandler handler) {

    }
}
