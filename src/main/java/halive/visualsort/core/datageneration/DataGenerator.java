package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.INamable;

public abstract class DataGenerator implements INamable{

    public static DataGenerator[] DATAGGENS;

    private String name;
    private String description;

    public DataGenerator(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Override
    public void addToName(String s) {
        name+=" ("+s+")";
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName();
    }

    public abstract void generateData(DataEntry[] entries, int maxvalue);

}
