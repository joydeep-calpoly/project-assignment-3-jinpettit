package parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

class Source {
    private final String id;
    private final String name;

    // Could make Private if not testing for each field of the Article class in the test cases
    @JsonCreator
    public Source(@JsonProperty("id") String id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Compares this Source object to another object for equality (for testing).
     *
     * Two Source objects are considered equal if their `id` and `name` fields are equal.
     *
     * @param o the object to compare this Source against
     * @return true if the given object is a Source and is equal to this Source;
     *         false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return Objects.equals(id, source.id) && Objects.equals(name, source.name);
    }

    /**
     * Returns a hash code value for this Source object.
     *
     * The hash code is computed using the `id` and `name` fields.
     *
     * @return a hash code value for this Source.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
