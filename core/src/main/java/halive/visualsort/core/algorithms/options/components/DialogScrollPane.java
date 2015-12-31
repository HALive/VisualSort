/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options.components;

import halive.visualsort.core.algorithms.options.IOptionDialogComponent;

import javax.swing.JScrollPane;
import java.awt.Component;

public class DialogScrollPane<T> extends JScrollPane implements IOptionDialogComponent<T> {

    private IOptionDialogComponent<T> containedComponent;

    public DialogScrollPane(IOptionDialogComponent<T> view) {
        super((Component) view);
        this.containedComponent = view;
        setDefaultSize((Component) view);
    }

    public DialogScrollPane(IOptionDialogComponent<T> view, int vsbPolicy, int hsbPolicy) {
        super((Component) view, vsbPolicy, hsbPolicy);
        containedComponent = view;
        setDefaultSize((Component) view);
    }

    private void setDefaultSize(Component c) {
        this.setMinimumSize(c.getMinimumSize());
        this.setPreferredSize(c.getPreferredSize());
        this.setMaximumSize(c.getMaximumSize());
    }

    @Override
    public T getSelectedValue() {
        return containedComponent.getSelectedValue();
    }

    @Override
    public String getReturnKey() {
        return containedComponent.getReturnKey();
    }

    @Override
    public boolean isSelectionValid() {
        return containedComponent.isSelectionValid();
    }
}
