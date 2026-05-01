package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class Display {
    private static final int WIDTH = 80;
    private static final String DIVIDER = "-".repeat(WIDTH);
    private static final String DOUBLE_DIV = "=".repeat(WIDTH);

    public static void showSplash() {
        System.out.println();
        System.out.println("          ⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹");
        System.out.println("           |                                        |");
        System.out.println("           |     ♡  D O L L Z - L E D G E R  ♡      |");
        System.out.println("           |        Your Economy. Your Power.       |");
        System.out.println("           |                                        |");
        System.out.println("          ⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹");
        System.out.println();
        System.out.println("              [ Financial Inventory Tracker v1.0 ]");
        System.out.println();
    }

    public static void showHomeScreen() {
        showSplash();
        System.out.println(DOUBLE_DIV);
        System.out.println("  ♡ MAIN HUB ♡");
        System.out.println(DOUBLE_DIV);
        System.out.println();
        System.out.println("  [D]  Add Deposit        -- Log incoming gold");
        System.out.println("  [P]  Make Payment       -- Record a purchase/debit");
        System.out.println("  [L]  Open Ledger        -- View your transaction log");
        System.out.println("  [X]  Exit               -- Save & quit");
        System.out.println();
        System.out.println(DIVIDER);
        System.out.print("  > Enter command: ");
    }

    public static void showLedgerMenu() {
        showSplash();
        System.out.println(DOUBLE_DIV);
        System.out.println("  ♡ LEDGER VAULT ♡");
        System.out.println(DOUBLE_DIV);
        System.out.println();
        System.out.println("  [A]  All Entries        -- Full transaction history");
        System.out.println("  [D]  Deposits Only      -- Incoming gold only");
        System.out.println("  [P]  Payments Only      -- Outgoing gold only");
        System.out.println("  [R]  Reports            -- Run pre-defined reports");
        System.out.println("  [H]  Home               -- Return to main hub");
        System.out.println();
        System.out.println(DIVIDER);
        System.out.print("  > Enter command: ");
    }

    public static void showReportsMenu() {
        showSplash();
        System.out.println(DOUBLE_DIV);
        System.out.println("  ♡ REPORTS & INTEL ♡");
        System.out.println(DOUBLE_DIV);
        System.out.println();
        System.out.println("  [1]  Month To Date      -- This month's activity");
        System.out.println("  [2]  Previous Month     -- Last month's log");
        System.out.println("  [3]  Year To Date       -- This year so far");
        System.out.println("  [4]  Previous Year      -- Full last year");
        System.out.println("  [5]  Search by Vendor   -- Filter by merchant");
        System.out.println("  [6]  Custom Search      -- Multi-field filter");
        System.out.println("  [0]  Back to Ledger");
        System.out.println();
        System.out.println(DIVIDER);
        System.out.print("  > Enter command: ");
    }

    public static void showTransactions(List<Transactions> list, String title) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  " + title);
        System.out.println(DIVIDER);

        if (list.isEmpty()) {
            System.out.println("  No entries found in this range. </3");
        } else {
            System.out.printf("  %-12s  %-5s  %-32s  %-20s  %s%n",
                    "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");
            System.out.println("  " + "-".repeat(WIDTH - 2));

            double total = 0;
            for (Transactions t : list) {
                String sign = t.isDeposit() ? "+" : "";
                System.out.printf("  %-12s  %-5s  %-32s  %-20s  %s%.2f%n",
                        t.getDate(),
                        t.getTime().toString().substring(0, 5),
                        cut(t.getDescription(), 32),
                        cut(t.getVendor(), 20),
                        sign, t.getAmount());
                total += t.getAmount();
            }

            System.out.println("  " + "-".repeat(WIDTH - 2));
            System.out.printf("  %-69s%+.2f%n", "NET BALANCE:", total);
        }

        System.out.println(DIVIDER);
        pressEnterToContinue();
    }

    public static void prompt(String label) {
        System.out.print("  > " + label + ": ");
    }

    public static void success(String msg) {
        System.out.println();
        System.out.println("  [OK] " + msg);
        System.out.println();
        pressEnterToContinue();
    }

    public static void error(String msg) {
        System.out.println("  [ERROR] " + msg);
    }

    public static void info(String msg) {
        System.out.println("  " + msg);
    }

    public static void sectionHeader(String msg) {
        System.out.println();
        System.out.println("  -- " + msg + " --");
        System.out.println();
    }

    public static void showGoodbye() {
        System.out.println();
        System.out.println("          ⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹");
        System.out.println("               |   Session Saved. See you next run,   |");
        System.out.println("               |          Ledger Master.              |");
        System.out.println("          ⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹");
        System.out.println();
    }

    public static void pressEnterToContinue() {
        System.out.print("  Press ENTER to continue...");
        try (Scanner sc = new Scanner(System.in)) {
            sc.nextLine();
        }
    }

    private static String cut(String s, int max) {
        return s.length() > max ? s.substring(0, max - 1) + "..." : s;
    }
}
