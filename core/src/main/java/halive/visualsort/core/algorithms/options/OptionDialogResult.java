/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options;

import java.util.HashMap;
import java.util.Map;

public class OptionDialogResult {

    private Map<String, Object> data = new HashMap<>();

    public void addToResults(IOptionDialogComponent component) {
        data.put(component.getReturnKey(), component.getSelectedValue());
    }

    public Object getResultForKey(String key) {
        return data.get(key);
    }

    public Map<String, Object> getData() {
        return data;
    }
}
