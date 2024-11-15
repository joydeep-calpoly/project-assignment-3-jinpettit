package parser;

class SourceFormat {
    private final SourceEnum source;
    private final FormatEnum format;

    SourceFormat(SourceEnum source, FormatEnum format) {
        this.source = source;
        this.format = format;
    }

    /**
     * Retrieves the source type (e.g., FILE or URL).
     *
     * @return the SourceEnum representing the source type.
     */
    public SourceEnum getSource() {
        return source;
    }

    /**
     * Retrieves the data format (e.g., SIMPLE or NEWSAPI).
     *
     * @return the FormatEnum representing the data format.
     */
    public FormatEnum getFormat() {
        return format;
    }
}
