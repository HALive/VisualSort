package halive.visualsort;

import halive.visualsort.test.nativeloader.NativeLoader;
import halive.visualsort.test.nativeloader.NativeLoaderUtils;
import halive.visualsort.core.plugins.CorePlugin;
import halive.visualsort.core.plugins.PluginHandler;
import halive.visualsort.gui.VisualSortUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;

public class VisualSort {

    public static String APPLICATION_NAME = "VisualSort";

    public static int MAX_ENTRIES = 100000;

    public static Logger logger;

    public static PluginHandler pluginHandler;

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        initLogger();
        boolean force;
        logger.info("Initialized Logger");
        for (UIManager.LookAndFeelInfo i : UIManager.getInstalledLookAndFeels()) {
            if (i.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(i.getClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
                    logger.error("Could not set look and feel", e);
                }
                break;
            }
        }
        logger.info("Look and feel set.");
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
            logger.error("Could not extract natives, Forcing J2D...", e);
            force = !(args.length > 0 && args[0].toLowerCase().equals("-no-native-check"));
        }
        logger.info("Loaded native files");
        logger.info("Loading Plugins");
        loadPlugins();
        logger.info("Initializing Plugins");
        pluginHandler.initializePlugins();
        final boolean finalForce = force;
        SwingUtilities.invokeLater(() -> {
            VisualSortUI ui = new VisualSortUI();
            ui.setVisible(true);
            if (finalForce) ui.forceJavaDRendering();
        });
    }

    private static void loadPlugins() {
        File pluginFolder = new File("plugins");
        pluginHandler = new PluginHandler();
        try {
            pluginHandler.addPlugin(CorePlugin.class);
        } catch (IllegalAccessException | InstantiationException e) {
            logger.fatal("Could not load Core Plugin. Aborting", e);
            System.exit(-1);
        }
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
            logger.info("Created Plugins folder");
            return;
        } else if (pluginFolder.isFile()) {
            logger.info("Could not load plugins. The plugin folder is a File.");
            return;
        }
        pluginHandler.searchFolder(pluginFolder, true);
    }

    private static void initLogger() {
        logger = LogManager.getLogger(APPLICATION_NAME);
    }
}
