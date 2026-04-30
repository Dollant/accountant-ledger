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
    }


}
