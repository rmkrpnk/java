package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

class TransactionCSVReaderTest {
    @Test
    public void testReadTransactionsFromCsvFile() throws IOException {
        Path file = Files.createTempFile("transactions", ".csv");
        Files.writeString(file,
                "Дата,Сума,Категорія\n" +
                "05-12-2023,-450,Сільпо\n" +
                "10-12-2023,8000,Зарплата\n" +
                "\n" +
                "18-12-2023,-200,Кав'ярня\n",
                StandardCharsets.UTF_8);

        List<Transaction> transactions = TransactionCSVReader.readTransactions(file.toString());
        Files.deleteIfExists(file);

        Assertions.assertEquals(3, transactions.size(), "Заголовок і порожні рядки мають пропускатися");

        Transaction first = transactions.get(0);
        Assertions.assertEquals("05-12-2023", first.getDate());
        Assertions.assertEquals(-450.0, first.getAmount());
        Assertions.assertEquals("Сільпо", first.getDescription());

        Assertions.assertEquals("Кав'ярня", transactions.get(2).getDescription());
    }

    @Test
    public void testParserWorksWithoutFile() {
        DataSource memorySource = () -> Arrays.asList("01-01-2024,100.5,Дохід", "битий рядок");

        List<Transaction> transactions = TransactionCSVReader.readTransactions(memorySource);

        Assertions.assertEquals(1, transactions.size());
        Assertions.assertEquals(100.5, transactions.get(0).getAmount());
    }

    @Test
    public void testMissingFileReturnsEmptyList() {
        List<Transaction> transactions = TransactionCSVReader.readTransactions("no_such_file.csv");
        Assertions.assertTrue(transactions.isEmpty());
    }
}
