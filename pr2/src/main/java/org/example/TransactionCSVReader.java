package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    public static List<Transaction> readTransactions(String filePath) {
        return readTransactions(createSource(filePath));
    }

    public static List<Transaction> readTransactions(DataSource source) {
        try {
            return parseTransactions(source.readLines());
        } catch (IOException e) {
            System.err.println("Не вдалося прочитати дані: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Transaction> parseTransactions(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        for (String rawLine : lines) {
            String line = rawLine.replace("﻿", "").trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] values = line.split(",", 3);
            if (values.length < 3) {
                continue;
            }
            double amount;
            try {
                amount = Double.parseDouble(values[1].trim());
            } catch (NumberFormatException e) {
                continue;
            }
            transactions.add(new Transaction(values[0].trim(), amount, values[2].trim()));
        }
        return transactions;
    }

    private static DataSource createSource(String filePath) {
        String lower = filePath.toLowerCase();
        if (lower.startsWith("http://") || lower.startsWith("https://")) {
            return new UrlDataSource(filePath);
        }
        return new FileDataSource(filePath);
    }
}
