package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");
    private static final DateTimeFormatter SORTABLE_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

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
                .filter(t -> t.getAmount() < 0) // лише витрати (від'ємні значення)
                .sorted(Comparator.comparing(Transaction::getAmount)) // від найбільшої витрати
                .limit(10) // перші 10 записів
                .collect(Collectors.toList());
    }

    public static Optional<Transaction> findLargestExpense(List<Transaction> transactions,
                                                           LocalDate from, LocalDate to) {
        return expensesInPeriod(transactions, from, to)
                .min(Comparator.comparingDouble(Transaction::getAmount));
    }

    public static Optional<Transaction> findSmallestExpense(List<Transaction> transactions,
                                                            LocalDate from, LocalDate to) {
        return expensesInPeriod(transactions, from, to)
                .max(Comparator.comparingDouble(Transaction::getAmount));
    }

    public static Map<String, Double> sumExpensesByCategory(List<Transaction> transactions) {
        Map<String, Double> sums = new HashMap<>();
        transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .forEach(t -> sums.merge(t.getDescription(), -t.getAmount(), Double::sum));

        return sums.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    public static Map<String, Double> sumExpensesByMonth(List<Transaction> transactions) {
        Map<String, Double> sums = new TreeMap<>();
        transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .forEach(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    sums.merge(date.format(SORTABLE_MONTH_FORMATTER), -t.getAmount(), Double::sum);
                });
        return sums;
    }

    private static Stream<Transaction> expensesInPeriod(List<Transaction> transactions,
                                                        LocalDate from, LocalDate to) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !date.isBefore(from) && !date.isAfter(to);
                });
    }
}