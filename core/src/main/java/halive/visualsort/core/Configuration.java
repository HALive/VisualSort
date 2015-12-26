/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

@SuppressWarnings("FieldCanBeLocal")
public class Configuration {

    private int maxValues = 10000;
    private boolean loadOpenGL = true;
    private boolean allowExternalPlugins = true;
    private boolean allowVisualisationExport = true;

    public boolean isAllowExternalPlugins() {
        return allowExternalPlugins;
    }

    public boolean isLoadOpenGL() {
        return loadOpenGL;
    }

    public int getMaxValues() {
        return maxValues;
    }

    public boolean isAllowVisualisationExport() {
        return allowVisualisationExport;
    }

    public static Configuration loadFormFile(File f) {
        Gson gson = new Gson();
        if (!f.exists()) {
            Configuration configuration = new Configuration();
            try {
                FileWriter out = new FileWriter(f);
                gson.toJson(configuration, out);
                out.close();
            } catch (IOException e) {
                VSLog.logger.log(Level.INFO, "Error Writing Config", e);
            }
            return configuration;
        }
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            Object o = gson.fromJson(reader, Configuration.class);
            return (Configuration) o;
        } catch (IOException e) {
            VSLog.logger.log(Level.INFO, "Error Loading Config", e);
            return new Configuration();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                VSLog.logger.log(Level.INFO, "Error Loading Config", e);
            }
        }
    }
}
