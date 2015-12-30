/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options.components;

import halive.visualsort.core.algorithms.options.IOptionDialogComponent;

import javax.swing.Icon;
import javax.swing.JCheckBox;

public class DialogCheckBox extends JCheckBox implements IOptionDialogComponent<Boolean> {

    private String returnKey;

    public DialogCheckBox(String returnKey) {
        this.returnKey = returnKey;
    }

    public DialogCheckBox(Icon icon, boolean selected, String returnKey) {
        super(icon, selected);
        this.returnKey = returnKey;
    }

    public DialogCheckBox(String text, String returnKey) {
        super(text);
        this.returnKey = returnKey;
    }

    public DialogCheckBox(String text, boolean selected, String returnKey) {
        super(text, selected);
        this.returnKey = returnKey;
    }

    public DialogCheckBox(String text, Icon icon, boolean selected, String returnKey) {
        super(text, icon, selected);
        this.returnKey = returnKey;
    }

    public DialogCheckBox(String text, Icon icon, String returnKey) {
        super(text, icon);
        this.returnKey = returnKey;
    }

    public DialogCheckBox(Icon icon, String returnKey) {
        super(icon);
        this.returnKey = returnKey;
    }

    @Override
    public Boolean getSelectedValue() {
        return this.isSelected();
    }

    @Override
    public String getReturnKey() {
        return returnKey;
    }
}
