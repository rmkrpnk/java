package org.example;

import java.util.ArrayList;
import java.util.List;

public class CsvTransactionParser implements TransactionParser {
    @Override
    public List<Transaction> parse(List<String> lines) {
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
}
