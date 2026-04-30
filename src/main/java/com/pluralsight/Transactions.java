package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transactions {

    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Transactions(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public static Transactions fromCsvLine(String line) {
        if (line == null || line.isBlank()) return null;
        String[] parts = line.split("\\|");

        if (parts.length != 5) return null;

        try {
            LocalDate date = LocalDate.parse(parts[0].trim(), DATE_FMT);
            LocalTime time = LocalTime.parse(parts[1].trim(), TIME_FMT);
            String desc = parts[2].trim();
            String vendor = parts[3].trim();
            double amount = Double.parseDouble(parts[4].trim());
            return new Transactions(date, time, desc, vendor, amount);
        }
    }
}
