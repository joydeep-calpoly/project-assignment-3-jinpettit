package parser;

import java.util.List;

interface Parser {
    /**
     * Parses the data and returns a list of valid articles.
     *
     * @return a List of Article objects extracted from the data.
     *         Returns an empty list if no valid articles are found
     *         or if an error occurs during parsing.
     */
    List<Article> parse();

    /**
     * Accepts a visitor for processing the parser.
     *
     * @param visitor the ParserVisitor that implements specific processing logic for this parser.
     * @return a List of Article objects processed by the visitor.
     *         The exact behavior depends on the visitor's implementation.
     */
    List<Article> accept(ParserVisitor visitor);
}