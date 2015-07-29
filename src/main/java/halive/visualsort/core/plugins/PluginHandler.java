package halive.visualsort.core.plugins;

import halive.visualsort.VisualSort;
import halive.visualsort.core.INamable;
import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.sorting.SortingAlgorithm;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginHandler {

    private List<IVisualSortPlugin> plugins;

    private List<DataGenerator> dataGenerators;
    private List<SortingAlgorithm> sortingAlgorithms;

    public PluginHandler( ){
        plugins = new ArrayList<>();
        dataGenerators = new ArrayList<>();
        sortingAlgorithms = new ArrayList<>();
    }

    public void searchFolder(File file,  boolean subdirs) {
        VisualSort.logger.info("Looking at: "+file.getAbsolutePath());
        for(File f : file.listFiles()) {
            if(f.isDirectory() && subdirs) {
                searchFolder(f, true);
            }else if(f.getAbsolutePath().toLowerCase().endsWith(".jar")) {
                searchJARForPlugins(f, file);
            }
        }
    }

    private void searchJARForPlugins(File f, File parentFolder) {
        VisualSort.logger.info("Searching for Plugins in "+ (f.getAbsolutePath().replace(parentFolder.getAbsolutePath(), "")));
        try {
            JarFile file = new JarFile(f);
            URLClassLoader loader = new URLClassLoader(new URL[]{f.toURI().toURL()}, getClass().getClassLoader());
            Enumeration entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                System.out.println(entry.getName());
                if(entry.getName().toLowerCase().endsWith(".class")) {
                    String classname = entry.getName().replace(".class","").replace("/",".");
                    try {
                        Class c = loader.loadClass(classname);
                        Object instance = c != null ? c.newInstance() : null;
                        if(instance != null && instance instanceof IVisualSortPlugin) {
                            addPlugin(c);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            VisualSort.logger.fatal("Could not check "+f.getAbsolutePath()+" For Plugins.",e);
            return;
        }
    }

    public void addPlugin(Class<? extends IVisualSortPlugin> c) throws IllegalAccessException, InstantiationException {
        Object inst = c.newInstance();
        if(inst instanceof IVisualSortPlugin) {
            addPlugin((IVisualSortPlugin) inst);
        }
    }

    public void addPlugin(IVisualSortPlugin plugin) {
        VisualSort.logger.info("Adding Plugin: "+plugin.getPluginName());
        plugins.add(plugin);
    }

    public void initializePlugins() {
        for (IVisualSortPlugin p : plugins) {
            VisualSort.logger.info("Initializing "+ p.getPluginName());
            initPlugin(p);
        }
        VisualSort.logger.info("Registering DataGenerators.");
        DataGenerator[] dataGenerators = new DataGenerator[this.dataGenerators.size()];
        for (int i = 0; i < dataGenerators.length; i++) {
            dataGenerators[i] = this.dataGenerators.get(i);
        }
        Arrays.sort(dataGenerators, new NamableComparator());
        DataGenerator.DATAGGENS = dataGenerators;
        VisualSort.logger.info("Registering SortingAlgoritms.");
        SortingAlgorithm[] algorithms = new SortingAlgorithm[sortingAlgorithms.size()];
        for (int i = 0; i < algorithms.length; i++) {
            algorithms[i] = sortingAlgorithms.get(i);
        }
        Arrays.sort(algorithms, new NamableComparator());
        SortingAlgorithm.ALGORTIHMS = algorithms;
    }

    private void initPlugin(IVisualSortPlugin plugin) {
        pluginLog(plugin, "Initializing DataGenerators");
        instantiate(dataGenerators, plugin.getDataGeneratorClasses(), plugin);
        pluginLog(plugin, "Initializing SortingAlgorithms");
        instantiate(sortingAlgorithms, plugin.getSortingAlgorithmClasses(), plugin);
    }

    private void instantiate(List outList, Class[] classes, IVisualSortPlugin p) {
        for (int i = 0; i < classes.length; i++) {
            Class c = classes[i];
            try {
                Object o = c.newInstance();
                if(o instanceof INamable) {
                    ((INamable) o).addToName(p.getPluginName());
                    pluginLog(p, ((INamable) o).getName() + " Instantiated.");
                    outList.add(o);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                VisualSort.logger.fatal("Could not Instantiate DataGen/SortingAlgorithm. Plugin: "+p.getPluginName(),e);
            }
        }
    }

    private void pluginLog(IVisualSortPlugin p, String msg) {
        VisualSort.logger.info("["+p.getPluginName()+"]: "+msg);
    }
}
