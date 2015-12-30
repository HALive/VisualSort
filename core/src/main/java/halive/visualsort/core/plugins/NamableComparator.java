/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.plugins;

import halive.visualsort.core.interfaces.IAlgorithm;

import java.util.Comparator;

public class NamableComparator implements Comparator<IAlgorithm> {

    @Override
    public int compare(IAlgorithm o1, IAlgorithm o2) {
        return (int) Math.signum(o1.getName().compareTo(o2.getName()));
    }
}
