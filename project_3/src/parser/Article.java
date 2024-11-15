package parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

class Article {
    private final Source source;
    private final String author;
    private final String title;
    private final String description;
    private final String url;
    private final String urlToImage;
    private final String publishedAt;
    private final String content;

    // Could make Private if not testing for each field of the Article class in the test cases
    @JsonCreator
    Article(
            @JsonProperty("source") Source source,
            @JsonProperty("author") String author,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("url") String url,
            @JsonProperty("urlToImage") String urlToImage,
            @JsonProperty("publishedAt") String publishedAt,
            @JsonProperty("content") String content) {

        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    /**
     * Checks whether the article has all the required fields.
     *
     * @return true if the article has a title, description, published date, and URL; false otherwise.
     */
    boolean isValid() {
        return this.title != null && this.description != null && publishedAt != null && url != null;
    }

    /**
     * Retrieves a string listing the invalid fields of the article.
     *
     * If the article is missing any required fields (title, description, published date, or URL),
     * this method will return a space-separated string containing the names of those fields.
     *
     * @return a string of invalid fields; if all required fields are present, returns an empty string.
     */
    String getInvalidFields() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.title == null)
            stringBuilder.append("Title ");
        if (this.description == null)
            stringBuilder.append("Description ");
        if (this.publishedAt == null)
            stringBuilder.append("Published At ");
        if (this.url == null)
            stringBuilder.append("URL ");
        return stringBuilder.toString();
    }

    /**
     * Returns a string representation of the Article object, including
     * the title, description, published date, and URL.
     *
     * @return a formatted string containing the article's title, description,
     *         published date, and URL.
     */
    @Override
    public String toString() {
        return "Title: " + this.title + "\n" +
                "Description: " + this.description + "\n" +
                "Published At: " + this.publishedAt + "\n" +
                "URL: " + this.url + "\n";
    }

    /**
     * Compares this Article object to another object for equality (for testing).
     *
     * Two Article objects are considered equal if all their fields (source, author,
     * title, description, URL, urlToImage, publishedAt, and content) are equal.
     *
     * @param o the object to compare this Article against
     * @return true if the given object is an Article and is equal to this Article;
     *         false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(source, article.source) && Objects.equals(author, article.author) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(url, article.url) && Objects.equals(urlToImage, article.urlToImage) && Objects.equals(publishedAt, article.publishedAt) && Objects.equals(content, article.content);
    }

    /**
     * Returns a hash code value for the Article object.
     *
     * The hash code is computed using all fields of the Article (source, author,
     * title, description, URL, urlToImage, publishedAt, and content).
     *
     * @return a hash code value for this Article object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, author, title, description, url, urlToImage, publishedAt, content);
    }
}
