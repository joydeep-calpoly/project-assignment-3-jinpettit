package parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static parser.Main.*;

class SimpleParserTest {

    private SimpleParser simpleParser;

    /**
     * Tests the parsing of a valid Simple file.
     * This test verifies that the article is parsed correctly and match expected values.
     */
    @Test
    void testValidSimple() throws IOException {
        String json = fromFile("inputs/simple_valid.txt");
        simpleParser = new SimpleParser(json);

        List<Article> articles = simpleParser.parse();

        Article expectedArticle = new Article(
                new Source("source1", "Source One"),
                "Author One",
                "Title One",
                "Description One",
                "http://example.com/article1",
                "http://example.com/image1.jpg",
                "2023-10-17T12:00:00Z",
                "Content of article one"
        );

        assertEquals(expectedArticle, articles.get(0));
        assertEquals(1, articles.size());
    }

    /**
     * Tests the parsing of a Simple file where the title field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingTitle() throws IOException {
        String json = fromFile("inputs/simple_missing_title.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles missing title should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where the description field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingDescription() throws IOException {
        String json = fromFile("inputs/simple_missing_description.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles missing description should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where the publishedAt field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingPublishedAt() throws IOException {
        String json = fromFile("inputs/simple_missing_publishedAt.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles missing publishedAt should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where the URL field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingURL() throws IOException {
        String json = fromFile("inputs/simple_missing_url.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles missing URL should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where two essential fields (e.g., title and description) are missing.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingTwo() throws IOException {
        String json = fromFile("inputs/simple_missing_two.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles missing both title and description should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where three essential fields (description, publishedAt, and URL) are missing.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingThree() throws IOException {
        String json = fromFile("inputs/simple_missing_three.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles missing description, publishedAt, and URL should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where all essential fields (title, description, publishedAt, and URL) are missing.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingAll() throws IOException {
        String json = fromFile("inputs/simple_missing_all.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles missing title, description, publishedAt, and URL should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where the article is empty.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testSimpleMissingEmpty() throws IOException {
        String json = fromFile("inputs/simple_empty.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Articles empty should not be parsed.");
    }

    /**
     * Tests the parsing of a Simple file where its empty.
     * Verifies that no articles are included in the output.
     */
    @Test
    void testSimpleEmptyFile() throws IOException {
        String json = fromFile("inputs/simple_empty_file.txt");
        simpleParser = new SimpleParser(json);
        List<Article> articles = simpleParser.parse();

        assertEquals(0, articles.size(), "Nothing to parse.");
    }
}
