package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TransactionReportGenerator {

    private static final double AMOUNT_PER_STAR = 1000.0; // 1 символ '*' = 1000 грн

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

    public static void printExtremeExpensesReport(LocalDate from, LocalDate to,
                                                  Optional<Transaction> largest,
                                                  Optional<Transaction> smallest) {
        System.out.println("Витрати за період " + from + " — " + to + ":");
        System.out.println("  Найбільша: " + largest
                .map(t -> t.getDescription() + " (" + t.getDate() + "): " + t.getAmount())
                .orElse("немає витрат"));
        System.out.println("  Найменша: " + smallest
                .map(t -> t.getDescription() + " (" + t.getDate() + "): " + t.getAmount())
                .orElse("немає витрат"));
    }

    // Текстова гістограма: кожен '*' відповідає AMOUNT_PER_STAR грн
    public static void printExpensesChart(String title, Map<String, Double> sums) {
        System.out.println(title + " (1 * = " + (int) AMOUNT_PER_STAR + " грн):");
        for (Map.Entry<String, Double> entry : sums.entrySet()) {
            double sum = entry.getValue();
            int stars = (int) Math.round(sum / AMOUNT_PER_STAR);
            if (stars == 0 && sum > 0) {
                stars = 1; // навіть мала сума має бути помітна
            }
            System.out.printf("%-25s %10.2f %s%n", entry.getKey(), sum, "*".repeat(stars));
        }
    }
}

