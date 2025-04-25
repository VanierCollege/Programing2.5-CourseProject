package ca.shahrestani.ali.edu.vanier.tool;

import java.util.HashMap;
import java.util.Map;

public final class DataManager {
    private static final Map<Class<? extends Savable>, SavableFactory<? extends Savable>> factories = new HashMap<>();

    private DataManager() {
    }

    /**
     * Register a class to be loadable in the {@code DataManager}
     *
     * @param type the (type) of class to be savable
     * @param factory the factory class implementing the loading
     * @param <T> the (type) of class to be savable
     */
    public static <T extends Savable> void registerFactory(Class<T> type, SavableFactory<T> factory) {
        factories.put(type, factory);
    }

    /**
     * Load a loadable class and construct a new instance of the class
     *
     * @param type the (type) of class to initialize
     * @param data the input data string
     * @return the newly constructed object
     * @param <T> the (type) of class to initialize
     */
    @SuppressWarnings("unchecked")
    public static <T extends Savable> T loadSavable(Class<T> type, String data) {
        SavableFactory<T> factory = (SavableFactory<T>) factories.get(type);
        if (factory == null) {
            throw new UnsupportedOperationException("No loading factory registered for type: " + type.getName());
        }
        return factory.load(data);
    }
}
