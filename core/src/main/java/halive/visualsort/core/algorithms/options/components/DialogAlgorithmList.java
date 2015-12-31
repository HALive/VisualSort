/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.core.algorithms.options.components;

import halive.visualsort.core.interfaces.IAlgorithm;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class DialogAlgorithmList<A extends IAlgorithm> extends DialogList<A> {

    private List<A> algorithms;
    private Class<? extends IAlgorithm>[] validAlgorithms;
    private boolean inverted;

    public DialogAlgorithmList(String returnKey, List<A> algorithms,
                               Class<? extends IAlgorithm>[] algoClasses, boolean inverted) {
        super(returnKey);
        this.validAlgorithms = algoClasses;
        this.algorithms = new ArrayList<>();
        this.inverted = inverted;
        algorithms.stream().filter(this::isAlgorithmValid).forEach(a -> {
            this.algorithms.add(a);
        });
        this.setModel(new AlgorithmListModel());
    }

    private boolean isAlgorithmValid(IAlgorithm algorithm) {
        for (Class<? extends IAlgorithm> algo : validAlgorithms) {
            if (algorithm.hasOptionDialog()) {
                return false;
            }
            if (algo.isInstance(algorithm)) {
                return !inverted;
            }
        }
        return inverted;
    }

    private class AlgorithmListModel implements ListModel<A> {

        @Override
        public int getSize() {
            return algorithms.size();
        }

        @Override
        public A getElementAt(int index) {
            return algorithms.get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {

        }

        @Override
        public void removeListDataListener(ListDataListener l) {

        }
    }
}
