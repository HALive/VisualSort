/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.export;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortingExporter {

    private SortingHandler handler;
    private List<SortingStep> steps;
    private File outputFile;

    public SortingExporter(SortingHandler handler, File outputFile) {
        this.handler = handler;
        this.outputFile = outputFile;
        this.steps = new ArrayList<>();
    }

    public void addStep(DataEntry[] entries) {
        steps.add(new SortingStep(entries));
    }

    public void export() {

    }
}
