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
        System.out.println("          |                                        |");
        System.out.println("          |       ♡  D O L L Z - L E D G E R  ♡    |");
        System.out.println("          |         Your Economy. Your Power.      |");
        System.out.println("          |                                        |");
        System.out.println("          ⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊‿︵‧˚₊⊹⊹₊˚‧︵‿₊୨ᰔ୧₊‿︵‧˚₊⊹");
        System.out.println();
        System.out.println("               [ Financial Inventory Tracker v1.0 ]");
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


}
