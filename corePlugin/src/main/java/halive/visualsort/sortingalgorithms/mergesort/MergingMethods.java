/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.mergesort;

//TODO Implement other Merging Methods
public enum MergingMethods {
    IN_PLACE(MergeUtils::mergeInPlace, "In-Place Merging"),
    OP_MERGE_V1(MergeUtils::merge, "Efficient Non In-Place Merge"),
    OP_MERGE_V2(MergeUtils::merge, "Inefficient Non In-Place Merge"),
    BITONIC_OP_MERGE(MergeUtils::merge, "Bitonic Non In-Place Merge");

    private String name;
    private MergeUtils.IMerge mergeMethod;

    MergingMethods(MergeUtils.IMerge mergeMethod, String name) {
        this.mergeMethod = mergeMethod;
        this.name = name;
    }

    public MergeUtils.IMerge getMergeMethod() {
        return mergeMethod;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
