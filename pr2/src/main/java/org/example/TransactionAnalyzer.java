package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
            String transactionMonthYear = date.format(MONTH_YEAR_FORMATTER);
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Optional<Transaction> findLargestExpense(List<Transaction> transactions, String from, String to) {
        return expensesInPeriod(transactions, from, to).stream()
                .min(Comparator.comparing(Transaction::getAmount));
    }

    public static Optional<Transaction> findSmallestExpense(List<Transaction> transactions, String from, String to) {
        return expensesInPeriod(transactions, from, to).stream()
                .max(Comparator.comparing(Transaction::getAmount));
    }

    public static Map<String, Double> expensesByCategory(List<Transaction> transactions) {
        Map<String, Double> totals = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(Transaction::getDescription,
                        Collectors.summingDouble(t -> -t.getAmount())));
        return totals.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    public static Map<String, Double> expensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> LocalDate.parse(t.getDate(), DATE_FORMATTER).format(YEAR_MONTH_FORMATTER),
                        TreeMap::new,
                        Collectors.summingDouble(t -> -t.getAmount())));
    }

    private static List<Transaction> expensesInPeriod(List<Transaction> transactions, String from, String to) {
        LocalDate start = LocalDate.parse(from, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(to, DATE_FORMATTER);
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .collect(Collectors.toList());
    }
}
