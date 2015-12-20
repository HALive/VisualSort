/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class VSLog {

    public static Logger logger;
    static {
        logger = Logger.getLogger("VisualSort");
    }

    private static class LoggerFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            return String.format("");
        }
    }
}
