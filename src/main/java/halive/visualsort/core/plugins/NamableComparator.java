/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.plugins;

import halive.visualsort.core.INamable;

import java.util.Comparator;

public class NamableComparator implements Comparator<INamable> {

    @Override
    public int compare(INamable o1, INamable o2) {
        return (int) Math.signum(o1.getName().compareTo(o2.getName()));
    }
}
