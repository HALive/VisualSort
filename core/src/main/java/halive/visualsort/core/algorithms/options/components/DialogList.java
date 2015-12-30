/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options.components;

import halive.visualsort.core.algorithms.options.IOptionDialogComponent;

import javax.swing.JList;

public class DialogList<T> extends JList<T> implements IOptionDialogComponent<T> {


    @Override
    public String getReturnKey() {
        return null;
    }

    @Override
    public boolean isSelectionValid() {
        return true;
    }
}
