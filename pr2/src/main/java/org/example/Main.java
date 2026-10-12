package org.example;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        System.out.println();
        String from = "01-12-2023";
        String to = "31-12-2023";
        Optional<Transaction> largest = TransactionAnalyzer.findLargestExpense(transactions, from, to);
        Optional<Transaction> smallest = TransactionAnalyzer.findSmallestExpense(transactions, from, to);
        TransactionReportGenerator.printMinMaxExpenseReport(from, to, largest, smallest);

        System.out.println();
        TransactionReportGenerator.printExpensesByCategoryReport(TransactionAnalyzer.expensesByCategory(transactions));
        System.out.println();
        TransactionReportGenerator.printExpensesByMonthReport(TransactionAnalyzer.expensesByMonth(transactions));
    }
}
