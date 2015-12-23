/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.plugins;

import halive.visualsort.core.INamable;
import halive.visualsort.core.VSLog;
import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class PluginHandler {

    private List<IVisualSortPlugin> plugins;

    private Map<IVisualSortPlugin, List<DataGenerator>> dataGenerators;
    private Map<IVisualSortPlugin, List<SortingAlgorithm>> sortingAlgorithms;

    public PluginHandler() {
        plugins = new ArrayList<>();
        dataGenerators = new HashMap<>();
        sortingAlgorithms = new HashMap<>();
    }

    public void searchFolder(File file, boolean subdirs) {
        VSLog.logger.info("Looking at: " + file.getAbsolutePath());
        for (File f : file.listFiles()) {
            if (f.isDirectory() && subdirs) {
                searchFolder(f, true);
            } else if (f.getAbsolutePath().toLowerCase().endsWith(".jar")) {
                searchJARForPlugins(f, file);
            }
        }
    }

    private void searchJARForPlugins(File f, File parentFolder) {
        VSLog.logger.info("Searching for Plugins in " +
                (f.getAbsolutePath().replace(parentFolder.getAbsolutePath(), "")));
        try {
            JarFile file = new JarFile(f);
            URLClassLoader loader = new URLClassLoader(new URL[]{f.toURI().toURL()}, getClass().getClassLoader());
            Enumeration entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                System.out.println(entry.getName());
                if (entry.getName().toLowerCase().endsWith(".class")) {
                    String classname = entry.getName().replace(".class", "").replace("/", ".");
                    try {
                        Class c = loader.loadClass(classname);
                        Object instance = c != null ? c.newInstance() : null;
                        if (instance != null && instance instanceof IVisualSortPlugin) {
                            addPlugin(c);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            VSLog.logger.log(Level.SEVERE, "Could not check " + f.getAbsolutePath() + " For Plugins.", e);
        }
    }

    public void addPlugin(Class<? extends IVisualSortPlugin> c) throws IllegalAccessException,
            InstantiationException {
        Object inst = c.newInstance();
        if (inst != null) {
            addPlugin((IVisualSortPlugin) inst);
        }
    }

    public void addPlugin(IVisualSortPlugin plugin) {
        VSLog.logger.info("Adding Plugin: " + plugin.getPluginName());
        //System.out.println("Adding Plugin: " + plugin.getPluginName());
        plugins.add(plugin);
        dataGenerators.put(plugin, new ArrayList<>());
        sortingAlgorithms.put(plugin, new ArrayList<>());
    }

    public void initializePlugins() {
        for (IVisualSortPlugin p : plugins) {
            VSLog.logger.info("Initializing " + p.getPluginName());
            initPlugin(p);
        }
    }

    private void initPlugin(IVisualSortPlugin plugin) {
        pluginLog(plugin, "Initializing DataGenerators");
        instantiate(dataGenerators, plugin.getDataGeneratorClasses(), plugin);
        pluginLog(plugin, "Initializing SortingAlgorithms");
        instantiate(sortingAlgorithms, plugin.getSortingAlgorithmClasses(), plugin);
    }

    private void instantiate(Map out, Class[] classes, IVisualSortPlugin p) {
        List outList = (List) out.get(p);
        for (int i = 0; i < classes.length; i++) {
            Class c = classes[i];
            try {
                Object o = c.newInstance();
                if (o instanceof INamable) {
                    pluginLog(p, ((INamable) o).getName() + " Instantiated.");
                    outList.add(o);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                VSLog.logger.log(Level.SEVERE, "Could not Instantiate DataGen/SortingAlgorithm. " +
                        "Plugin: " + p.getPluginName(), e);
            }
        }
    }

    public List<IVisualSortPlugin> getPlugins() {
        return plugins;
    }

    public Map<IVisualSortPlugin, List<SortingAlgorithm>> getSortingAlgorithms() {
        return sortingAlgorithms;
    }

    public Map<IVisualSortPlugin, List<DataGenerator>> getDataGenerators() {
        return dataGenerators;
    }

    private void pluginLog(IVisualSortPlugin p, String msg) {
        VSLog.logger.info("[" + p.getPluginName() + "]: " + msg);
    }
}
