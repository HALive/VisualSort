/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.interfaces;

import halive.visualsort.core.plugins.PluginHandler;

import javax.swing.JButton;

public interface IVisualSortUI {

    /**
     * Displays the given Message in a Status Label
     *
     * @param message
     */
    void displayStatus(String message);

    /**
     * Is Called to update the Values of the Stuts Labels For Swaps Comparisons and the elapsed time
     *
     * @param comp  the Current count of Comparisons
     * @param swaps the Current count of Swaps
     * @param time  the current elapsed time
     */
    void updateStatusLabels(long comp, long swaps, String time);

    /**
     * Returns the Start (Start Sorting) Button.
     *
     * @return
     */
    JButton getStartButton();

    /**
     * Enables/Disables the Algorithm and DataGeneration Selection.
     *
     * @param b
     */
    void enableAlgorithmSelection(boolean b);

    /**
     * Enables/Disables the Stop Buttons
     *
     * @param b
     */
    void enableStopButtons(boolean b);

    PluginHandler getPluginHandler();
}
