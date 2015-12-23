/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.pluginloader.test;

import halive.visualsort.core.plugins.PluginHandler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PluginLoaderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private String resJarPath = "pluginloader/jokeAlgorithms.jar";
    private PluginHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new PluginHandler();
    }

    @Test
    public void testPluginLoader() throws Exception {

    }
}
