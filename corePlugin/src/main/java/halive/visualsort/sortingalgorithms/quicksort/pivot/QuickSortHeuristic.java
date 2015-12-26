/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.sortingalgorithms.quicksort.pivot;

public enum QuickSortHeuristic {
    LAST_ELEMENT("Last Element", PivotImplementation::getLastElementPivot),
    RANDOM_ELEMENT("Random Element", PivotImplementation::getRandomPivot),
    MEDIAN_OF_THREE("Median of three", PivotImplementation::getMedianOfThreePivot);

    private String category;
    private IQSPivotHeuristic heuristic;

    QuickSortHeuristic(String cat, IQSPivotHeuristic ref) {
        this.category = cat;
        this.heuristic = ref;
    }

    public IQSPivotHeuristic getHeuristic() {
        return heuristic;
    }

    public String getCategory() {
        return category;
    }
}
