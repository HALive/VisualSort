package halive.visualsort.core.datageneration;

import halive.visualsort.core.DataEntry;

public abstract class DataGenerator {

    public static final DataGenerator[] DATAGGENS;

    static {
        DATAGGENS = new DataGenerator[]{new RandomDataGenerator(), new SineGenerator(),
                new SawtoohGenerator(), new LinearAscendingGnerator(), new LinearDescendingGnerator(),
                new PositiveParabolicGenerator(), new NegativeParabolicGenerator(),
                new TriangleGenerator(), new InvertedTriangleGenerator()};
    }

    private String name;
    private String description;

    public DataGenerator(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
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
