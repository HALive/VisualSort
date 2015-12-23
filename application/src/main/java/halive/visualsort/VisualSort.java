/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort;

import halive.nativeloader.NativeLoader;
import halive.nativeloader.NativeLoaderUtils;
import halive.visualsort.core.Configuration;
import halive.visualsort.core.VSLog;
import halive.visualsort.core.plugins.PluginHandler;
import halive.visualsort.gui.VisualSortUI;

import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.util.logging.Level;

public class VisualSort {

    public static String APPLICATION_NAME = "VisualSort";

    public static int MAX_ENTRIES = 100000;

    public static PluginHandler pluginHandler;

    public static void main(String[] args) {
        boolean force = true;
        VSLog.logger.info("Loading Configuration");
        Configuration cfg = Configuration.loadFormFile(new File("config.json"));
        MAX_ENTRIES = cfg.getMaxValues();
        initializeLookAndFeel();
        if (cfg.isLoadOpenGL()) {
            force = extractAndLoadNatives(args);
        }
        PluginHandler handler = initializePlugins(cfg);
        launchApplication(force, cfg, handler);
    }

    private static void launchApplication(boolean force, Configuration cfg, PluginHandler handler) {
        final boolean finalForce = force;
        SwingUtilities.invokeLater(() -> {
            VisualSortUI ui = new VisualSortUI(handler);
            ui.setVisible(true);
            if (finalForce) {
                ui.forceJavaDRendering();
            }
        });
    }

    private static PluginHandler initializePlugins(Configuration cfg) {
        VSLog.logger.info("Loading Plugins");
        loadPlugins(cfg);
        VSLog.logger.info("Initializing Plugins");
        pluginHandler.initializePlugins();
        return pluginHandler;
    }

    private static void initializeLookAndFeel() {
        VSLog.logger.info("Initialized Logger");
        for (UIManager.LookAndFeelInfo i : UIManager.getInstalledLookAndFeels()) {
            if (i.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(i.getClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
                    VSLog.logger.log(Level.SEVERE, "Could not set look and feel", e);
                }
                break;
            }
        }
        VSLog.logger.info("Look and feel set.");
    }

    private static boolean extractAndLoadNatives(String[] args) {
        boolean force;
        try {
            ProgressMonitor mon = new ProgressMonitor(null, "Extracting natives...", "", 0, 100);
            mon.setMillisToPopup(0);
            NativeLoader loader = new NativeLoader(VisualSort.class);
            File nativesFolder = new File("natives");
            loader.copyNatives(nativesFolder, mon);
            mon.setNote("Updating Library Path");
            NativeLoaderUtils.addLibraryPath(nativesFolder.getPath());
            mon.close();
            force = false;
        } catch (Exception e) {
            VSLog.logger.log(Level.SEVERE, "Could not extract natives, Forcing J2D...", e);
            force = !(args.length > 0 && args[0].toLowerCase().equals("-no-native-check"));
        }
        VSLog.logger.info("Loaded native files");
        return force;
    }

    private static void loadPlugins(Configuration cfg) {
        File pluginFolder = new File("plugins");
        pluginHandler = new PluginHandler();
        try {
            pluginHandler.addPlugin(CorePlugin.class);
        } catch (IllegalAccessException | InstantiationException | Error e) {
            VSLog.logger.log(Level.SEVERE, "Could not load Core Plugin. Aborting", e);
            System.exit(-1);
        }
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
            VSLog.logger.info("Created Plugins folder");
            return;
        } else if (pluginFolder.isFile()) {
            VSLog.logger.info("Could not load plugins. The plugin folder is a File.");
            return;
        }
        if (cfg.isAllowExternalPlugins()) {
            pluginHandler.searchFolder(pluginFolder, true);
        }
    }
}
