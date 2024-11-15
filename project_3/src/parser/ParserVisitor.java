package parser;

import java.util.List;

interface ParserVisitor {
    /**
     * Processes a NewsParser instance and performs specific operations.
     *
     * @param newsParser the NewsParser to be visited.
     * @return a List of Article objects processed by the visitor.
     *         The exact behavior depends on the visitor's implementation.
     */
    List<Article> visit(NewsParser newsParser);

    /**
     * Processes a SimpleParser instance and performs specific operations.
     *
     * @param simpleParser the SimpleParser to be visited.
     * @return a List of Article objects processed by the visitor.
     *         The exact behavior depends on the visitor's implementation.
     */
    List<Article> visit(SimpleParser simpleParser);
}
