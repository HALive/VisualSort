/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options.components;

import halive.visualsort.core.algorithms.options.IOptionDialogComponent;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class DialogSpinner extends JSpinner implements IOptionDialogComponent {

    private String returnKey;

    public DialogSpinner(String returnKey) {
        this.returnKey = returnKey;
    }

    public DialogSpinner(SpinnerModel model, String returnKey) {
        super(model);
        this.returnKey = returnKey;
    }

    @Override
    public Object getSelectedValue() {
        return this.getValue();
    }

    @Override
    public String getReturnKey() {
        return returnKey;
    }

    @Override
    public boolean isSelectionValid() {
        return true;
    }
}
