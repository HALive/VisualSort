/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VSLog {
    public static Logger logger;
    static {
        logger = LogManager.getLogger("VisualSort");
    }

}
