package parser;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Main {
    static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String newsapiFile = "inputs/newsapi.txt";
    private static final String simpleFile = "inputs/simple.txt";
    static final String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=67a30faa94b04c68a3e64ca1c20912cc";
    /**
     * The main method for the parser.
     *
     * This method configures a logger to write logs to a specified file and processes
     * a predefined list of JSON sources, including local files and a remote URL. Each JSON source
     * is parsed to extract and display the articles contained within it. If any errors occur during
     * parsing, they will be logged to the configured log file.
     *
     * @param args command-line arguments (not used).
     * @throws IOException if an error occurs while reading JSON files or accessing the URL.
     */
    public static void main(String[] args) throws IOException {
        try {
            FileHandler fileHandler = new FileHandler("articles-parser.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a ParserVisitor
        ParserVisitor parserVisitor = new ParserVisitorImplementation();

        // Define different source formats
        SourceFormat newsApiSource = new SourceFormat(SourceEnum.FILE, FormatEnum.NEWSAPI);
        SourceFormat simpleSource = new SourceFormat(SourceEnum.FILE, FormatEnum.SIMPLE);
        SourceFormat urlSource = new SourceFormat(SourceEnum.URL, FormatEnum.NEWSAPI);

        // Parse and print the News API file, Simple format file, and URL
        parseAndPrint(newsApiSource, newsapiFile, parserVisitor);
        parseAndPrint(simpleSource, simpleFile, parserVisitor);
        parseAndPrint(urlSource, url, parserVisitor);
    }

    /**
     * Parses data from the specified source and prints the parsed articles.
     *
     * @param sourceFormat the format of the source, specifying the source type (file or URL) and the format (NEWSAPI or SIMPLE).
     * @param source       the path to the file or the URL to fetch data from.
     * @param visitor      the visitor to process the parsed articles.
     */
    private static void parseAndPrint(SourceFormat sourceFormat, String source, ParserVisitor visitor) {
        try {
            String data = loadData(sourceFormat, source);

            Parser parser;
            if (sourceFormat.getFormat() == FormatEnum.NEWSAPI) {
                parser = new NewsParser(data);
            } else {
                parser = new SimpleParser(data);
            }

            parser.accept(visitor).forEach(System.out::println);

        } catch (IOException e) {
            logger.severe("Error loading data from source: " + e.getMessage());
        }
    }

    /**
     * Loads data from the specified source based on the source format.
     *
     * @param sourceFormat the format of the source, specifying the source type (file or URL).
     * @param source       the path to the file or the URL to fetch data from.
     * @return the data as a string loaded from the file or URL.
     * @throws IOException if an error occurs while reading the file or fetching data from the URL.
     */
    private static String loadData(SourceFormat sourceFormat, String source) throws IOException {
        if (sourceFormat.getSource() == SourceEnum.URL) {
            return fromUrl(source);
        } else {
            return fromFile(source);
        }
    }

    /**
     * Reads the contents of a file specified by the given file path and returns it as a String.
     *
     * @param filePath the path to the file to be read
     * @return a String containing the contents of the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    static String fromFile(String filePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Reads the contents from a URL specified by the given file path and returns it as a String.
     *
     * @param filePath the URL to be read
     * @return a String containing the contents retrieved from the URL
     * @throws IOException if an I/O error occurs while reading from the URL
     */
    static String fromUrl(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
