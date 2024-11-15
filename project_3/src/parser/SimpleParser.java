package parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SimpleParser implements Parser{
    private final String file;

    SimpleParser(String file) {
        this.file = file;
    }

    /**
     * Parses the Simple format file and extracts valid articles.
     *
     * This method reads the JSON content from the specified file, deserializes it into a Articles object,
     * Articles with missing required fields are logged as warnings.
     *
     * @return a List of valid Article objects extracted from the JSON file. Returns an empty list if
     *         there are no valid articles or if an error occurs during parsing.
     */
    public List<Article> parse() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> validArticles = new ArrayList<>();

        try {
            Article article = objectMapper.readValue(file, Article.class);

            if (article.isValid()) {
                validArticles.add(article);
            } else {
                Main.logger.warning("Invalid Required Fields: " + article.getInvalidFields());
            }

            return validArticles;

        } catch (IOException e) {
            Main.logger.severe("Error reading or parsing JSON file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Accepts a visitor and allows it to process this parser.
     *
     * @param visitor the visitor implementing specific processing logic for this parser.
     * @return a list of articles processed by the visitor.
     */
    public List<Article> accept(ParserVisitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Retrieves the file associated with this parser.
     *
     * @return the file as a string used for parsing data.
     */
    public String getFile() {
        return file;
    }
}
