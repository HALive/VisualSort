/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.pluginloader.test;

import halive.visualsort.core.datageneration.DataGenerator;
import halive.visualsort.core.plugins.IVisualSortPlugin;
import halive.visualsort.core.plugins.PluginHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class PluginLoaderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private String resJarPath = "pluginloader/jokeAlgorithms.jar";
    private File pluginFolder;
    private PluginHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new PluginHandler();
        pluginFolder = new File(folder.getRoot(), "plugins");
        pluginFolder.mkdir();
        InputStream in = getClass().getClassLoader().getResourceAsStream(resJarPath);
        FileOutputStream out = new FileOutputStream(new File(pluginFolder, "test.jar"));
        int r = -1;
        while ((r = in.read()) != -1) {
            out.write(r);
        }
        in.close();
        out.close();
    }

    @Test
    public void testPluginLoader() throws Exception {
        handler.searchFolder(pluginFolder, true);
        assertTrue("Invalid Number OF plugins Found", handler.getPlugins().size() == 1);
        IVisualSortPlugin p = handler.getPlugins().get(0);
        assertTrue("The Plugin is no instance of the Plugin", p instanceof IVisualSortPlugin);
        assertTrue("Plugin names are Invalid", p.getPluginName().equals("Joke Algorithms"));
        handler.initializePlugins();
        SortingAlgorithm a = handler.getSortingAlgorithms().get(p).get(0);
        assertTrue("BogoSort name is Invalid", a.getName().equals("BogoSort"));
        DataGenerator g = handler.getDataGenerators().get(p).get(0);
        assertTrue("TrashGen Name is Invalid", g.getName().equals("Trash Gen"));
    }
}
