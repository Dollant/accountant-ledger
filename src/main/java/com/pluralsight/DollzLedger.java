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

            }
        }
    }
}
