package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TransactionAnalyzerTest {
    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("01-01-2023", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("02-01-2023", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("03-01-2023", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    public void testCountTransactionsByMonth() {
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    @Test
    public void testFindTopExpenses() {
        List<Transaction> transactions = IntStream.rangeClosed(1, 12)
                .mapToObj(i -> new Transaction("01-01-2024", -100.0 * i, "Витрата " + i))
                .collect(Collectors.toList());
        transactions.add(new Transaction("02-01-2024", 50000.0, "Зарплата"));
        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(10, top.size(), "Має бути рівно 10 витрат");
        Assertions.assertEquals(-1200.0, top.get(0).getAmount(), "Першою має бути найбільша витрата");
        Assertions.assertEquals(-300.0, top.get(9).getAmount(), "Останньою має бути 10-та за розміром витрата");
        Assertions.assertTrue(top.stream().allMatch(t -> t.getAmount() < 0), "У списку мають бути лише витрати");
    }

    @Test
    public void testFindTopExpensesWhenLessThanTen() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -10.0, "A"),
                new Transaction("02-01-2024", -30.0, "B"),
                new Transaction("03-01-2024", 100.0, "Дохід"));

        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(2, top.size());
        Assertions.assertEquals("B", top.get(0).getDescription());
    }

    @Test
    public void testLargestAndSmallestExpenseInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("30-11-2023", -9999.0, "Поза періодом"),
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("14-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("18-12-2023", -200.0, "Кав'ярня"),
                new Transaction("26-12-2023", 3000.0, "Премія"),
                new Transaction("05-01-2024", -1.0, "Поза періодом"));

        Optional<Transaction> largest = TransactionAnalyzer.findLargestExpense(transactions, "01-12-2023", "31-12-2023");
        Optional<Transaction> smallest = TransactionAnalyzer.findSmallestExpense(transactions, "01-12-2023", "31-12-2023");

        Assertions.assertEquals("Оренда квартири", largest.orElseThrow().getDescription());
        Assertions.assertEquals("Кав'ярня", smallest.orElseThrow().getDescription());
        Assertions.assertTrue(TransactionAnalyzer.findLargestExpense(transactions, "01-06-2023", "30-06-2023").isEmpty(),
                "За період без витрат результату бути не повинно");
    }

    @Test
    public void testExpensesByCategoryAndMonth() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("10-12-2023", 8000.0, "Зарплата"),
                new Transaction("05-01-2024", -500.0, "Сільпо"),
                new Transaction("07-01-2024", -250.0, "Вечеря"));

        Map<String, Double> byCategory = TransactionAnalyzer.expensesByCategory(transactions);
        Assertions.assertEquals(950.0, byCategory.get("Сільпо").doubleValue());
        Assertions.assertEquals(250.0, byCategory.get("Вечеря").doubleValue());
        Assertions.assertFalse(byCategory.containsKey("Зарплата"), "Доходи не є витратами");

        Map<String, Double> byMonth = TransactionAnalyzer.expensesByMonth(transactions);
        Assertions.assertEquals(450.0, byMonth.get("2023-12").doubleValue());
        Assertions.assertEquals(750.0, byMonth.get("2024-01").doubleValue());
    }
}
