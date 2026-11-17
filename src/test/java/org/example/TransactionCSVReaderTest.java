package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class TransactionCSVReaderTest {

    @TempDir
    Path tempDir;

    @Test
    public void testReadTransactions() throws IOException {
        Path csv = tempDir.resolve("test.csv");
        Files.writeString(csv,
                "05-12-2023,-450,Сільпо\n" +
                        "10-12-2023,8000,Зарплата\n",
                StandardCharsets.UTF_8);

        List<Transaction> transactions = TransactionCSVReader.readTransactions(csv.toUri().toString());

        Assertions.assertEquals(2, transactions.size());
        Assertions.assertEquals("05-12-2023", transactions.get(0).getDate());
        Assertions.assertEquals(-450.0, transactions.get(0).getAmount());
        Assertions.assertEquals("Сільпо", transactions.get(0).getDescription());
        Assertions.assertEquals(8000.0, transactions.get(1).getAmount());
    }

    @Test
    public void testHeaderIsSkipped() throws IOException {
        Path csv = tempDir.resolve("header.csv");
        Files.writeString(csv,
                "Дата,Сума,Категорія\n" +
                        "05-12-2023,-450,Сільпо\n",
                StandardCharsets.UTF_8);

        List<Transaction> transactions = TransactionCSVReader.readTransactions(csv.toUri().toString());

        Assertions.assertEquals(1, transactions.size(), "Заголовок не має потрапити у список");
    }
}
