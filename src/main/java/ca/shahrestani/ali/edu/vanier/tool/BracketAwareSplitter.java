package ca.shahrestani.ali.edu.vanier.tool;

import java.util.ArrayList;
import java.util.List;

public class BracketAwareSplitter {
    private static final char DELIMITER = ',';
    private static final char OPEN_BRACKET = '[';
    private static final char CLOSE_BRACKET = ']';

    /**
     * Splits a string by a delimiter, ignoring delimiters found within brackets.
     *
     * @param input the string to split
     * @return a list of strings resulting from the split
     */
    public static List<String> splitIgnoringBrackets(String input) {
        List<String> result = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            result.add("");
            return result;
        }

        StringBuilder currentElement = new StringBuilder();
        int currentDepth = 0; // bracket depth

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (currentChar == OPEN_BRACKET) {
                currentDepth++;
                if (currentDepth > 1) { // Keep deep nested brackets
                    currentElement.append(currentChar);
                }
            } else if (currentChar == CLOSE_BRACKET) {
                if (currentDepth > 1) { // Keep deep nested brackets
                    currentElement.append(currentChar);
                }
                currentDepth = Math.max(0, currentDepth - 1);
            } else if (currentChar == DELIMITER && currentDepth == 0) {
                // Not a nested delimiter
                result.add(currentElement.toString().trim());
                currentElement.setLength(0);
            } else {
                currentElement.append(currentChar);
            }
        }

        // Add any remaining element
        result.add(currentElement.toString().trim());

        return result;
    }
}
