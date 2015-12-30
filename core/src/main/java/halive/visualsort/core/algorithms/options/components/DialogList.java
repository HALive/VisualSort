/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options.components;

import halive.visualsort.core.algorithms.options.IOptionDialogComponent;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;

public class DialogList<T> extends JList<T> implements IOptionDialogComponent<T> {

    private String returnKey;

    public DialogList(String returnKey) {
        this.returnKey = returnKey;
        init();
    }

    public DialogList(ListModel<T> dataModel, String returnKey) {
        super(dataModel);
        this.returnKey = returnKey;
        init();
    }

    public DialogList(T[] listData, String returnKey) {
        super(listData);
        this.returnKey = returnKey;
        init();
    }

    private void init() {
        Dimension size = new Dimension(200, 100);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public JComponent getInScrollPane() {
        JScrollPane scrollPane = new DialogScrollPane<>(this);
        this.setPreferredSize(null);
        this.setMinimumSize(null);
        this.setMaximumSize(null);
        return scrollPane;
    }

    @Override
    public String getReturnKey() {
        return returnKey;
    }

    @Override
    public boolean isSelectionValid() {
        return getSelectedValue() != null;
    }
}
