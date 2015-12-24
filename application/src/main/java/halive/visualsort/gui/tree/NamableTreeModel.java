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

/**
 * Describes the Tree Modell for the Trees used to
 * Select the DataGenerators/SortingAlgorithms
 */
@SuppressWarnings("unchecked")
public class NamableTreeModel extends DefaultTreeModel {

    /**
     * Creates a new NamableTreeModel
     *
     * @param handler see getNodes
     * @param ref     see getNodes
     */
    public NamableTreeModel(PluginHandler handler, GetReference ref) {
        super(getNodes(handler, ref));
    }

    /**
     * Creates a Tree Structure for a Specific DataSource from
     * the Plugin Handler(either getSortingAlgorithms
     * or getDataGenerators)
     *
     * @param handler the PluginHandler
     * @param ref     the MethodReference to either getSortingAlgorithms, getDataGenerators
     * @return Rootnode Containing the Namables
     */
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
