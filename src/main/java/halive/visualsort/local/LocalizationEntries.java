/*
 * Copyright (c) HALive 2015
 * See LICENCE For Licence information.
 */

package halive.visualsort.local;

public enum LocalizationEntries {
    DATAGEN_RANDOM_NAME("datagen.random.name"),
    DATAGEN_RANDOM_DECRIPTION("datagen.random.description"),
    DATAGEN_SINE_NAME("datagen.sine.name"),
    DATAGEN_SINE_DECRIPTION("datagen.sine.description"),
    DATAGEN_SAWTOOTH_NAME("datagen.sawtooth.name"),
    DATAGEN_SAWTOOTH_DECRIPTION("datagen.sawtooth.description"),
    DATAGEN_LIND_NAME("datagen.lineardesc.name"),
    DATAGEN_LIND_DECRIPTION("datagen.lineardesc.description"),
    DATAGEN_LINA_NAME("datagen.linearasc.name"),
    DATAGEN_LINA_DECRIPTION("datagen.linearasc.description"),
    DATAGEN_PSQUARE_NAME("datagen.possquare.name"),
    DATAGEN_PSQUARE_DECRIPTION("datagen.possquare.description"),
    DATAGEN_NSQUARE_NAME("datagen.negsquare.name"),
    DATAGEN_NSQUARE_DECRIPTION("datagen.negsquare.description"),
    SORTER_BUBBLESORT_NAME("sorter.bubblesort.name"),
    SORTER_BUBBLESORT_DESCRIPTION("sorter.bubblesort.description");

    private String key;

    LocalizationEntries(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String localize() {
        return Localizer.localize(key);
    }

    @Override
    public String toString() {
        return this.localize();
    }
}
