package parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ParserVisitorImplementation implements ParserVisitor {
    /**
     * Processes a NewsParser instance to parse NewsAPI format data and extract valid articles.
     *
     * This method deserializes the JSON content into a News object, iterates through its articles,
     * and collects articles that pass validation. Articles with missing required fields are logged as warnings.
     *
     * @param newsParser the NewsParser instance containing the JSON data to parse.
     * @return a List of valid Article objects extracted from the NewsAPI data.
     *         Returns an empty list if no valid articles are found or if an error occurs during parsing.
     */
    @Override
    public List<Article> visit(NewsParser newsParser) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> validArticles = new ArrayList<>();

        try {
            News news = objectMapper.readValue(newsParser.getFile(), News.class);

            for (Article article : news.getArticleList()) {
                if (article.isValid()) {
                    validArticles.add(article);
                } else {
                    Main.logger.warning("Invalid Required Fields: " + article.getInvalidFields());
                }
            }

            return validArticles;

        } catch (IOException e) {
            Main.logger.severe("Error reading or parsing JSON file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Processes a SimpleParser instance to parse Simple format data and extract valid articles.
     *
     * This method deserializes the JSON content into an Article object,
     * validates it, and adds it to the list of valid articles if it meets the criteria.
     * Articles with missing required fields are logged as warnings.
     *
     * @param simpleParser the SimpleParser instance containing the JSON data to parse.
     * @return a List of valid Article objects extracted from the Simple format data.
     *         Returns an empty list if no valid articles are found or if an error occurs during parsing.
     */
    @Override
    public List<Article> visit(SimpleParser simpleParser) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> validArticles = new ArrayList<>();

        try {
            Article article = objectMapper.readValue(simpleParser.getFile(), Article.class);

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
}
