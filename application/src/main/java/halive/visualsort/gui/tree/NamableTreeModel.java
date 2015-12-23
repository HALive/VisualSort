/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.gui.tree;

import halive.visualsort.core.INamable;
import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.core.plugins.PluginHandler;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;
import java.util.Map;

public class NamableTreeModel extends DefaultTreeModel {

    public NamableTreeModel(PluginHandler handler, GetReference ref) {
        super(getNodes(handler, ref));
    }

    private static DefaultMutableTreeNode getNodes(PluginHandler handler, GetReference ref) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("VisualSort");
        List<IVisualSortPlugin> plugins = handler.getPlugins();
        for (IVisualSortPlugin plugin : plugins) {
            DefaultMutableTreeNode pluginNode = new DefaultMutableTreeNode(plugin.getPluginName());
            List<INamable> algos = (List<INamable>) ref.getNamables().get(plugin);
            if (algos.size() == 0) {
                continue;
            }
            for (INamable algo : algos) {
                DefaultMutableTreeNode algoNode = new DefaultMutableTreeNode(algo);
                pluginNode.add(algoNode);
            }
            root.add(pluginNode);
        }
        return root;
    }

    public interface GetReference {

        Map getNamables();
    }
}
