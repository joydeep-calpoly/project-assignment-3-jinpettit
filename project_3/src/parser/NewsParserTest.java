package parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static parser.Main.*;

class NewsParserTest {

    private NewsParser newsParser;

    /**
     * Tests the parsing of a valid NewsAPI file.
     * This test verifies that the article is parsed correctly and match expected values.
     */
    @Test
    void testValidNewsAPI() throws IOException {
        String json = fromFile("inputs/valid.json");
        newsParser = new NewsParser(json);

        List<Article> articles = newsParser.parse();

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
     * Tests the parsing of NewsAPI File.
     * Verifies that at least one article was parsed from the URL.
     * Can't test exact size since article amount is constantly changing.
     */
    @Test
    void testNewsAPIFile() throws IOException {
        String json = fromFile("inputs/newsapi.txt");
        newsParser = new NewsParser(json);

        List<Article> articles = newsParser.parse();
        assertEquals(20, articles.size());
    }

    /**
     * Tests the parsing of a NewsAPI file missing fields.
     * This test verifies that no articles are returned when required fields are missing.
     */
    @Test
    void testNewsAPIMissingFields() throws IOException {
        String json = fromFile("inputs/invalid.json");
        newsParser = new NewsParser(json);

        List<Article> articles = newsParser.parse();
        assertEquals(0, articles.size());
    }

    /**
     * Tests the parsing of a NewsAPI file where the title field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingTitle() throws IOException {
        String json = fromFile("inputs/missing_title.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles missing title should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where the description field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingDescription() throws IOException {
        String json = fromFile("inputs/missing_description.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles missing description should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where the publishedAt field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingPublishedAt() throws IOException {
        String json = fromFile("inputs/missing_publishedAt.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles missing publishedAt should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where the URL field is missing.
     * Ensures that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingURL() throws IOException {
        String json = fromFile("inputs/missing_url.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles missing URL should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where two essential fields (e.g., title and description) are missing.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingTwo() throws IOException {
        String json = fromFile("inputs/missing_two.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles missing both title and description should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where three essential fields (description, publishedAt, and URL) are missing.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingThree() throws IOException {
        String json = fromFile("inputs/missing_three.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles missing description, publishedAt, and URL should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where all essential fields (title, description, publishedAt, and URL) are missing.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingAll() throws IOException {
        String json = fromFile("inputs/missing_all.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles missing title, description, publishedAt, and URL should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where the article is empty.
     * Verifies that such articles are not included in the output.
     */
    @Test
    void testNewsAPIMissingEmpty() throws IOException {
        String json = fromFile("inputs/empty.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Articles empty should not be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where the article only has the four essential attributes.
     * Verifies that such articles are included in the output.
     */
    @Test
    void testNewsAPIMissingValid() throws IOException {
        String json = fromFile("inputs/missing_valid.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(1, articles.size(), "Articles should still be parsed.");
    }

    /**
     * Tests the parsing of a NewsAPI file where its empty.
     * Verifies that no articles are included in the output.
     */
    @Test
    void testNewsAPIEmptyFile() throws IOException {
        String json = fromFile("inputs/empty_file.json");
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertEquals(0, articles.size(), "Nothing to parse.");
    }

    /**
     * Tests the parsing of NewsAPI URL.
     * Verifies that at least one article was parsed from the URL.
     * Can't test exact size since article amount is constantly changing.
     */
    @Test
    void testNewsAPIUrl() throws IOException {
        String json = fromUrl(url);
        newsParser = new NewsParser(json);
        List<Article> articles = newsParser.parse();

        assertTrue(articles.size() > 0, "Should return at least one article.");
    }

    /**
     * Tests the handling of an empty or malformed NewsAPI URL.
     * Verifies that an IOException is thrown when attempting to parse an empty URL.
     * This test ensures that the parser correctly handles invalid or empty URLs.
     */
    @Test
    void testNewsAPIUrlEmpty() throws IOException {
        String url = "";

        assertThrows(IOException.class, () -> {
            String json = fromUrl(url);
            NewsParser newsParser = new NewsParser(json);
            newsParser.parse();
        }, "Empty URL should throw an IOException.");
    }
}
