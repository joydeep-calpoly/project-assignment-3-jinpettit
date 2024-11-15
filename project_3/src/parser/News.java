package parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class News {
    private final String status;
    private final int totalResults;
    private final List<Article> articleList;

    @JsonCreator
    private News(
            @JsonProperty("status") String status,
            @JsonProperty("totalResults") int totalResults,
            @JsonProperty("articles") List<Article> articleList) {

        this.status = status;
        this.totalResults = totalResults;
        this.articleList = articleList;
    }

    /**
     * Retrieves the list of articles in the news.
     *
     * @return a List of Article objects representing the articles.
     */
    public List<Article> getArticleList() {
        return articleList;
    }
}
