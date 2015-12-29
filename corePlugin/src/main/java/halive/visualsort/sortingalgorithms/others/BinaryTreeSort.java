/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.others;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.sorting.SortingAlgorithm;

import java.awt.Color;

/**
 * This Class Sorts The Data By Storing it in a Binary Tree (sorted using Binary Search)
 * The visualisation is prettty much identical to Counting Sort
 */
@SuppressWarnings({"ConstantConditions", "ForLoopReplaceableByForEach"})
public class BinaryTreeSort extends SortingAlgorithm {

    public BinaryTreeSort() {
        super("Binary Tree Sort", " ");
    }

    @Override
    public void doSort(DataEntry[] data, SortingHandler sortingHandler) {
        TreeNode mainNode = null;
        for (int i = 0; i < data.length; i++) {
            if (mainNode == null) {
                mainNode = new TreeNode(data[i].getValue());
            } else {
                mainNode.insertValue(data[i].getValue(), sortingHandler);
            }
            sortingHandler.onSwapped();
            data[i].setTemporaryColor(Color.GRAY);
        }
        int[] values = mainNode.getContents();
        for (int i = 0; i < values.length; i++) {
            data[i].setValue(values[i]);
            data[i].removeTemporaryColor();
            data[i].setPrimaryColor(Color.red);
            sortingHandler.onSwapped();
        }
    }

    @Override
    public boolean allowExport() {
        return false;
    }

    @Override
    public String getCategory() {
        return "Others";
    }

    public static class TreeNode {

        private int value;
        private TreeNode[] nodes = new TreeNode[2];

        public TreeNode(int value) {
            this.value = value;
        }

        public void insertValue(int i, SortingHandler h) {
            int index = h.compare(i > value) ? 1 : 0;
            if (h.compare(nodes[index] != null)) {
                nodes[index].insertValue(i, h);
            } else {
                nodes[index] = new TreeNode(i);
            }
        }

        public void setRightNode(TreeNode node) {
            nodes[1] = node;
        }

        public void setLeftNode(TreeNode node) {
            nodes[0] = node;
        }

        public boolean containsElement(int i) {
            if (i == value) {
                return true;
            }
            int idx = (i > value) ? 1 : 0;
            return nodes[idx] != null && nodes[idx].containsElement(i);
        }

        public int[] getContents() {
            int[][] vals = new int[2][];
            int sumArrayLen = 1;
            for (int i = 0; i < vals.length; i++) {
                vals[i] = nodes[i] != null ? nodes[i].getContents() : null;
                sumArrayLen += vals[i] != null ? vals[i].length : 0;
            }
            int[] out = new int[sumArrayLen];
            int pos = 0;
            for (int i = 0; i < vals.length; i++) {
                if (vals[i] != null) {
                    for (int j = 0; j < vals[i].length; j++) {
                        out[pos] = vals[i][j];
                        pos++;
                    }
                }
                if (i == 0) {
                    out[pos] = value;
                    pos++;
                }
            }
            return out;
        }
    }
}
