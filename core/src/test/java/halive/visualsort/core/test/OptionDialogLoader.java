/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.test;

import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.OptionDialogResult;
import halive.visualsort.core.algorithms.options.components.DialogCheckBox;
import halive.visualsort.core.algorithms.options.components.DialogList;
import halive.visualsort.core.algorithms.options.components.DialogSpinner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Simple Test Class to test the Basci Funtionality of the Option Dialog
 */
public class OptionDialogLoader {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                UIManager.setLookAndFeel(info.getClassName());
            }
        }
        OptionDialog dialog = new OptionDialog(null, "Dialog Test");
        dialog.addComponentPair("Select Your Options", null);
        dialog.addComponentPair("List",
                new DialogList<String>(new String[]{"Tach", "Juung", "Danjuung"}, "testlist").getInScrollPane());
        dialog.addComponentPair("Spinner", new DialogSpinner("spinner"));
        for (int i = 0; i < 10; i++) {
            DialogCheckBox checkBox = new DialogCheckBox("Checkbox no. " + i, "cb." + i);
            String label = "Label No. " + i;
            dialog.addComponentPair(label, checkBox);
        }
        dialog.showDialog();
        OptionDialogResult result = dialog.getResult();
        result.getData().entrySet().forEach(e -> System.out.println(e.getKey() + " : " + e.getValue().toString()));
        System.exit(0);
    }
}
