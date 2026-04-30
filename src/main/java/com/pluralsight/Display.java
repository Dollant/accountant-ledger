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

}
