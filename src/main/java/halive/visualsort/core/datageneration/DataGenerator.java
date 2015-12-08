package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.INamable;

/**
 * This Class Describes a Abstract Class thats used to Generate data that can be sorted using different
 * Sorting Algorithms
 * <p>
 * If this Class gets extended. the created class has to have a default Constructor (no Parameters) in order to be used
 * as a Datagenerator
 *
 * @author HALive
 */
public abstract class DataGenerator implements INamable {

    public static DataGenerator[] DATAGGENS;

    private String name;
    private String description;

    /**
     * Creates a new Datagen
     *
     * @param name        The name Of the Datagenerator
     * @param description A Short Description of the ALgorithm (currently not used)
     */
    public DataGenerator(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Override
    public void addToName(String s) {
        name += " (" + s + ")";
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * This Method is called when data should be generated
     *
     * @param entries  The Entry Array to generate Into
     * @param maxvalue The Maximalvalue the entries should have.
     *                 Every Value in the array should be smaller or equal to this number and greater then 0
     */
    public abstract void generateData(DataEntry[] entries, int maxvalue);

}
