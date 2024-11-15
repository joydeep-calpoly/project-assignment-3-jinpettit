package parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static parser.Main.fromFile;
import static parser.Main.fromUrl;

class ParserVisitorTest {

    private final ParserVisitor parserVisitor = new ParserVisitorImplementation();

    /**
     * Tests that a SimpleParser is correctly created for files in SIMPLE format
     * and validates the visitor functionality.
     *
     * @throws IOException if an error occurs while loading the file data.
     */
    @Test
    void testSimpleParserCreation() throws IOException {
        String path = "inputs/simple.txt";
        SourceFormat simpleSource = new SourceFormat(SourceEnum.FILE, FormatEnum.SIMPLE);

        SimpleParser simpleParser = (SimpleParser) createParser(simpleSource, path);
        List<Article> articles = simpleParser.accept(parserVisitor);

        assertNotNull(simpleParser, "The parser should be created for SIMPLE format.");
        assertEquals(1, articles.size());
    }

    /**
     * Tests that a NewsParser is correctly created for files in NEWSAPI format
     * and validates the visitor functionality.
     *
     * @throws IOException if an error occurs while loading the file data.
     */
    @Test
    void testNewsApiParserCreation() throws IOException {
        String path = "inputs/newsapi.txt";
        SourceFormat newsApiSource = new SourceFormat(SourceEnum.FILE, FormatEnum.NEWSAPI);

        NewsParser newsApiParser = (NewsParser) createParser(newsApiSource, path);
        List<Article> articles = newsApiParser.accept(parserVisitor);

        assertNotNull(newsApiParser, "The parser should be created for NEWSAPI format.");
        assertEquals(20, articles.size());
    }

    /**
     * Tests that a NewsParser is correctly created when parsing data from a URL
     * in NEWSAPI format and validates the visitor functionality.
     *
     * @throws IOException if an error occurs while loading data from the URL.
     */
    @Test
    void testNewsApiParserFromUrl() throws IOException {
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=67a30faa94b04c68a3e64ca1c20912cc";
        SourceFormat urlSource = new SourceFormat(SourceEnum.URL, FormatEnum.NEWSAPI);

        NewsParser newsApiParser = (NewsParser) createParser(urlSource, url);
        List<Article> articles = newsApiParser.accept(parserVisitor);

        assertNotNull(newsApiParser, "The parser should be created for NEWSAPI format from a URL.");
        assertTrue(articles.size() > 0, "Should return at least one article.");
    }

    /**
     * Tests that an IllegalArgumentException is thrown when attempting to create
     * a SimpleParser for a URL source, as SIMPLE format is not supported for URLs.
     *
     * @throws IOException if an error occurs during the creation of the parser.
     */
    @Test
    void testSimpleParserFromUrl() throws IOException {
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=YOUR_API_KEY";
        SourceFormat urlSource = new SourceFormat(SourceEnum.URL, FormatEnum.SIMPLE);

        assertThrows(IllegalArgumentException.class, () -> createParser(urlSource, url),
                "SIMPLE format should not be supported for URLs.");
    }

    /**
     * Helper method to create a parser based on the specified source format.
     * This method loads data from a file or URL based on the source type and
     * creates the corresponding parser.
     *
     * @param sourceFormat the format of the data source (e.g., FILE or URL, SIMPLE or NEWSAPI).
     * @param path the path to the file or URL from which data is to be loaded.
     * @return the appropriate Parser instance based on the source format.
     * @throws IOException if an error occurs while loading the data.
     * @throws IllegalArgumentException if an unsupported source or format combination is specified.
     */
    private Parser createParser(SourceFormat sourceFormat, String path) throws IOException {
        String json = "";

        if (sourceFormat.getSource() == SourceEnum.URL && sourceFormat.getFormat() == FormatEnum.SIMPLE) {
            throw new IllegalArgumentException("SIMPLE format is not supported for URLs.");
        }

        if (sourceFormat.getSource() == SourceEnum.FILE) {
            json = fromFile(path);
        } else if (sourceFormat.getSource() == SourceEnum.URL) {
            json = fromUrl(path);
        }

        if (sourceFormat.getFormat() == FormatEnum.SIMPLE) {
            return new SimpleParser(json);
        } else if (sourceFormat.getFormat() == FormatEnum.NEWSAPI) {
            return new NewsParser(json);
        }

        throw new IllegalArgumentException("Unknown format: " + sourceFormat.getFormat());
    }
}
