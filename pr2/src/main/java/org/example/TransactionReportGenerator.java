package org.example;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TransactionReportGenerator {
    private static final double AMOUNT_PER_SYMBOL = 1000;

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printMinMaxExpenseReport(String from, String to,
                                                Optional<Transaction> largest,
                                                Optional<Transaction> smallest) {
        System.out.println("Витрати за період " + from + " — " + to + ":");
        System.out.println("  Найбільша: " + largest.map(TransactionReportGenerator::format).orElse("витрат немає"));
        System.out.println("  Найменша:  " + smallest.map(TransactionReportGenerator::format).orElse("витрат немає"));
    }

    public static void printExpensesByCategoryReport(Map<String, Double> byCategory) {
        printChart("Витрати по категоріях", byCategory);
    }

    public static void printExpensesByMonthReport(Map<String, Double> byMonth) {
        printChart("Витрати по місяцях", byMonth);
    }

    public static String bar(double amount) {
        int symbols = (int) Math.round(amount / AMOUNT_PER_SYMBOL);
        if (symbols == 0 && amount > 0) {
            symbols = 1;
        }
        return "*".repeat(symbols);
    }

    private static void printChart(String title, Map<String, Double> data) {
        System.out.println(title + " (1 '*' = " + (long) AMOUNT_PER_SYMBOL + " грн):");
        int width = data.keySet().stream().mapToInt(String::length).max().orElse(0);
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            System.out.printf("  %-" + width + "s | %-20s %.2f грн%n",
                    entry.getKey(), bar(entry.getValue()), entry.getValue());
        }
    }

    private static String format(Transaction t) {
        return t.getDescription() + " (" + t.getDate() + "): " + t.getAmount();
    }
}
