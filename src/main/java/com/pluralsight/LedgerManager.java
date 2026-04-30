package com.pluralsight;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LedgerManager {
    private static final String CSV_FILE = "transactions.csv";
    private static final String CSV_HEADER = "date|time|description|vendor|amount";

    private List<Transactions> transactions = new ArrayList<>();

    public void load() throws IOException {
        Path path = Path.of(CSV_FILE);

        if (!Files.exists(path)) {
            Files.writeString(path, CSV_HEADER + System.lineSeparator());
            return;
        }

        transactions.clear();
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {
            Transactions t = Transactions.fromCsvLine(lines.get(i));
            // fix: skip null results from malformed lines instead of adding them to the list
            if (t != null) transactions.add(t);
        }
    }
}
