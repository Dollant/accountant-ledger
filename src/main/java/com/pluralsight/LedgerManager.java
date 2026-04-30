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

        transactions.sort(Comparator
                .comparing(Transactions::getDate)
                .thenComparing(Transactions::getTime)
                .reversed());
    }

    public void save(Transactions t) throws IOException {
        transactions.add(0, t);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            writer.write(t.toCsvLine());
            writer.newLine();
        }
    }

    public List<Transactions> getAll() { return Collections.unmodifiableList(transactions); }

    public List<Transactions> getDeposits() {
        return transactions.stream().filter(Transactions::isDeposit).collect(Collectors.toList());
    }

    public List<Transactions> getPayments() {
        return transactions.stream().filter(Transactions::isPayment).collect(Collectors.toList());
    }

    public List<Transactions> getMonthToDate() {
        LocalDate now   = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        return filterByDateRange(start, now);
    }
    public List<Transactions> getPreviousMonth() {
        LocalDate now = LocalDate.now();
        LocalDate firstOfPrev = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastOfPrev  = now.withDayOfMonth(1).minusDays(1);
        return filterByDateRange(firstOfPrev, lastOfPrev);
    }

    public List<Transactions> getYearToDate() {
        LocalDate now   = LocalDate.now();
        LocalDate start = now.withDayOfYear(1);
        return filterByDateRange(start, now);
    }

    public List<Transactions> getPreviousYear() {
        LocalDate now   = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear() - 1, 1, 1);
        LocalDate end   = LocalDate.of(now.getYear() - 1, 12, 31);
        return filterByDateRange(start, end);
    }

    public List<Transactions> getByVendor(String vendorQuery) {
        String q = vendorQuery.toLowerCase().trim();
        return transactions.stream()
                .filter(t -> t.getVendor().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Transactions> customSearch(LocalDate startDate, LocalDate endDate,
                                          String description, String vendor, String amountStr) {
        return transactions.stream()
                .filter(t -> startDate == null || !t.getDate().isBefore(startDate))
                .filter(t -> endDate == null || !t.getDate().isAfter(endDate))
                .filter(t -> description == null || description.isBlank()
                        || t.getDescription().toLowerCase().contains(description.toLowerCase()))
                .filter(t -> vendor == null || vendor.isBlank()
                        || t.getVendor().toLowerCase().contains(vendor.toLowerCase()))
                .filter(t -> {
                    if (amountStr == null || amountStr.isBlank()) return true;
                    try {
                        double amt = Double.parseDouble(amountStr);
                        return t.getAmount() == amt;
                    } catch (NumberFormatException e) {
                        return true;
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Transactions> filterByDateRange(LocalDate start, LocalDate end) {

    }

}
