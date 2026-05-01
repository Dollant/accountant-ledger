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
        }
    }
}
