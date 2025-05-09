package tool;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for BracketAwareSplitter are provided as a convenience.
 */
public class BracketAwareSplitterTest {

    @Test
    public void testSplitIgnoringBrackets_singleItem() {
        String input = "tEsT";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "tEsT"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_multipleItems() {
        String input = "Hello, World, T , E,S,T ";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "Hello",
                        "World",
                        "T",
                        "E",
                        "S",
                        "T"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_withInsideSpaces() {
        String input = "Hel lo, Wor ld";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "Hel lo",
                        "Wor ld"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_leadingEmpty() {
        String input = ", a, b";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "",
                        "a",
                        "b"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_trailingEmpty() {
        String input = "a, b,";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "a",
                        "b",
                        ""
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_middleEmpty() {
        String input = "a, , b";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "a",
                        "",
                        "b"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_nested() {
        String input = "a, b, [c,   d  ], e";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "a",
                        "b",
                        "c,   d",
                        "e"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_nestedEmpty1() {
        String input = "a, b, [], e";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "a",
                        "b",
                        "",
                        "e"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_nestedEmpty2() {
        String input = "a, b, [,,], e";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "a",
                        "b",
                        ",,",
                        "e"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_nestedDeep() {
        String input = "a, b, [1, 2, [x, y, z], 4], e";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        "a",
                        "b",
                        "1, 2, [x, y, z], 4",
                        "e"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_empty() {
        String input = "";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        ""
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_null() {
        String input = null;
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        Assertions.assertEquals(
                Arrays.asList(
                        ""
                ),
                result
        );
    }
}
