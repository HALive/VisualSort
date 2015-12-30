/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options.components;

import halive.visualsort.core.algorithms.options.IOptionDialogComponent;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public abstract class DialogTree<T> extends JTree implements IOptionDialogComponent<T>, TreeSelectionListener {

    public DialogTree(TreeNode root) {
        super(root);
        commonInit();
    }

    public DialogTree(TreeModel newModel) {
        super(newModel);
        commonInit();
    }

    public DialogTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
        commonInit();
    }

    private void commonInit() {
        this.addTreeSelectionListener(this);
    }
}
