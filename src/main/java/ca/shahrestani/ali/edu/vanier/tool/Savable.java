package ca.shahrestani.ali.edu.vanier.tool;

/**
 * Standardize methods to allow class data unloading (dumbing).
 */
public interface Savable {
    /**
     * Method to allow classes to define a way to dumb their data into a single string.
     *
     * @return a string that represents the object
     */
    String export();

    /**
     * Helper method to call the {@code export()} method of a {@code Savable} class.
     *
     * @param obj the object to export
     * @return the string that represents the object
     * @param <T> the type of object to export
     */
    static <T extends Savable> String export(T obj) {
        return obj.export();
    }
}
