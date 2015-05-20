package halive.visualsort;

import halive.nativeloader.NativeLoader;
import halive.nativeloader.NativeLoaderUtils;
import halive.visualsort.gui.VisualSortUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;

public class VisualSort {

    public static String APPLICATION_NAME = "VisualSort";

    public static int MAX_ENTRIES = 100000;

    public static Logger logger;

    public static void main(String[] args){
        initLogger();
        logger.info("Initialized Logger");
        for(UIManager.LookAndFeelInfo i : UIManager.getInstalledLookAndFeels()) {
            if(i.getName().equals("Nimbus")) {
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
            ProgressMonitor mon = new ProgressMonitor(null, "Extracting natives...", "", 0,100);
            mon.setMillisToPopup(0);
            NativeLoader loader = new NativeLoader(VisualSort.class, null);
            File nativesFolder = new File("natives");
            loader.copyNatives(nativesFolder, mon);
            mon.setNote("Updating Library Path");
            NativeLoaderUtils.addLibraryPath(nativesFolder.getPath());
            mon.close();
        } catch (Exception e) {
            logger.error("Could not extract natives", e);
        }
        logger.info("Loaded native files");
        SwingUtilities.invokeLater(() -> {
            VisualSortUI ui = new VisualSortUI();
            ui.setVisible(true);
        });
    }

    private static void initLogger() {
        logger = LogManager.getLogger(APPLICATION_NAME);
    }
}
