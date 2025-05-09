package ca.shahrestani.ali.edu.vanier.tool;

import java.util.Map;

/**
 * Standardize method to allow class data loading (factory construction).
 */
public interface SavableFactory<T extends Savable> {
    /**
     * Method to construct a new instance of T based on a string input.
     * <p></p>
     * Note that this method assumes the input string is in the expected format. Fail-fast method.
     *
     * @param str the data string
     * @return the newly constructed object of type T
     */
    T load(String str, Map<String, Object> dependencies);
}
