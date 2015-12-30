/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive;

import halive.visualsort.core.algorithms.options.OptionDialog;

public class OptionDialogLoader {

    public static void main(String[] args) {
        OptionDialog dialog = new OptionDialog();
        dialog.showDialog();
        System.exit(0);
    }
}
