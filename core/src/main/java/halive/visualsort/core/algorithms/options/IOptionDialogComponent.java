/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options;

public interface IOptionDialogComponent<T> {

    T getSelectedValue();

    String getReturnKey();

    boolean isSelectionValid();
}
