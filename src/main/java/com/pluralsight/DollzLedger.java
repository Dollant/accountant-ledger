package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class DollzLedger {

    private static final Scanner scanner = new Scanner(System.in);
    private static final LedgerManager ledger  = new LedgerManager();
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static String readLine() {
        if (scanner.hasNextLine()) return scanner.nextLine();
        return "";
    }

    public static void main(String[] args) {
        try {
            ledger.load();
        } catch (Exception e) {
            System.out.println("Could not load transactions.csv: " + e.getMessage());
        }

        Display.showSplash();
        Display.info("Ledger loaded. " + ledger.getAll().size() + " transaction(s) on record.");
        System.out.print("  Press ENTER to continue...");
        readLine();

        runHomeScreen();
    }

    private static void runHomeScreen() {
        while (true) {
            Display.showHomeScreen();
            String input = readLine().trim().toUpperCase();

            switch (input) {
                case "D" -> addTransaction(true);   // true = deposit
                case "P" -> addTransaction(false);  // false = payment
                case "L" -> runLedgerScreen();
                case "X" -> {
                    Display.showGoodbye();
                    return;
                }
                default -> Display.error("Unknown command. Try D, P, L, or X.");
            }
        }
    }
    private static void addTransaction(boolean isDeposit) {
        String kind = isDeposit ? "NEW DEPOSIT" : "NEW PAYMENT";
        Display.sectionHeader(kind);

        LocalDate date = null;
        while (date == null) {
            Display.prompt("Date (yyyy-MM-dd) [leave blank for today]");
            String raw = readLine().trim();
            if (raw.isBlank()) {
                date = LocalDate.now();
            } else {
                try {
                    date = LocalDate.parse(raw, DATE_FMT);
                } catch (DateTimeParseException e) {
                    Display.error("Invalid date. Use yyyy-MM-dd (e.g. 2024-03-15)");
                }
            }
        }

        LocalTime time = LocalTime.now().withNano(0);
        Display.prompt("Time (HH:mm:ss) [leave blank for now -> " + time + "]");
        String rawTime = readLine().trim();
        if (!rawTime.isBlank()) {
            try{
                time = LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
            } catch (DateTimeParseException e) {
                Display.error("Invalid time -- using current time instead.");
            }
        }

        String description = "";
        while (description.isBlank()) {
            Display.prompt("Description");
            description = readLine().trim();
            if (description.isBlank()) Display.error("Description cannot be empty.");
        }

        String vendor = "";
        while (vendor.isBlank()) {
            Display.prompt("Vendor / Merchant");
            vendor = readLine().trim();
            if (vendor.isBlank()) Display.error("Vendor cannot be empty.");
        }

        double amount = 0;
        boolean validAmount = false;
        while (!validAmount) {
            Display.prompt("Amount (numbers only, e.g. 50.00)");
            String rawAmt = readLine().trim().replace("$", "");
            try {
                amount = Math.abs(Double.parseDouble(rawAmt));
                if (amount <= 0) {
                    Display.error("Amount must be greater than zero.");
                } else {
                    validAmount = true;
                }
            } catch (NumberFormatException e) {
                Display.error("Invalid amount. Enter a number like 49.99");
            }
        }

        if (!isDeposit) amount = -amount;

        Transactions t = new Transactions(date, time, description, vendor, amount);
        try {
            ledger.save(t);
            Display.success("Transaction logged! " + description + " (" + (isDeposit ? "+" : "") + amount + ")");
        } catch (Exception e) {
            Display.error("Failed to save: " + e.getMessage());
        }
    }

    private static void runLedgerScreen() {
        while (true) {
            Display.showLedgerMenu();
            String input = readLine().trim().toUpperCase();

            switch (input) {
                case "A" -> { Display.showTransactions(ledger.getAll(), "ALL ENTRIES"); readLine(); }
                case "D" -> { Display.showTransactions(ledger.getDeposits(), "DEPOSITS");    readLine(); }
                case "P" -> { Display.showTransactions(ledger.getPayments(), "PAYMENTS");    readLine(); }
                case "R" -> runReportsScreen();
                case "H" -> { return; }
                default  -> Display.error("Unknown command. Try A, D, P, R, or H.");
            }
        }
    }

    private static void runReportsScreen() {
        while (true) {
            Display.showReportsMenu();

            String input = readLine().trim();

            switch (input) {
                case "1" -> { Display.showTransactions(ledger.getMonthToDate(),   "MONTH TO DATE");  readLine(); }
                case "2" -> { Display.showTransactions(ledger.getPreviousMonth(), "PREVIOUS MONTH"); readLine(); }
                case "3" -> { Display.showTransactions(ledger.getYearToDate(),    "YEAR TO DATE");   readLine(); }
                case "4" -> { Display.showTransactions(ledger.getPreviousYear(),  "PREVIOUS YEAR");  readLine(); }
                case "5" -> searchByVendor();
                case "6" -> customSearch();
                case "0" -> { return; }
                default  -> Display.error("Unknown command. Enter 0-6.");
            }
        }
    }

    private static void searchByVendor() {
        Display.sectionHeader("VENDOR SEARCH");
        Display.prompt("Enter vendor name (partial match is fine)");
        String query = readLine().trim();

        if (query.isBlank()) {
            Display.error("No vendor entered -- returning to reports.");
            return;
        }

        List<Transactions> results = ledger.getByVendor(query);
        Display.showTransactions(results, "VENDOR: \"" + query + "\"");
        readLine();
    }
    private static void customSearch() {
        Display.sectionHeader("CUSTOM SEARCH -- leave any field blank to skip it");

        LocalDate startDate = null;
        Display.prompt("Start Date (yyyy-MM-dd)");
        String raw = readLine().trim();
        if (!raw.isBlank()) {
            try { startDate = LocalDate.parse(raw, DATE_FMT); }
            catch (DateTimeParseException e) { Display.error("Invalid date -- skipping."); }
        }

        LocalDate endDate = null;
        Display.prompt("End Date (yyyy-MM-dd)");
        raw = readLine().trim();
        if (!raw.isBlank()) {
            try { endDate = LocalDate.parse(raw, DATE_FMT); }
            catch (DateTimeParseException e) { Display.error("Invalid date -- skipping."); }
        }

        Display.prompt("Description (partial match)");
        String description = readLine().trim();

        Display.prompt("Vendor (partial match)");
        String vendor = readLine().trim();

        Display.prompt("Exact Amount (e.g. -89.99 or 1500.00)");
        String amountStr = readLine().trim();

        // feat: pass all five values to LedgerManager which handles skipping blank fields internally
        List<Transactions> results = ledger.customSearch(startDate, endDate, description, vendor, amountStr);
        Display.showTransactions(results, "CUSTOM SEARCH RESULTS");
        readLine();
    }
}

