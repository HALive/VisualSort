/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.interfaces;

public interface IAlgorithm {

    String getName();

    default String getCategory() {
        return "Default";
    }
}
