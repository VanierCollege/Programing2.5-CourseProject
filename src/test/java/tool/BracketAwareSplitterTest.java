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

    /**
     * Helper method to ensure output of failed tests can help distinguish list commas from literal commas
     * 
     * @param expected the expected list of strings
     * @param actual the actual list of strings
     */
    private void assertEqualsCommaList(List<String> expected, List<String> actual) {
        expected = expected.stream().map(str -> str.replace(",", "$")).toList();
        actual = actual.stream().map(str -> str.replace(",", "$")).toList();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSplitIgnoringBrackets_singleItem() {
        String input = "tEsT";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
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
        assertEqualsCommaList(
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
        assertEqualsCommaList(
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
        assertEqualsCommaList(
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
        assertEqualsCommaList(
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
        assertEqualsCommaList(
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
        assertEqualsCommaList(
                Arrays.asList(
                        "a",
                        "b",
                        "c,   d  ",
                        "e"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_nestedEmpty1() {
        String input = "a, b, [], e";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
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
        assertEqualsCommaList(
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
        assertEqualsCommaList(
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
    public void testSplitIgnoringBrackets_literal1() {
        String input = "a, b, string with a [ in it, c";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
                Arrays.asList(
                        "a",
                        "b",
                        "string with a [ in it",
                        "c"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_literal2() {
        String input = "a, b, string with a ] in it, c";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
                Arrays.asList(
                        "a",
                        "b",
                        "string with a ] in it",
                        "c"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_literal3() {
        String input = "a, b, string with a [] in it, c";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
                Arrays.asList(
                        "a",
                        "b",
                        "string with a [] in it",
                        "c"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_literal4() {
        String input = "a, b, string with a [stm] in it, c";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
                Arrays.asList(
                        "a",
                        "b",
                        "string with a [stm] in it",
                        "c"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_literal5() {
        String input = "a, b, string with a [not, a, list] in it, c";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
                Arrays.asList(
                        "a",
                        "b",
                        "string with a [not",
                        "a",
                        "list] in it",
                        "c"
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_empty() {
        String input = "";
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);
        assertEqualsCommaList(
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
        assertEqualsCommaList(
                Arrays.asList(
                        ""
                ),
                result
        );
    }

    @Test
    public void testSplitIgnoringBrackets_complex1() {
        String input = ", a, b, [1, 2, 3], literal ] , literal [ , literal [ [x, y, z] ] , c, [ ] ], ] ] , [], [1] , " +
                "literal unbalanced 1 [ x, y, z] ], literal unbalanced 2 [ [ x, y, z] , [x, y, [[a, b, c], 2, 3], z] ,";
        List<String> expected = Arrays.asList(
                "",
                "a",
                "b",
                "1, 2, 3",
                "literal ]",
                "literal [",
                "literal [ [x",
                "y",
                "z] ]",
                "c",
                "[ ] ]",
                "] ]",
                "",
                "1",
                "literal unbalanced 1 [ x",
                "y",
                "z] ]",
                "literal unbalanced 2 [ [ x",
                "y",
                "z]",
                "x, y, [[a, b, c], 2, 3], z",
                ""
        );
        List<String> result = BracketAwareSplitter.splitIgnoringBrackets(input);

        assertEqualsCommaList(expected, result);
    }
}
