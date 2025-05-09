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
        int currentDepth = 0; // Bracket depth

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            String currentElementTrimmed = currentElement.toString().trim();

            if (false) {
                System.out.print(i+":");
                System.out.print("{"+currentChar+"}");
                System.out.print("("+currentDepth+")");
                System.out.print("-"+currentElement+"-");
                System.out.println(currentElement.length());
            }

            if (currentChar == OPEN_BRACKET) {
                if (!currentElementTrimmed.isEmpty() && currentDepth == 0) {
                    // There is already a data established so this is a literal bracket
                    currentElement.append(currentChar);
                    continue;
                }

                currentDepth++;
                currentElement.append(currentChar);

//                if (currentDepth > 1) { // Keep deep nested brackets
//                    currentElement.append(currentChar);
//                }
            } else if (currentChar == CLOSE_BRACKET) {
                currentDepth = Math.max(0, currentDepth - 1);
                currentElement.append(currentChar);

//                if (currentDepth > 1) { // Keep deep nested brackets
//                    currentElement.append(currentChar);
//                }
            } else if (currentChar == DELIMITER && currentDepth == 0) {
                // Not a nested delimiter so split
                result.add(processInnerBrackets(currentElement.toString()));
//                result.add(currentElement.toString().trim());
                currentElement.setLength(0);
            } else {
                currentElement.append(currentChar);
            }
        }

        // Add any remaining element
        result.add(currentElement.toString().trim());

        return result;
    }

    /**
     * Helper method to process an element before adding it to the results.
     * Checks if the element is a structural list and removes outer brackets if so.
     *
     * @param rawElement the element after initial parsing
     * @return the processed element
     */
    private static String processInnerBrackets(String rawElement) {
        String trimmedElement = rawElement.trim();

        if (trimmedElement.startsWith(String.valueOf(OPEN_BRACKET)) && trimmedElement.endsWith(String.valueOf(CLOSE_BRACKET))) {
            // If these are in fact the outermost brackets (aka structural) then the bracket depth should be 0 at the end

            int innerDepth = 0;
            boolean isStructuralList = true;
            for (int i = 0; i < trimmedElement.length(); i++) {
                char currentChar = trimmedElement.charAt(i);
                if (currentChar == OPEN_BRACKET) {
                    innerDepth++;
                } else if (currentChar == CLOSE_BRACKET) {
                    innerDepth--;
                }

                if (innerDepth == 0 && i < trimmedElement.length() - 1) {
                    // The brackets were balanced BEFORE reaching the last outer bracket
                    // The outer brackets are thus not structural
                    isStructuralList = false;
                    break;
                }
            }

            // If structural and balanced the depth should be 0
            if (isStructuralList && innerDepth == 0) {
                // Remove surrounding structural brackets
                return trimmedElement.substring(1, trimmedElement.length() - 1);
            } else {
                // This wasn't a structural list (or malformed) so the brackets are probably literal
                return trimmedElement;
            }
        } else {
            // Not a bracketed string so normal element
            return trimmedElement;
        }
    }
}
