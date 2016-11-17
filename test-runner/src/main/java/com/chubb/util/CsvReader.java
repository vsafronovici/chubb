package com.chubb.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vsafronovici on 10/11/2016.
 */
public class CsvReader {

    private static FileReader getFileReader(String filePath) throws IOException {
        return new FileReader(filePath);
    }

    private static List<String> readHeader(Reader in) throws IOException {
        CSVParser records = CSVFormat.DEFAULT.parse(in);

        CSVRecord val = records.getRecords().iterator().next();

        List<String> headers = new ArrayList<>();
        val.iterator().forEachRemaining(s -> headers.add(s));

        return headers;
    }

    public static List<Map<String, String>> readCSV(final File file) throws IOException {
        return readCSV(file.getPath());
    }

    public static List<Map<String, String>> readCSV(final String file) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();

        List<String> headers = readHeader(getFileReader(file));

        CSVParser records2 = new CSVParser(getFileReader(file), CSVFormat.DEFAULT.withHeader());

        for (CSVRecord record : records2) {
            Map<String, String> line = new HashMap<>();

            for (String header : headers) {
                line.put(header, record.get(header));
            }
            data.add(line);
        }
        return data;
    }

    public static String writeCSV(final List<Map<String, String>> data) throws IOException {
        String result = "";

        List<String> headerLine = new ArrayList<>();
        Map<String, String> header = data.get(0);
        header.forEach((s, s2) -> headerLine.add(s));

        List<List<String>> lines = new ArrayList<>();
        data.forEach(stringStringMap -> {
            List<String> line = new ArrayList<>();
            stringStringMap.forEach((s, s2) -> line.add(s2));
            lines.add(line);
        });

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        StringWriter stringWriter = new StringWriter();

        CSVPrinter csvFilePrinter = new CSVPrinter(stringWriter, csvFileFormat);
        csvFilePrinter.printRecord(headerLine);
        for (List<String> line : lines) {
            csvFilePrinter.printRecord(line);
        }
        stringWriter.flush();
        result = stringWriter.toString();

        return result;
    }


}
