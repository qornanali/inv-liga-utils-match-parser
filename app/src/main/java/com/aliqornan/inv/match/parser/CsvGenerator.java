package com.aliqornan.inv.match.parser;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

import static com.opencsv.ICSVWriter.NO_ESCAPE_CHARACTER;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

public class CsvGenerator {
    private static final char SEPARATOR = ',';
    private static final String LINE_SEPARATOR = "line.separator";

    public File generate(String filePath, Consumer<CSVWriter> writerHandler)
            throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
                SEPARATOR,
                NO_QUOTE_CHARACTER,
                NO_ESCAPE_CHARACTER,
                System.getProperty(LINE_SEPARATOR))) {
            writerHandler.accept(writer);
        }
        return new File(filePath);
    }

}
