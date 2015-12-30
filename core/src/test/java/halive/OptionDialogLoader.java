/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive;

import halive.visualsort.core.algorithms.options.OptionDialog;
import halive.visualsort.core.algorithms.options.OptionDialogResult;
import halive.visualsort.core.algorithms.options.components.DialogCheckBox;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class OptionDialogLoader {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                UIManager.setLookAndFeel(info.getClassName());
            }
        }
        OptionDialog dialog = new OptionDialog(null, "Dialog Test");
        dialog.addComponentPair("Select Your Options", null);
        for (int i = 0; i < 10; i++) {
            DialogCheckBox checkBox = new DialogCheckBox("Checkbox no. " + i, "cb." + i);
            String label = "Label No. " + i;
            dialog.addComponentPair(label, checkBox);
        }
        dialog.showDialog();
        OptionDialogResult result = dialog.getResult();
        for (int i = 0; i < 10; i++) {
            Object object = result.getResultForKey("cb." + i);
            System.out.println("Checkbox " + i + ": " + object);
        }
        System.exit(0);
    }
}
