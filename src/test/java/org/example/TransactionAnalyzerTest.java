package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class TransactionAnalyzerTest {

    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("2023-01-01", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("2023-01-02", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("2023-01-03", 150.0, "Дохід");
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
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            transactions.add(new Transaction("01-01-2024", -100.0 * i, "Витрата " + i));
        }
        transactions.add(new Transaction("02-01-2024", 99999.0, "Зарплата")); // дохід не враховується

        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(10, top.size(), "Має бути рівно 10 витрат");
        Assertions.assertEquals(-1200.0, top.get(0).getAmount(), "Перша має бути найбільша витрата");
        Assertions.assertEquals(-300.0, top.get(9).getAmount(), "Десята витрата неправильна");
        Assertions.assertTrue(top.stream().allMatch(t -> t.getAmount() < 0), "Доходи не мають потрапити у список");
    }

    @Test
    public void testFindLargestAndSmallestExpenseByPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("14-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("18-12-2023", -200.0, "Кав'ярня"),
                new Transaction("10-12-2023", 8000.0, "Зарплата"),
                new Transaction("05-01-2024", -9000.0, "Поза періодом"));
        LocalDate from = LocalDate.of(2023, 12, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        Assertions.assertEquals(-3200.0,
                TransactionAnalyzer.findLargestExpense(transactions, from, to).orElseThrow().getAmount());
        Assertions.assertEquals(-200.0,
                TransactionAnalyzer.findSmallestExpense(transactions, from, to).orElseThrow().getAmount());
    }

    @Test
    public void testSumExpensesByCategoryAndMonth() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("07-12-2023", -50.0, "Сільпо"),
                new Transaction("05-01-2024", -500.0, "Аптека"),
                new Transaction("10-12-2023", 8000.0, "Зарплата"));

        Map<String, Double> byCategory = TransactionAnalyzer.sumExpensesByCategory(transactions);
        Map<String, Double> byMonth = TransactionAnalyzer.sumExpensesByMonth(transactions);

        Assertions.assertEquals(500.0, byCategory.get("Сільпо"));
        Assertions.assertEquals(500.0, byCategory.get("Аптека"));
        Assertions.assertFalse(byCategory.containsKey("Зарплата"), "Доходи не входять у звіт витрат");
        Assertions.assertEquals(500.0, byMonth.get("2023-12"));
        Assertions.assertEquals(500.0, byMonth.get("2024-01"));
    }
}
