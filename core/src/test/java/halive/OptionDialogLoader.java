/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive;

import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.components.DialogCheckBox;

public class OptionDialogLoader {

    public static void main(String[] args) {
        OptionDialog dialog = new OptionDialog(null, "Dialog Test");
        dialog.addComponentPair("Select Your Options", null);
        for (int i = 0; i < 10; i++) {
            DialogCheckBox checkBox = new DialogCheckBox("Checkbox no. " + i, "cb." + i);
            String label = "Label No. " + i;
            dialog.addComponentPair(label, checkBox);
        }
        dialog.showDialog();
        System.exit(0);
    }
}
